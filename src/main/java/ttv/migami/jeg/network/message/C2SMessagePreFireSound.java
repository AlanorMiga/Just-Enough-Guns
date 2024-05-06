package ttv.migami.jeg.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import ttv.migami.jeg.common.network.ServerPlayHandler;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class C2SMessagePreFireSound extends PlayMessage<C2SMessagePreFireSound>
{

    public C2SMessagePreFireSound() {}

    public C2SMessagePreFireSound(Player player)
    {
    }

    @Override
    public void encode(C2SMessagePreFireSound message, FriendlyByteBuf buffer)
    {

    }

    @Override
    public C2SMessagePreFireSound decode(FriendlyByteBuf buffer)
    {
        return new C2SMessagePreFireSound();
    }

    @Override
    public void handle(C2SMessagePreFireSound message, Supplier<NetworkEvent.Context> context)
    {
        ServerPlayer player = context.get().getSender();
        context.get().enqueueWork(() -> ServerPlayHandler.handlePreFireSound(message, player));
        context.get().setPacketHandled(true);
    }
}