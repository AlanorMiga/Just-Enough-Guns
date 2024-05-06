package ttv.migami.jeg.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ttv.migami.jeg.JustEnoughGuns;
import ttv.migami.jeg.common.FireMode;
import ttv.migami.jeg.common.GripType;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.compat.PlayerReviveHelper;
import ttv.migami.jeg.event.GunFireEvent;
import ttv.migami.jeg.item.GunItem;
import ttv.migami.jeg.network.PacketHandler;
import ttv.migami.jeg.network.message.C2SMessageBurst;
import ttv.migami.jeg.network.message.C2SMessagePreFireSound;
import ttv.migami.jeg.network.message.C2SMessageShoot;
import ttv.migami.jeg.network.message.C2SMessageShooting;
import ttv.migami.jeg.util.GunEnchantmentHelper;
import ttv.migami.jeg.util.GunModifierHelper;

/**
 * Author: MrCrayfish
 */
public class ShootingHandler
{
    private static ShootingHandler instance;
    private int fireTimer;

    public static ShootingHandler get()
    {
        if(instance == null)
        {
            instance = new ShootingHandler();
        }
        return instance;
    }

    private boolean shooting;

    private ShootingHandler() {}

    private boolean isInGame()
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.getOverlay() != null)
            return false;
        if(mc.screen != null)
            return false;
        if(!mc.mouseHandler.isMouseGrabbed())
            return false;
        return mc.isWindowActive();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMouseClick(InputEvent.ClickInputEvent event)
    {
        if(event.isCanceled())
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if(player == null)
            return;

        if(PlayerReviveHelper.isBleeding(player))
            return;

        if(event.isAttack())
        {
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunItem gunItem)
            {
                event.setSwingHand(false);
                event.setCanceled(true);
            }
        }
        else if(event.isUseItem())
        {
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunItem gunItem)
            {
                if(event.getHand() == InteractionHand.OFF_HAND)
                {
                    // Allow shields to be used if weapon is one-handed
                    if(player.getOffhandItem().getItem() == Items.SHIELD)
                    {
                        Gun modifiedGun = gunItem.getModifiedGun(heldItem);
                        if(modifiedGun.getGeneral().getGripType() == GripType.ONE_HANDED)
                        {
                            return;
                        }
                    }
                    event.setCanceled(true);
                    event.setSwingHand(false);
                    return;
                }
                if(AimingHandler.get().isZooming() && AimingHandler.get().isLookingAtInteractableBlock())
                {
                    event.setCanceled(true);
                    event.setSwingHand(false);
                }
            }
        }
    }

    @SubscribeEvent
    public void onHandleShooting(TickEvent.ClientTickEvent event)
    {
        if(event.phase != TickEvent.Phase.START)
            return;

        if(!this.isInGame())
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if(player != null)
        {
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunItem gunItem && (Gun.hasAmmo(heldItem) || player.isCreative()) && !PlayerReviveHelper.isBleeding(player))
            {
                boolean shooting = mc.options.keyAttack.isDown();
                if(JustEnoughGuns.controllableLoaded)
                {
                    shooting |= ControllerHandler.isShooting();
                }
                if(shooting)
                {
                    if(!this.shooting)
                    {
                        this.shooting = true;
                        Gun gun = gunItem.getModifiedGun(heldItem);
                        if (gun.getGeneral().getFireMode() == FireMode.BURST) {
                            PacketHandler.getPlayChannel().sendToServer(new C2SMessageBurst());
                        }
                        PacketHandler.getPlayChannel().sendToServer(new C2SMessageShooting(true));
                    }
                }
                else if(this.shooting)
                {
                    this.shooting = false;
                    PacketHandler.getPlayChannel().sendToServer(new C2SMessageShooting(false));
                }
            }
            else if(this.shooting)
            {
                this.shooting = false;
                PacketHandler.getPlayChannel().sendToServer(new C2SMessageShooting(false));
            }
        }
        else
        {
            this.shooting = false;
        }
    }

    // Props to Moon-404 for the double-tap fix!
    @SubscribeEvent
    public void onPostClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase != TickEvent.Phase.END)
            return;

        if(!isInGame())
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if(player != null)
        {
            if(PlayerReviveHelper.isBleeding(player))
                return;

            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunItem)
            {
                Gun gun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);
                if(!mc.options.keyAttack.isDown() && gun.getGeneral().getFireTimer() != 0)
                {
                    fireTimer = gun.getGeneral().getFireTimer();
                }
                if(mc.options.keyAttack.isDown())
                {
                    if(gun.getGeneral().getFireTimer() != 0)
                    {
                        ItemCooldowns tracker = player.getCooldowns();
                        if(fireTimer > 0 && !tracker.isOnCooldown(heldItem.getItem())) {
                            if (fireTimer == gun.getGeneral().getFireTimer() - 2)
                            {
                                PacketHandler.getPlayChannel().sendToServer(new C2SMessagePreFireSound(player));
                            }
                            // If the player is in water, reduce the preFiring in half
                            if (player.isUnderWater()) {
                                fireTimer--;
                            }
                            fireTimer--;
                        } else {
                            // Execute after preFire timer ends
                            this.fire(player, heldItem);
                            if (gun.getGeneral().getFireMode() == FireMode.SEMI_AUTO || gun.getGeneral().getFireMode() == FireMode.PULSE)
                            {
                                mc.options.keyAttack.setDown(false);
                                fireTimer = gun.getGeneral().getFireTimer();
                            }
                        }
                    }
                    else {
                        this.fire(player, heldItem);
                        if(gun.getGeneral().getFireMode() == FireMode.SEMI_AUTO)
                        {
                            mc.options.keyAttack.setDown(false);
                        }
                    }
                }
            }
        }
    }

    public void fire(Player player, ItemStack heldItem)
    {
        if(!(heldItem.getItem() instanceof GunItem))
            return;

        if(!Gun.hasAmmo(heldItem) && !player.isCreative())
            return;
        
        if(player.isSpectator())
            return;

        if(player.getUseItem().getItem() == Items.SHIELD)
            return;

        ItemCooldowns tracker = player.getCooldowns();
        int maxDamage = heldItem.getMaxDamage();
        int currentDamage = heldItem.getDamageValue();
        if(!tracker.isOnCooldown(heldItem.getItem()))
        {
            GunItem gunItem = (GunItem) heldItem.getItem();
            Gun modifiedGun = gunItem.getModifiedGun(heldItem);
            ItemStack stack = player.getMainHandItem();

            if(MinecraftForge.EVENT_BUS.post(new GunFireEvent.Pre(player, heldItem)))
                return;

            if(stack.isDamageableItem() && currentDamage < (maxDamage - 1))
            {
            int rate = GunEnchantmentHelper.getRate(heldItem, modifiedGun);
            rate = GunModifierHelper.getModifiedRate(heldItem, rate);
            tracker.addCooldown(heldItem.getItem(), rate);
            PacketHandler.getPlayChannel().sendToServer(new C2SMessageShoot(player));

            MinecraftForge.EVENT_BUS.post(new GunFireEvent.Post(player, heldItem));
            }
            else if(!stack.isDamageableItem())
            {
                int rate = GunEnchantmentHelper.getRate(heldItem, modifiedGun);
                rate = GunModifierHelper.getModifiedRate(heldItem, rate);
                tracker.addCooldown(heldItem.getItem(), rate);
                PacketHandler.getPlayChannel().sendToServer(new C2SMessageShoot(player));

                MinecraftForge.EVENT_BUS.post(new GunFireEvent.Post(player, heldItem));
            }
        }
    }
}
