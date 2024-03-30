package ttv.migami.jeg.init;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.data.sync.Serializers;
import com.mrcrayfish.framework.api.data.sync.SyncedClassKey;
import com.mrcrayfish.framework.api.data.sync.SyncedDataKey;
import ttv.migami.jeg.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Author: MrCrayfish
 */
public class ModSyncedDataKeys
{
    public static final SyncedDataKey<Player, Boolean> AIMING = SyncedDataKey.builder(SyncedClassKey.PLAYER, Serializers.BOOLEAN)
            .id(new ResourceLocation(Reference.MOD_ID, "aiming"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();

    public static final SyncedDataKey<Player, Boolean> SHOOTING = SyncedDataKey.builder(SyncedClassKey.PLAYER, Serializers.BOOLEAN)
            .id(new ResourceLocation(Reference.MOD_ID, "shooting"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();

    public static final SyncedDataKey<Player, Boolean> RELOADING = SyncedDataKey.builder(SyncedClassKey.PLAYER, Serializers.BOOLEAN)
            .id(new ResourceLocation(Reference.MOD_ID, "reloading"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();

    public static final SyncedDataKey<Player, Boolean> UNJAMMING = SyncedDataKey.builder(SyncedClassKey.PLAYER, Serializers.BOOLEAN)
            .id(new ResourceLocation(Reference.MOD_ID, "unjamming"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();

    public static void register()
    {
        FrameworkAPI.registerSyncedDataKey(AIMING);
        FrameworkAPI.registerSyncedDataKey(SHOOTING);
        FrameworkAPI.registerSyncedDataKey(RELOADING);
    }
}
