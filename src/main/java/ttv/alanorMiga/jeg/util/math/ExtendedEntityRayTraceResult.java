package ttv.alanorMiga.jeg.util.math;

import ttv.alanorMiga.jeg.entity.ProjectileEntity;
import net.minecraft.world.phys.EntityHitResult;

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
