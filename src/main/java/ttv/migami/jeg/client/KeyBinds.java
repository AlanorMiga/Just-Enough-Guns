package ttv.migami.jeg.client;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import org.lwjgl.glfw.GLFW;

/**
 * Author: MrCrayfish
 */
public class KeyBinds
{
    public static final KeyMapping KEY_RELOAD = new KeyMapping("key.jeg.reload", GLFW.GLFW_KEY_R, "key.categories.jeg");
    public static final KeyMapping KEY_UNLOAD = new KeyMapping("key.jeg.unload", GLFW.GLFW_KEY_U, "key.categories.jeg");
    public static final KeyMapping KEY_ATTACHMENTS = new KeyMapping("key.jeg.attachments", GLFW.GLFW_KEY_Z, "key.categories.jeg");
    public static final KeyMapping KEY_MELEE = new KeyMapping("key.jeg.melee", GLFW.GLFW_KEY_V, "key.categories.jeg");

    public static void register()
    {
        ClientRegistry.registerKeyBinding(KEY_RELOAD);
        ClientRegistry.registerKeyBinding(KEY_UNLOAD);
        ClientRegistry.registerKeyBinding(KEY_ATTACHMENTS);
        ClientRegistry.registerKeyBinding(KEY_MELEE);
    }
}
