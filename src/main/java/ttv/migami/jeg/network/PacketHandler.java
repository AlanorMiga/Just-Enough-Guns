package ttv.migami.jeg.network;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.network.FrameworkNetwork;
import com.mrcrayfish.framework.api.network.MessageDirection;
import net.minecraft.resources.ResourceLocation;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.network.message.*;

public class PacketHandler
{
    private static FrameworkNetwork playChannel;

    public static void init()
    {
        playChannel = FrameworkAPI.createNetworkBuilder(new ResourceLocation(Reference.MOD_ID, "play"), 1)
                .registerPlayMessage(C2SMessageAim.class, MessageDirection.PLAY_SERVER_BOUND)
                .registerPlayMessage(C2SMessageReload.class, MessageDirection.PLAY_SERVER_BOUND)
                .registerPlayMessage(C2SMessageShoot.class, MessageDirection.PLAY_SERVER_BOUND)
                .registerPlayMessage(C2SMessageBurst.class, MessageDirection.PLAY_SERVER_BOUND)
                .registerPlayMessage(C2SMessageUnload.class, MessageDirection.PLAY_SERVER_BOUND)
                .registerPlayMessage(S2CMessageStunGrenade.class, MessageDirection.PLAY_CLIENT_BOUND)
                .registerPlayMessage(C2SMessageCraft.class, MessageDirection.PLAY_SERVER_BOUND)
                .registerPlayMessage(S2CMessageBulletTrail.class, MessageDirection.PLAY_CLIENT_BOUND)
                .registerPlayMessage(C2SMessageAttachments.class, MessageDirection.PLAY_SERVER_BOUND)
                .registerPlayMessage(S2CMessageUpdateGuns.class, MessageDirection.PLAY_CLIENT_BOUND)
                .registerPlayMessage(S2CMessageBlood.class, MessageDirection.PLAY_CLIENT_BOUND)
                .registerPlayMessage(C2SMessageShooting.class, MessageDirection.PLAY_SERVER_BOUND)
                .registerPlayMessage(S2CMessageGunSound.class, MessageDirection.PLAY_CLIENT_BOUND)
                .registerPlayMessage(S2CMessageProjectileHitBlock.class, MessageDirection.PLAY_CLIENT_BOUND)
                .registerPlayMessage(S2CMessageProjectileHitEntity.class, MessageDirection.PLAY_CLIENT_BOUND)
                .registerPlayMessage(S2CMessageRemoveProjectile.class, MessageDirection.PLAY_CLIENT_BOUND)
                .build();
    }

    public static FrameworkNetwork getPlayChannel()
    {
        return playChannel;
    }

}
