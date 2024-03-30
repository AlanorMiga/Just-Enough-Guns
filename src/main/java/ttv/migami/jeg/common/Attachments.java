package ttv.migami.jeg.common;

import ttv.migami.jeg.item.attachment.impl.Scope;

/**
 * Author: MrCrayfish
 */
public class Attachments
{
    public static final Scope HOLOGRAPHIC_SIGHT = Scope.builder().aimFovModifier(0.5F).modifiers(GunModifiers.SLOW_ADS).build();
    public static final Scope TELESCOPIC_SIGHT = Scope.builder().aimFovModifier(0.5F).modifiers(GunModifiers.SLOWEST_ADS).build();
}
