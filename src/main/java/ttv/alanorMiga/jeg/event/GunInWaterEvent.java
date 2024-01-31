package ttv.alanorMiga.jeg.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.item.UnderwaterFirearmItem;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GunInWaterEvent
{

    @SubscribeEvent
    public static void preShoot(GunFireEvent.Pre event)
    {
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();

        if (!(heldItem.getItem() instanceof UnderwaterFirearmItem) && player.isUnderWater())
        {
            event.setCanceled(true);
        }
    }

}
