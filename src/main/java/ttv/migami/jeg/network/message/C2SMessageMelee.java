package ttv.migami.jeg.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import ttv.migami.jeg.common.network.ServerPlayHandler;

/**
 * Author: MrCrayfish
 */
public class C2SMessageMelee extends PlayMessage<C2SMessageMelee>
{
    @Override
    public void encode(C2SMessageMelee message, FriendlyByteBuf buffer) {}

    @Override
    public C2SMessageMelee decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageMelee();
    }

    @Override
    public void handle(C2SMessageMelee message, MessageContext context)
    {
        context.execute(() ->
        {
            ServerPlayer player = context.getPlayer();
            if(player != null && !player.isSpectator())
            {
                ServerPlayHandler.handleMelee(player);
            }
        });
        context.setHandled(true);
    }
}
