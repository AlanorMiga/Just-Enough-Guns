package ttv.alanorMiga.jeg.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ttv.alanorMiga.jeg.common.Gun;

/*
 * Makes so Assault Rifles can be assigned with different features.
 */
public class MakeshiftShotgunItem extends GunItem {

    public MakeshiftShotgunItem(Properties properties) {

        super(properties);

    }

    private static final int COOLDOWN_TICKS = 20; // 20 ticks = 1 second
    private static int cooldownRemaining = 0;
    private static boolean cooldownActive = false;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (cooldownRemaining > 0) {
                cooldownRemaining--;
            }
        }
    }

    @SubscribeEvent
    public void onReequipAnimation(PlayerEvent.ItemPickupEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();
        Gun gun = ((GunItem) mainHandItem.getItem()).getModifiedGun(mainHandItem);
        if (!cooldownActive) {
            // Do whatever you want the player to do when they select an item here
            cooldownActive = true;
            cooldownRemaining = gun.getGeneral().getRate() * 4;
        }
        else {
            cooldownActive = false;
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        Gun gun = ((GunItem) stack.getItem()).getModifiedGun(stack);
        return gun.getGeneral().getRate() * 4;
    }

    //Disables the enchantment foil to allow a different kind of customization.
    public boolean isFoil(ItemStack stack) {
        return false;
    }

}