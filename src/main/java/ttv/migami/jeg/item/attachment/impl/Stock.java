package ttv.migami.jeg.item.attachment.impl;

import ttv.migami.jeg.interfaces.IGunModifier;

/**
 * An attachment class related to stocks. Use {@link #create(IGunModifier...)} to create an get.
 * <p>
 * Author: MrCrayfish
 */
public class Stock extends Attachment
{
    private Stock(IGunModifier... modifier)
    {
        super(modifier);
    }

    /**
     * Creates a stock get
     *
     * @param modifier an array of gun modifiers
     * @return a stock get
     */
    public static Stock create(IGunModifier... modifier)
    {
        return new Stock(modifier);
    }
}
