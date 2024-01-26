package ttv.alanorMiga.jeg.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.common.Gun;
import ttv.alanorMiga.jeg.event.GunFireEvent.Post;
import ttv.alanorMiga.jeg.init.ModSounds;
import ttv.alanorMiga.jeg.item.GunItem;
import ttv.alanorMiga.jeg.item.attachment.IAttachment;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GunDurabilityEvent {

    @SubscribeEvent
    public static void postShoot(Post event) {
        Player player = event.getPlayer();
        Level level = event.getPlayer().getLevel();
        ItemStack heldItem = player.getMainHandItem();
        CompoundTag tag = heldItem.getTag();

        if (heldItem.isDamageableItem() && tag != null) {
            if (tag.getInt("AmmoCount") >= 1) {
                damageGun(heldItem, level, player);
                damageAttachments(heldItem, level, player);
            }
            if (heldItem.getDamageValue() >= (heldItem.getMaxDamage() / 1.5)) {
                level.playSound(player, player.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 1.75F);
            }
        }
    }

    @SubscribeEvent
    public static void preShoot(GunFireEvent.Pre event) {
        Player player = event.getPlayer();
        Level level = event.getPlayer().getLevel();
        ItemStack heldItem = player.getMainHandItem();
        Gun gun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);
        CompoundTag tag = heldItem.getTag();

        if (heldItem.isDamageableItem() && tag != null) {
            if (heldItem.getDamageValue() == (heldItem.getMaxDamage() - 1)) {
                level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                event.getPlayer().getCooldowns().addCooldown(event.getStack().getItem(), gun.getGeneral().getRate());
                event.setCanceled(true);
            }
            //This is the Jam function
            int maxDamage = heldItem.getMaxDamage();
            int currentDamage = heldItem.getDamageValue();
            if (currentDamage >= maxDamage / 1.5) {
                if (Math.random() >= 0.975) {
                    event.getPlayer().playSound(ModSounds.ITEM_PISTOL_COCK.get(), 1.0F, 1.0F);
                    int coolDown = gun.getGeneral().getRate() * 10;
                    if (coolDown > 60) {
                        coolDown = 60;
                    }
                    event.getPlayer().getCooldowns().addCooldown(event.getStack().getItem(), (coolDown));
                    event.setCanceled(true);
                }
            } else if (tag.getInt("AmmoCount") >= 1) {
                broken(heldItem, level, player);
            }
        }
    }

    public static void broken(ItemStack stack, Level level, Player player) {
        int maxDamage = stack.getMaxDamage();
        int currentDamage = stack.getDamageValue();
        if (currentDamage >= (maxDamage - 2)) {
            level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    public static void damageGun(ItemStack stack, Level level, Player player) {
        if (!player.getAbilities().instabuild) {
            if (stack.isDamageableItem()) {
                int maxDamage = stack.getMaxDamage();
                int currentDamage = stack.getDamageValue();
                if (currentDamage >= (maxDamage - 1)) {
                    if (currentDamage >= (maxDamage - 2)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        //stack.shrink(1);
                    }
                } else {
                    stack.hurtAndBreak(1, player, null);
                }
            }
        }
    }

    public static void damageAttachments(ItemStack stack, Level level, Player player) {
        if (!player.getAbilities().instabuild) {
            if (stack.getItem() instanceof GunItem) {

                //Scope
                ItemStack scopeStack = Gun.getAttachment(IAttachment.Type.SCOPE, stack);
                if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.SCOPE) && scopeStack.isDamageableItem()) {
                    int maxDamage = scopeStack.getMaxDamage();
                    int currentDamage = scopeStack.getDamageValue();
                    if (currentDamage == (maxDamage - 1)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        Gun.removeAttachment(stack, "Scope");
                    } else {
                        scopeStack.hurtAndBreak(1, player, null);
                    }
                }

                //Barrel
                ItemStack barrelStack = Gun.getAttachment(IAttachment.Type.BARREL, stack);
                if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.BARREL) && barrelStack.isDamageableItem()) {
                    int maxDamage = barrelStack.getMaxDamage();
                    int currentDamage = barrelStack.getDamageValue();
                    if (currentDamage == (maxDamage - 1)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        Gun.removeAttachment(stack, "Barrel");
                    } else {
                        barrelStack.hurtAndBreak(1, player, null);
                    }
                }

                //Stock
                ItemStack stockStack = Gun.getAttachment(IAttachment.Type.STOCK, stack);
                if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.STOCK) && stockStack.isDamageableItem()) {
                    int maxDamage = stockStack.getMaxDamage();
                    int currentDamage = stockStack.getDamageValue();
                    if (currentDamage == (maxDamage - 1)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        Gun.removeAttachment(stack, "Stock");
                    } else {
                        stockStack.hurtAndBreak(1, player, null);
                    }
                }

                //Under Barrel
                ItemStack underBarrelStack = Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack);
                if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.UNDER_BARREL) && underBarrelStack.isDamageableItem()) {
                    int maxDamage = underBarrelStack.getMaxDamage();
                    int currentDamage = underBarrelStack.getDamageValue();
                    if (currentDamage == (maxDamage - 1)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        Gun.removeAttachment(stack, "Under_Barrel");
                    } else {
                        underBarrelStack.hurtAndBreak(1, player, null);
                    }
                }
            }
        }
    }

}
