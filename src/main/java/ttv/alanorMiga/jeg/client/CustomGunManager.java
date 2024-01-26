package ttv.alanorMiga.jeg.client;

import com.mrcrayfish.framework.api.data.login.ILoginData;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.common.CustomGun;
import ttv.alanorMiga.jeg.common.CustomGunLoader;
import ttv.alanorMiga.jeg.init.ModItems;
import ttv.alanorMiga.jeg.network.message.S2CMessageUpdateGuns;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public class CustomGunManager
{
    private static Map<ResourceLocation, CustomGun> customGunMap;

    public static boolean updateCustomGuns(S2CMessageUpdateGuns message)
    {
        return updateCustomGuns(message.getCustomGuns());
    }

    private static boolean updateCustomGuns(Map<ResourceLocation, CustomGun> customGunMap)
    {
        CustomGunManager.customGunMap = customGunMap;
        return true;
    }

    public static void fill(NonNullList<ItemStack> items)
    {
        if(customGunMap != null)
        {
            customGunMap.forEach((id, gun) ->
            {
                ItemStack stack = new ItemStack(ModItems.ASSAULT_RIFLE.get());
                stack.setHoverName(new TranslatableComponent("item." + id.getNamespace() + "." + id.getPath() + ".name"));
                CompoundTag tag = stack.getOrCreateTag();
                tag.put("Model", gun.getModel().save(new CompoundTag()));
                tag.put("Gun", gun.getGun().serializeNBT());
                tag.putBoolean("Custom", true);
                tag.putInt("AmmoCount", gun.getGun().getReloads().getMaxAmmo());
                items.add(stack);
            });
        }
    }

    @SubscribeEvent
    public static void onClientDisconnect(ClientPlayerNetworkEvent.LoggedOutEvent event)
    {
        customGunMap = null;
    }

    public static class LoginData implements ILoginData
    {
        @Override
        public void writeData(FriendlyByteBuf buffer)
        {
            Validate.notNull(CustomGunLoader.get());
            CustomGunLoader.get().writeCustomGuns(buffer);
        }

        @Override
        public Optional<String> readData(FriendlyByteBuf buffer)
        {
            Map<ResourceLocation, CustomGun> customGuns = CustomGunLoader.readCustomGuns(buffer);
            CustomGunManager.updateCustomGuns(customGuns);
            return Optional.empty();
        }
    }
}
