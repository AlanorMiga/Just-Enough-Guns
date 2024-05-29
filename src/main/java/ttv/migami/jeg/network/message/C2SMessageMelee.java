package ttv.migami.jeg.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import ttv.migami.jeg.common.network.ServerPlayHandler;

import java.util.function.Supplier;

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
    public void handle(C2SMessageMelee message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null && !player.isSpectator())
            {
                ServerPlayHandler.handleMelee(player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
