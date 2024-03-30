package ttv.migami.jeg.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.item.GunItem;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BurstFireEvent {

    // Not on ModCommonEventBus because it breaks there? And if it ain't broke don't fix it I guess.
    private static int burstCount = 0;

    @SubscribeEvent
    public static void preShoot(GunFireEvent.Pre event)
    {

        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();
        if(heldItem.getItem() instanceof GunItem gunItem)
        {

            Gun gun = gunItem.getModifiedGun(heldItem);
            if (gun.getGeneral().isBurst())
            {
                int serverModifier = event.getEntity().getServer() == null ? 2 : 1;
                if (burstCount / serverModifier >= gun.getGeneral().getBurstAmount())
                {
                    applyBurstTag(heldItem);
                }

                if (isBursting(heldItem)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void postShoot(GunFireEvent.Post event) {

        if (!event.isClient()) {
            ItemStack heldItem = event.getStack();
            if(heldItem.getItem() instanceof GunItem gunItem)
            {
                Gun gun = gunItem.getModifiedGun(heldItem);
                if (gun.getGeneral().isBurst())
                {
                    if (burstCount <= gun.getGeneral().getBurstAmount())
                    {
                        ++burstCount;
                    }
                }
            }
        }
    }

    public static boolean isBursting(ItemStack heldItem) {
        boolean burst = false;

        if(heldItem.getItem() instanceof GunItem gunItem)
        {
            CompoundTag tag = heldItem.getTag();

            Gun gun = gunItem.getModifiedGun(heldItem);
            if (tag != null && gun.getGeneral().isBurst())
            {
                burst = tag.getBoolean("Bursting");
            }
        }
        return burst;
    }

    private static void applyBurstTag(ItemStack heldItem) {

        if(heldItem.getItem() instanceof GunItem gunItem)
        {
            Gun gun = gunItem.getModifiedGun(heldItem);
            if (gun.getGeneral().isBurst())
            {
                CompoundTag tag = heldItem.getOrCreateTag();
                if (!isBursting(heldItem))
                {
                    tag.putBoolean("Bursting", true);
                }
            }
        }
    }

    public static void resetBurst(ItemStack heldItem) {

        if(heldItem.getItem() instanceof GunItem gunItem)
        {
            Gun gun = gunItem.getModifiedGun(heldItem);
            if (gun.getGeneral().isBurst())
            {
                CompoundTag tag = heldItem.getOrCreateTag();
                burstCount = 0;
                if (isBursting(heldItem))
                {
                    tag.remove("Bursting");
                }
            }
        }
    }
}

