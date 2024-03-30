package ttv.migami.jeg.client;

import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyConflictContext;
import ttv.migami.jeg.item.GunItem;

/**
 * Author: MrCrayfish
 */
public enum GunConflictContext implements IKeyConflictContext
{
    IN_GAME_HOLDING_WEAPON
    {
        @Override
        public boolean isActive()
        {
            return !KeyConflictContext.GUI.isActive() && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getMainHandItem().getItem() instanceof GunItem;
        }

        @Override
        public boolean conflicts(IKeyConflictContext other)
        {
            return this == other;
        }
    }
}
