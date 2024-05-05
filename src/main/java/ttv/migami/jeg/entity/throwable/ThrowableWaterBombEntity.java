package ttv.migami.jeg.entity.throwable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import ttv.migami.jeg.entity.projectile.WhirpoolEntity;
import ttv.migami.jeg.init.ModEntities;
import ttv.migami.jeg.init.ModItems;

/**
 * Author: MrCrayfish
 */
public class ThrowableWaterBombEntity extends ThrowableGrenadeEntity
{
    public float rotation;
    public float prevRotation;
    private BlockPos target;

    public ThrowableWaterBombEntity(EntityType<? extends ThrowableGrenadeEntity> entityType, Level worldIn)
    {
        super(entityType, worldIn);
    }

    public ThrowableWaterBombEntity(EntityType<? extends ThrowableGrenadeEntity> entityType, Level world, LivingEntity entity)
    {
        super(entityType, world, entity);
        this.setShouldBounce(false);
        this.setGravityVelocity(0.05F);
        this.setItem(new ItemStack(ModItems.WATER_BOMB.get()));
        this.setMaxLife(20 * 6);
    }

    public ThrowableWaterBombEntity(Level world, LivingEntity entity, int timeLeft)
    {
        super(ModEntities.THROWABLE_WATER_BOMB.get(), world, entity);
        this.setShouldBounce(false);
        this.setGravityVelocity(0.05F);
        this.setItem(new ItemStack(ModItems.WATER_BOMB.get()));
        this.setMaxLife(20 * 6);
    }

    @Override
    protected void defineSynchedData()
    {
    }

    @Override
    public void tick()
    {
        super.tick();
    }

    @Override
    public void particleTick()
    {
        if (this.level().isClientSide)
        {
            if (this.isUnderWater()) {
                for(int i = 0; i < 2; i++) {
                    this.level().addParticle(ParticleTypes.BUBBLE, true, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0);
                    this.level().addParticle(ParticleTypes.BUBBLE, true, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0);
                }
            }
            else {
                for(int i = 0; i < 2; i++) {
                    this.level().addParticle(ParticleTypes.SPLASH, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                    this.level().addParticle(ParticleTypes.SPLASH, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                }
            }
        }
    }

    @Override
    public void onDeath()
    {
        if (this.target != null) {
            WhirpoolEntity.createLandWhirpool(this, null, BlockPos.containing(this.target.getCenter()));
        }
    }

    @Override
    protected void onHit(HitResult result)
    {
        super.onHit(result);

        if (result.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityResult = (EntityHitResult) result;
            Entity entity = entityResult.getEntity();

            this.target = entity.blockPosition();
            this.remove(RemovalReason.KILLED);
            this.onDeath();
        }
        else if (result.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) result;
            BlockPos block = blockHitResult.getBlockPos().above();

            this.target = BlockPos.containing(block.getCenter());
            this.remove(RemovalReason.KILLED);
            this.onDeath();
        }
    }
}
