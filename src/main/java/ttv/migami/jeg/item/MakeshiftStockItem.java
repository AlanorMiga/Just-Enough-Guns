package ttv.migami.jeg.item;

import ttv.migami.jeg.item.attachment.impl.Stock;

public class MakeshiftStockItem extends StockItem {

    public MakeshiftStockItem(Stock stock, Properties properties) {
        super(stock, properties);
    }

    public MakeshiftStockItem(Stock stock, Properties properties, boolean colored) {
        super(stock, properties, colored);
    }

}
