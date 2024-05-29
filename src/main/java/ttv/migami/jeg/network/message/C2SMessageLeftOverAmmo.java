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
public class C2SMessageLeftOverAmmo extends PlayMessage<C2SMessageLeftOverAmmo>
{
    @Override
    public void encode(C2SMessageLeftOverAmmo message, FriendlyByteBuf buffer) {}

    @Override
    public C2SMessageLeftOverAmmo decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageLeftOverAmmo();
    }

    @Override
    public void handle(C2SMessageLeftOverAmmo message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null && !player.isSpectator())
            {
                ServerPlayHandler.handleExtraAmmo(player);
            }
        });
        supplier.get().setPacketHandled(true);    }
}
