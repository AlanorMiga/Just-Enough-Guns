package ttv.migami.jeg.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import ttv.migami.jeg.item.attachment.IMagazine;
import ttv.migami.jeg.item.attachment.impl.Magazine;

/**
 * A basic magazine attachment item implementation with color support
 *
 * Credit: MrCrayfish
 */
public class MagazineItem extends AttachmentItem implements IMagazine, IColored
{
    private final Magazine magazine;
    private final boolean colored;

    public MagazineItem(Magazine magazine, Properties properties)
    {
        super(properties);
        this.magazine = magazine;
        this.colored = true;
    }

    public MagazineItem(Magazine magazine, Properties properties, boolean colored)
    {
        super(properties);
        this.magazine = magazine;
        this.colored = colored;
    }

    @Override
    public Magazine getProperties()
    {
        return this.magazine;
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
