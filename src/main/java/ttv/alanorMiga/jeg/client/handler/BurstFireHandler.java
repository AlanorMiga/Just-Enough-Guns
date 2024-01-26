package ttv.alanorMiga.jeg.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.common.Gun;
import ttv.alanorMiga.jeg.item.GunItem;
import ttv.alanorMiga.jeg.network.PacketHandler;
import ttv.alanorMiga.jeg.network.message.C2SMessageBurst;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BurstFireHandler
{

    @SubscribeEvent
    public static void onMouseButtonEvent(InputEvent.MouseInputEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null)
        {

            ItemStack heldItem = mc.player.getMainHandItem();
            if(heldItem.getItem() instanceof GunItem gunItem)
            {
                Gun gun = gunItem.getModifiedGun(heldItem);
                if (gun.getGeneral().isBurst())
                {
                    if (mc.options.keyAttack.isDown() && event.getAction() == 1)
                    {
                        PacketHandler.getPlayChannel().sendToServer(new C2SMessageBurst());
                    }
                }
            }
        }
    }
}
