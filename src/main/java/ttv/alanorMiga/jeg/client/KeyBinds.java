package ttv.alanorMiga.jeg.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;
import ttv.alanorMiga.jeg.Config;

/**
 * Author: MrCrayfish
 */
public class KeyBinds
{
    public static final KeyMapping KEY_RELOAD = new KeyMapping("key.jeg.reload", GLFW.GLFW_KEY_R, "key.categories.jeg");
    public static final KeyMapping KEY_UNLOAD = new KeyMapping("key.jeg.unload", GLFW.GLFW_KEY_U, "key.categories.jeg");
    public static final KeyMapping KEY_ATTACHMENTS = new KeyMapping("key.jeg.attachments", GLFW.GLFW_KEY_Z, "key.categories.jeg");

    public static void registerKeyMappings(RegisterKeyMappingsEvent event)
    {
        event.register(KEY_RELOAD);
        event.register(KEY_UNLOAD);
        event.register(KEY_ATTACHMENTS);
    }

    public static KeyMapping getAimMapping()
    {
        Minecraft mc = Minecraft.getInstance();
        return Config.CLIENT.controls.flipControls.get() ? mc.options.keyAttack : mc.options.keyUse;
    }

    public static KeyMapping getShootMapping()
    {
        Minecraft mc = Minecraft.getInstance();
        return Config.CLIENT.controls.flipControls.get() ? mc.options.keyUse : mc.options.keyAttack;
    }
}
