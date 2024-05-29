package ttv.migami.jeg.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;
import ttv.migami.jeg.client.KeyBinds;
import ttv.migami.jeg.network.PacketHandler;
import ttv.migami.jeg.network.message.C2SMessageMelee;

/**
 * Author: MrCrayfish
 */
public class MeleeHandler
{
    private static MeleeHandler instance;

    public static MeleeHandler get()
    {
        if(instance == null)
        {
            instance = new MeleeHandler();
        }
        return instance;
    }

    private MeleeHandler()
    {
    }

    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event)
    {
        Player player = Minecraft.getInstance().player;
        if(player == null)
            return;

        if(KeyBinds.KEY_MELEE.isDown() && event.getAction() == GLFW.GLFW_PRESS)
        {
            PacketHandler.getPlayChannel().sendToServer(new C2SMessageMelee());
        }

    }

}
