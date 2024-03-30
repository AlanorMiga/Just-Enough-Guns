package ttv.migami.jeg.network.message;

import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.framework.api.network.PlayMessage;
import ttv.migami.jeg.client.network.ClientPlayHandler;
import ttv.migami.jeg.common.CustomGun;
import ttv.migami.jeg.common.CustomGunLoader;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.common.NetworkGunManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class S2CMessageUpdateGuns extends PlayMessage<S2CMessageUpdateGuns>
{
    private ImmutableMap<ResourceLocation, Gun> registeredGuns;
    private ImmutableMap<ResourceLocation, CustomGun> customGuns;

    public S2CMessageUpdateGuns() {}

    @Override
    public void encode(S2CMessageUpdateGuns message, FriendlyByteBuf buffer)
    {
        Validate.notNull(NetworkGunManager.get());
        Validate.notNull(CustomGunLoader.get());
        NetworkGunManager.get().writeRegisteredGuns(buffer);
        CustomGunLoader.get().writeCustomGuns(buffer);
    }

    @Override
    public S2CMessageUpdateGuns decode(FriendlyByteBuf buffer)
    {
        S2CMessageUpdateGuns message = new S2CMessageUpdateGuns();
        message.registeredGuns = NetworkGunManager.readRegisteredGuns(buffer);
        message.customGuns = CustomGunLoader.readCustomGuns(buffer);
        return message;
    }

    @Override
    public void handle(S2CMessageUpdateGuns message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleUpdateGuns(message));
        supplier.get().setPacketHandled(true);
    }

    public ImmutableMap<ResourceLocation, Gun> getRegisteredGuns()
    {
        return this.registeredGuns;
    }

    public ImmutableMap<ResourceLocation, CustomGun> getCustomGuns()
    {
        return this.customGuns;
    }
}
