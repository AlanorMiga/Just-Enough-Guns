package ttv.alanorMiga.jeg.item.attachment;

import ttv.alanorMiga.jeg.item.StockItem;
import ttv.alanorMiga.jeg.item.attachment.impl.Stock;

/**
 * An interface to turn an any item into a stock attachment. This is useful if your item extends a
 * custom item class otherwise {@link StockItem} can be used instead of
 * this interface.
 * <p>
 * Author: MrCrayfish
 */
public interface IStock extends IAttachment<Stock>
{
    /**
     * @return The type of this attachment
     */
    @Override
    default Type getType()
    {
        return Type.STOCK;
    }
}
