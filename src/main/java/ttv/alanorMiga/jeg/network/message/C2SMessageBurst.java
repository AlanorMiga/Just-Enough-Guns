package ttv.alanorMiga.jeg.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import ttv.alanorMiga.jeg.common.network.ServerPlayHandler;

import java.util.function.Supplier;

public class C2SMessageBurst extends PlayMessage<C2SMessageBurst> {

    @Override
    public void encode(C2SMessageBurst message, FriendlyByteBuf buffer) {
    }

    @Override
    public C2SMessageBurst decode(FriendlyByteBuf buffer) {
        return new C2SMessageBurst();
    }

    @Override
    public void handle(C2SMessageBurst message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            if (player != null) {
                ServerPlayHandler.handleBurst(player);
            }

        });
        supplier.get().setPacketHandled(true);
    }

}
