package ttv.migami.jeg.item.attachment.impl;

import ttv.migami.jeg.interfaces.IGunModifier;

/**
 * An attachment class related to magazines. Use {@link #create(IGunModifier...)} to create an
 * get.
 * <p>
 * Author: MrCrayfish
 */
public class Magazine extends Attachment
{
    private Magazine(IGunModifier... modifier)
    {
        super(modifier);
    }

    /**
     * Creates an magazine get
     *
     * @param modifier an array of gun modifiers
     * @return an magazine get
     */
    public static Magazine create(IGunModifier... modifier)
    {
        return new Magazine(modifier);
    }
}
