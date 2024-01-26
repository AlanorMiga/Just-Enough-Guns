package ttv.alanorMiga.jeg.compat;

import com.mrcrayfish.backpacked.Backpacked;
import com.mrcrayfish.backpacked.core.ModEnchantments;
import com.mrcrayfish.backpacked.inventory.BackpackInventory;
import com.mrcrayfish.backpacked.inventory.BackpackedInventoryAccess;
import ttv.alanorMiga.jeg.common.AmmoContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import ttv.alanorMiga.jeg.common.Gun;

/**
 * Author: MrCrayfish
 */
public class BackpackHelper
{
    public static AmmoContext findAmmo(Player player, ResourceLocation id)
    {
        ItemStack backpack = Backpacked.getBackpackStack(player);
        if(backpack.isEmpty())
            return AmmoContext.NONE;

        if(EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MARKSMAN.get(), backpack) <= 0)
            return AmmoContext.NONE;

        BackpackInventory inventory = ((BackpackedInventoryAccess) player).getBackpackedInventory();
        if(inventory == null)
            return AmmoContext.NONE;

        for(int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if(Gun.isAmmo(stack, id))
            {
                return new AmmoContext(stack, inventory);
            }
        }

        return AmmoContext.NONE;
    }
}
