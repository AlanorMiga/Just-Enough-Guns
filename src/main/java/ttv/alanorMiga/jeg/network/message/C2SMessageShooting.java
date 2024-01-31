package ttv.alanorMiga.jeg.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import ttv.alanorMiga.jeg.init.ModSyncedDataKeys;

/**
 * Author: MrCrayfish
 */
public class C2SMessageShooting extends PlayMessage<C2SMessageShooting>
{
    private boolean shooting;

    public C2SMessageShooting() {}

    public C2SMessageShooting(boolean shooting)
    {
        this.shooting = shooting;
    }

    @Override
    public void encode(C2SMessageShooting message, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(message.shooting);
    }

    @Override
    public C2SMessageShooting decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageShooting(buffer.readBoolean());
    }

    @Override
    public void handle(C2SMessageShooting message, MessageContext context)
    {
        context.execute(() ->
        {
            ServerPlayer player = context.getPlayer();
            if(player != null)
            {
                ModSyncedDataKeys.SHOOTING.setValue(player, message.shooting);
            }
        });
        context.setHandled(true);
    }
}