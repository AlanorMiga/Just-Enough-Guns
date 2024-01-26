package ttv.alanorMiga.jeg.client;

import com.mrcrayfish.controllable.client.BindingRegistry;
import com.mrcrayfish.controllable.client.ButtonBinding;
import com.mrcrayfish.controllable.client.Buttons;

/**
 * Author: MrCrayfish
 */
public class GunButtonBindings
{
    public static final ButtonBinding SHOOT = new ButtonBinding(Buttons.RIGHT_TRIGGER, "jeg.button.shoot", "button.categories.jeg", GunConflictContext.IN_GAME_HOLDING_WEAPON);
    public static final ButtonBinding AIM = new ButtonBinding(Buttons.LEFT_TRIGGER, "jeg.button.aim", "button.categories.jeg", GunConflictContext.IN_GAME_HOLDING_WEAPON);
    public static final ButtonBinding RELOAD = new ButtonBinding(Buttons.X, "jeg.button.reload", "button.categories.jeg", GunConflictContext.IN_GAME_HOLDING_WEAPON);
    public static final ButtonBinding OPEN_ATTACHMENTS = new ButtonBinding(Buttons.B, "jeg.button.attachments", "button.categories.jeg", GunConflictContext.IN_GAME_HOLDING_WEAPON);
    public static final ButtonBinding STEADY_AIM = new ButtonBinding(Buttons.RIGHT_THUMB_STICK, "jeg.button.steadyAim", "button.categories.jeg", GunConflictContext.IN_GAME_HOLDING_WEAPON);

    public static void register()
    {
        BindingRegistry.getInstance().register(SHOOT);
        BindingRegistry.getInstance().register(AIM);
        BindingRegistry.getInstance().register(RELOAD);
        BindingRegistry.getInstance().register(OPEN_ATTACHMENTS);
        BindingRegistry.getInstance().register(STEADY_AIM);
    }
}
