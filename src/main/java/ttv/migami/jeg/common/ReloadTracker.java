package ttv.migami.jeg.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import ttv.migami.jeg.Config;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.init.ModSyncedDataKeys;
import ttv.migami.jeg.item.GunItem;
import ttv.migami.jeg.network.PacketHandler;
import ttv.migami.jeg.network.message.S2CMessageGunSound;
import ttv.migami.jeg.util.GunEnchantmentHelper;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ReloadTracker
{
    private static final Map<Player, ReloadTracker> RELOAD_TRACKER_MAP = new WeakHashMap<>();

    private final int startTick;
    private final int slot;
    private final ItemStack stack;
    private final Gun gun;

    private ReloadTracker(Player player)
    {
        this.startTick = player.tickCount;
        this.slot = player.getInventory().selected;
        this.stack = player.getInventory().getSelected();
        this.gun = ((GunItem) stack.getItem()).getModifiedGun(stack);
    }

    /**
     * Tests if the current item the player is holding is the same as the one being reloaded
     *
     * @param player the player to check
     * @return True if it's the same weapon and slot
     */
    private boolean isSameWeapon(Player player)
    {
        return !this.stack.isEmpty() && player.getInventory().selected == this.slot && player.getInventory().getSelected() == this.stack;
    }

    /**
     * @return
     */
    private boolean isWeaponFull()
    {
        CompoundTag tag = this.stack.getOrCreateTag();
        return tag.getInt("AmmoCount") >= GunEnchantmentHelper.getAmmoCapacity(this.stack, this.gun);
    }

    private boolean isWeaponEmpty()
    {
        CompoundTag tag = this.stack.getOrCreateTag();
        return tag.getInt("AmmoCount") == 0;
    }

    private boolean hasNoAmmo(Player player)
    {
        return Gun.findAmmo(player, this.gun.getProjectile().getItem()).stack().isEmpty();
    }

    private boolean canReload(Player player)
    {
        if(gun.getReloads().isMagFed())
        {
            if(this.isWeaponEmpty())
            {
                int deltaTicks = player.tickCount - this.startTick;
                int interval = gun.getReloads().getReloadTimer() + gun.getReloads().getEmptyMagTimer();
                return deltaTicks > interval;
            }
            else
            {
                int deltaTicks = player.tickCount - this.startTick;
                int interval = gun.getReloads().getReloadTimer();
                return deltaTicks > interval;
            }
        }
        else
        {
            int deltaTicks = player.tickCount - this.startTick;
            int interval = GunEnchantmentHelper.getReloadInterval(this.stack);
            return deltaTicks > 0 && deltaTicks % interval == 0;
        }

    }

    public static int ammoInInventory(ItemStack[] ammoStack)
    {
        int result = 0;
        for (ItemStack x: ammoStack)
            result+=x.getCount();
        return result;
    }

    private void shrinkFromAmmoPool(ItemStack[] ammoStack, Player player, int shrinkAmount)
    {
        int shrinkAmt = shrinkAmount;
        ArrayList<ItemStack> stacks = new ArrayList<>();

        for (ItemStack x: ammoStack)
        {
            if(!x.isEmpty())
            {
                int max = Math.min(shrinkAmt, x.getCount());
                x.shrink(max);
                shrinkAmt-=max;
            }
            if(shrinkAmt==0)
                return;
        }
    }

    private void increaseMagAmmo(Player player)
    {
        ItemStack[] ammoStack = Gun.findAmmoStack(player, this.gun.getProjectile().getItem());
        if(ammoStack.length > 0)
        {
            CompoundTag tag = this.stack.getTag();
            int ammoAmount = Math.min(ammoInInventory(ammoStack), GunEnchantmentHelper.getAmmoCapacity(this.stack, this.gun));
            int currentAmmo = tag.getInt("AmmoCount");
            int maxAmmo = GunEnchantmentHelper.getAmmoCapacity(this.stack, this.gun);
            int amount = maxAmmo - currentAmmo;
            if(tag != null)
            {
                if (ammoAmount < amount) {
                    tag.putInt("AmmoCount", currentAmmo + ammoAmount);
                    this.shrinkFromAmmoPool(ammoStack, player, ammoAmount);
                } else {
                    tag.putInt("AmmoCount", maxAmmo);
                    this.shrinkFromAmmoPool(ammoStack, player, amount);
                }
            }
        }

        ResourceLocation reloadSound = this.gun.getSounds().getReload();
        if(reloadSound != null)
        {
            double radius = Config.SERVER.reloadMaxDistance.get();
            S2CMessageGunSound message = new S2CMessageGunSound(reloadSound, SoundSource.PLAYERS, (float) player.getX(), (float) player.getY() + 1.0F, (float) player.getZ(), 1.0F, 1.0F, player.getId(), false, true);
            PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getX(), (player.getY() + 1.0), player.getZ(), radius, player.level.dimension())), message);
        }
    }

    private void increaseAmmo(Player player)
    {
        AmmoContext context = Gun.findAmmo(player, this.gun.getProjectile().getItem());
        ItemStack ammo = context.stack();
        if(!ammo.isEmpty())
        {
            int amount = Math.min(ammo.getCount(), this.gun.getReloads().getReloadAmount());
            CompoundTag tag = this.stack.getTag();
            if(tag != null)
            {
                int maxAmmo = GunEnchantmentHelper.getAmmoCapacity(this.stack, this.gun);
                amount = Math.min(amount, maxAmmo - tag.getInt("AmmoCount"));
                tag.putInt("AmmoCount", tag.getInt("AmmoCount") + amount);
            }
            ammo.shrink(amount);

            // Trigger that the container changed
            Container container = context.container();
            if(container != null)
            {
                container.setChanged();
            }
        }

        ResourceLocation reloadSound = this.gun.getSounds().getReload();
        if(reloadSound != null)
        {
            double radius = Config.SERVER.reloadMaxDistance.get();
            S2CMessageGunSound message = new S2CMessageGunSound(reloadSound, SoundSource.PLAYERS, (float) player.getX(), (float) player.getY() + 1.0F, (float) player.getZ(), 1.0F, 1.0F, player.getId(), false, true);
            PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getX(), (player.getY() + 1.0), player.getZ(), radius, player.level.dimension())), message);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START && !event.player.level.isClientSide)
        {
            Player player = event.player;
            if(ModSyncedDataKeys.RELOADING.getValue(player))
            {
                if(!RELOAD_TRACKER_MAP.containsKey(player))
                {
                    if(!(player.getInventory().getSelected().getItem() instanceof GunItem))
                    {
                        ModSyncedDataKeys.RELOADING.setValue(player, false);
                        return;
                    }
                    RELOAD_TRACKER_MAP.put(player, new ReloadTracker(player));
                }
                ReloadTracker tracker = RELOAD_TRACKER_MAP.get(player);
                if(!tracker.isSameWeapon(player) || tracker.isWeaponFull() || tracker.hasNoAmmo(player))
                {
                    RELOAD_TRACKER_MAP.remove(player);
                    ModSyncedDataKeys.RELOADING.setValue(player, false);
                    return;
                }
                if(tracker.canReload(player))
                {
                    final Player finalPlayer = player;
                    final Gun gun = tracker.gun;
                    if(gun.getReloads().isMagFed()) {
                        tracker.increaseMagAmmo(player);
                    }
                    else if(!gun.getReloads().isMagFed()) {
                        tracker.increaseAmmo(player);
                    }
                    if(tracker.isWeaponFull() || tracker.hasNoAmmo(player))
                    {
                        RELOAD_TRACKER_MAP.remove(player);
                        ModSyncedDataKeys.RELOADING.setValue(player, false);

                        DelayedTask.runAfter(4, () ->
                        {
                            ResourceLocation cockSound = gun.getSounds().getCock();
                            if(cockSound != null && finalPlayer.isAlive())
                            {
                                double radius = Config.SERVER.reloadMaxDistance.get();
                                S2CMessageGunSound messageSound = new S2CMessageGunSound(cockSound, SoundSource.PLAYERS, (float) finalPlayer.getX(), (float) (finalPlayer.getY() + 1.0), (float) finalPlayer.getZ(), 1.0F, 1.0F, finalPlayer.getId(), false, true);
                                PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(finalPlayer.getX(), (finalPlayer.getY() + 1.0), finalPlayer.getZ(), radius, finalPlayer.level.dimension())), messageSound);
                            }
                        });
                    }
                }
            }
            else RELOAD_TRACKER_MAP.remove(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerEvent.PlayerLoggedOutEvent event)
    {
        MinecraftServer server = event.getPlayer().getServer();
        if(server != null)
        {
            server.execute(() -> RELOAD_TRACKER_MAP.remove(event.getPlayer()));
        }
    }
}
