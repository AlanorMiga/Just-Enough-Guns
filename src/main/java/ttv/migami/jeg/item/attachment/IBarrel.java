package ttv.migami.jeg.item.attachment;

import ttv.migami.jeg.item.BarrelItem;
import ttv.migami.jeg.item.attachment.impl.Barrel;

/**
 * An interface to turn an any item into a barrel attachment. This is useful if your item extends a
 * custom item class otherwise {@link BarrelItem} can be used instead of
 * this interface.
 * <p>
 * Author: Ocelot, MrCrayfish
 */
public interface IBarrel extends IAttachment<Barrel>
{
    /**
     * @return The type of this attachment
     */
    @Override
    default Type getType()
    {
        return Type.BARREL;
    }
}
