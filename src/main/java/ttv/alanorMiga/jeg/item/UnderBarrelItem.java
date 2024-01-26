package ttv.alanorMiga.jeg.item;

import ttv.alanorMiga.jeg.item.attachment.IUnderBarrel;
import ttv.alanorMiga.jeg.item.attachment.impl.UnderBarrel;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;

/**
 * A basic under barrel attachment item implementation with color support
 *
 * Author: MrCrayfish
 */
public class UnderBarrelItem extends AttachmentItem implements IUnderBarrel, IColored
{
    private final UnderBarrel underBarrel;
    private final boolean colored;

    public UnderBarrelItem(UnderBarrel underBarrel, Properties properties)
    {
        super(properties);
        this.underBarrel = underBarrel;
        this.colored = true;
    }

    public UnderBarrelItem(UnderBarrel underBarrel, Properties properties, boolean colored)
    {
        super(properties);
        this.underBarrel = underBarrel;
        this.colored = colored;
    }

    @Override
    public UnderBarrel getProperties()
    {
        return this.underBarrel;
    }

    @Override
    public boolean canColor(ItemStack stack)
    {
        return this.colored;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return enchantment == Enchantments.BINDING_CURSE || super.canApplyAtEnchantingTable(stack, enchantment);
    }
}
