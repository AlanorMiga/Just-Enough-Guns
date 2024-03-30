package ttv.migami.jeg.util.math;

import net.minecraft.world.phys.EntityHitResult;
import ttv.migami.jeg.entity.projectile.ProjectileEntity;

/**
 * Author: MrCrayfish
 */
public class ExtendedEntityRayTraceResult extends EntityHitResult
{
    private final boolean headshot;

    public ExtendedEntityRayTraceResult(ProjectileEntity.EntityResult result)
    {
        super(result.getEntity(), result.getHitPos());
        this.headshot = result.isHeadshot();
    }

    public boolean isHeadshot()
    {
        return this.headshot;
    }
}
