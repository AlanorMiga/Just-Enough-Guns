package ttv.alanorMiga.jeg.item;

import ttv.alanorMiga.jeg.client.handler.GunRenderingHandler;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * Author: MrCrayfish
 */
public class AttachmentItem extends Item implements IMeta
{
    public AttachmentItem(Properties properties)
    {
        super(properties);
    }

    /* Dirty hack to apply enchant effect to attachments if gun is enchanted */
    @Override
    public boolean isFoil(ItemStack stack)
    {
        if(FMLEnvironment.dist == Dist.CLIENT)
        {
            ItemStack weapon = GunRenderingHandler.get().getRenderingWeapon();
            if(weapon != null)
            {
                return weapon.getItem().isFoil(weapon);
            }
        }
        return super.isFoil(stack);
    }
}
