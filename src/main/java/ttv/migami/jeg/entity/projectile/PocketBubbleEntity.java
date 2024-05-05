package ttv.migami.jeg.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.entity.Bubble;
import ttv.migami.jeg.entity.Splash;
import ttv.migami.jeg.init.ModParticleTypes;
import ttv.migami.jeg.item.GunItem;

public class PocketBubbleEntity extends ProjectileEntity
{
    public PocketBubbleEntity(EntityType<? extends ProjectileEntity> entityType, Level worldIn)
    {
        super(entityType, worldIn);
    }

    public PocketBubbleEntity(EntityType<? extends ProjectileEntity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon, GunItem item, Gun modifiedGun)
    {
        super(entityType, worldIn, shooter, weapon, item, modifiedGun);
    }

    @Override
    public void tick()
    {
        super.tick();
    }

    @Override
    protected void onProjectileTick()
    {
        if(this.level().isClientSide&& this.tickCount < this.life) {
            if (this.tickCount > 2)
            {
                this.level().addParticle(ModParticleTypes.TYPHOONEE_BEAM.get(), true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
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
    }

    @Override
    protected void onHitEntity(Entity entity, Vec3 hitVec, Vec3 startVec, Vec3 endVec, boolean headshot)
    {
        createWaterExplosion(this, this.shooter, BlockPos.containing(entity.getPosition(1F)));
    }

    @Override
    protected void onHitBlock(BlockState state, BlockPos pos, Direction face, double x, double y, double z)
    {
        createWaterExplosion(this, this.shooter, BlockPos.containing(this.getPosition(1F)));
    }

    @Override
    public void onExpired()
    {
        createWaterExplosion(this, this.shooter, BlockPos.containing(this.getPosition(1F)));
    }

    public static void createWaterExplosion(Entity entity, LivingEntity player, BlockPos pos)
    {
        Level world = entity.level();
        world.addFreshEntity(new Splash(world, player, pos, 20));
    }

    public static void createBubble(Entity entity, LivingEntity player, BlockPos pos)
    {
        Level world = entity.level();
        world.addFreshEntity(new Bubble(world, player, pos));
    }
}