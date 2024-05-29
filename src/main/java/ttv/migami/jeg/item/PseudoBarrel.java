package ttv.migami.jeg.item;

import net.minecraft.world.item.ItemStack;
import ttv.migami.jeg.item.attachment.IAttachment;
import ttv.migami.jeg.item.attachment.IBarrel;
import ttv.migami.jeg.item.attachment.impl.Barrel;

public class PseudoBarrel implements IBarrel
{
    private final ItemStack sword;

    public PseudoBarrel(ItemStack sword)
    {
        this.sword = sword;
    }

    @Override
    public IAttachment.Type getType()
    {
        return IAttachment.Type.BARREL;
    }

    @Override
    public Barrel getProperties() {
        return null;
    }

    @Override
    public boolean canAttachTo(ItemStack weapon)
    {
        return true;
    }
}
