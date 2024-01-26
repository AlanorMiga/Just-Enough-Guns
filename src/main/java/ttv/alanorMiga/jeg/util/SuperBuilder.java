package ttv.alanorMiga.jeg.util;

/**
 * Author: MrCrayfish
 */
public abstract class SuperBuilder<R, T extends SuperBuilder<R, T>>
{
    public abstract R build();

    @SuppressWarnings("unchecked")
    protected final T self()
    {
        return (T) this;
    }
}
