package ttv.migami.jeg.item.attachment;

import ttv.migami.jeg.item.attachment.impl.Magazine;

/**
 * An interface to turn an any item into a under barrel attachment. This is useful if your item
 * extends a custom item class otherwise {@link ttv.migami.jeg.item.MagazineItem} can be
 * used instead of this interface.
 * <p>
 * Author: MrCrayfish
 */
public interface IMagazine extends IAttachment<Magazine>
{
    /**
     * @return The type of this attachment
     */
    @Override
    default Type getType()
    {
        return Type.MAGAZINE;
    }
}
