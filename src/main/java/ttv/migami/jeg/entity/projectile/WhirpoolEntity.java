package ttv.migami.jeg.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import ttv.migami.jeg.Config;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.entity.Splash;
import ttv.migami.jeg.init.ModParticleTypes;
import ttv.migami.jeg.interfaces.IExplosionDamageable;
import ttv.migami.jeg.item.GunItem;
import ttv.migami.jeg.world.ProjectileExplosion;

public class WhirpoolEntity extends ProjectileEntity
{
    public WhirpoolEntity(EntityType<? extends ProjectileEntity> entityType, Level worldIn)
    {
        super(entityType, worldIn);
    }

    public WhirpoolEntity(EntityType<? extends ProjectileEntity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon, GunItem item, Gun modifiedGun)
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
        if(this.isUnderWater())
        {
            createWhirpool(this, Config.COMMON.missiles.explosionRadius.get().floatValue(), false);
        }
        else
        {
            createLandWhirpool(this, this.shooter, BlockPos.containing(entity.getPosition(1F)));
        }
    }

    @Override
    protected void onHitBlock(BlockState state, BlockPos pos, Direction face, double x, double y, double z)
    {
        if(this.isUnderWater())
        {
            createWhirpool(this, Config.COMMON.missiles.explosionRadius.get().floatValue(), false);
        }
        else
        {
            createLandWhirpool(this, this.shooter, BlockPos.containing(this.getPosition(1F)));
        }
    }

    @Override
    public void onExpired()
    {
        if(this.isUnderWater())
        {
            createWhirpool(this, Config.COMMON.missiles.explosionRadius.get().floatValue(), false);
        }
    }

    public static void createWhirpool(Entity entity, float radius, boolean forceNone)
    {
        Level world = entity.level();
        if(world.isClientSide())
            return;

        DamageSource source = entity instanceof ProjectileEntity projectile ? entity.damageSources().explosion(entity, projectile.getShooter()) : null;
        Explosion.BlockInteraction mode = Config.COMMON.gameplay.griefing.enableBlockRemovalOnExplosions.get() && !forceNone ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.KEEP;
        Explosion explosion = new ProjectileExplosion(world, entity, source, null, entity.getX(), entity.getY(), entity.getZ(), radius / 2, false, mode);

        if(net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion))
            return;

        // Do explosion logic
        explosion.explode();
        explosion.finalizeExplosion(true);

        // Send event to blocks that are exploded (none if mode is none)
        explosion.getToBlow().forEach(pos ->
        {
            if(world.getBlockState(pos).getBlock() instanceof IExplosionDamageable)
            {
                ((IExplosionDamageable) world.getBlockState(pos).getBlock()).onProjectileExploded(world, world.getBlockState(pos), pos, entity);
            }
        });

        // Clears the affected blocks if mode is none
        if(!explosion.interactsWithBlocks())
        {
            explosion.clearToBlow();
        }

        for(ServerPlayer player : ((ServerLevel) world).players())
        {
            if(player.distanceToSqr(entity.getX(), entity.getY(), entity.getZ()) < 4096)
            {
                player.connection.send(new ClientboundExplodePacket(entity.getX(), entity.getY(), entity.getZ(), radius, explosion.getToBlow(), explosion.getHitPlayers().get(player)));
            }
        }
    }

    public static void createLandWhirpool(Entity entity, LivingEntity player, BlockPos pos)
    {
        Level world = entity.level();
        world.addFreshEntity(new Splash(world, player, pos));
    }
}