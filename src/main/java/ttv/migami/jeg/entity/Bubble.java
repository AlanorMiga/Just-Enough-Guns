package ttv.migami.jeg.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import ttv.migami.jeg.init.ModEntities;

import java.util.List;
import java.util.UUID;

public class Bubble extends Mob implements TraceableEntity {
    private int warmupDelayTicks;
    private int lifeTicks = 140;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    private static final EntityDataAccessor<Boolean> JUST_SPAWNED =
            SynchedEntityData.defineId(Bubble.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DESPAWNING =
            SynchedEntityData.defineId(Bubble.class, EntityDataSerializers.BOOLEAN);

    public Bubble(EntityType<? extends Bubble> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Bubble(Level pLevel, LivingEntity pPlayer, BlockPos pPos) {
        super(ModEntities.BUBBLE.get(), pLevel);
        this.setPos(pPos.getCenter().add(0, -0.5, 0));
        this.setOwner(pPlayer);
        this.noPhysics = true;
    }

    public void setOwner(@javax.annotation.Nullable LivingEntity pOwner) {
        this.owner = pOwner;
        this.ownerUUID = pOwner == null ? null : pOwner.getUUID();
    }

    /**
     * Returns null or the entityliving it was ignited by
     */
    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(JUST_SPAWNED, true);
        this.entityData.define(DESPAWNING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public void tick() {
        super.tick();

        Level level = this.level();

        if (!level.isClientSide)
        {
            BlockPos blockAbove = this.blockPosition().above(4);
            BlockPos blockJustAbove = this.blockPosition().above(3);
            BlockPos emergencyBrake = this.blockPosition().above(2);
            if (level.getBlockState(blockAbove).isSolidRender(level, blockAbove) ||
                    level.getBlockState(blockAbove).isSolidRender(level, blockJustAbove) ||
                            level.getBlockState(blockAbove).isSolidRender(level, emergencyBrake)) {
                this.setDeltaMovement(0, 0, 0);
            } else {
                this.setDeltaMovement(0, 0.1, 0);
            }

            List<Entity> collidedEntities = level.getEntities(this, this.getBoundingBox());
            for (Entity entity : collidedEntities) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (entity instanceof Bubble) {
                        this.remove(RemovalReason.KILLED);
                        onDeath();
                    }

                    if (livingEntity.isOnFire()) {
                        livingEntity.extinguishFire();
                        ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 6, 0.3D, 0.3D, 0.3D, 0.0D);
                    }
                    // Trap entity
                    if (entity instanceof Player) {
                        entity.startRiding(this);
                    }
                    else {
                        livingEntity.startRiding(this);
                    }
                    livingEntity.setAirSupply(livingEntity.getMaxAirSupply());
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false));
                    if (livingEntity.isUnderWater()) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, false, false));
                    }
                }
            }
            ((ServerLevel) level).sendParticles(ParticleTypes.FALLING_WATER, this.getX(), this.getY(), this.getZ(), 1, 1.0D, 0.0D, 1.0D, 0.0D);

            if (--this.warmupDelayTicks < 0) {
                --this.lifeTicks;
                if (this.warmupDelayTicks == -1) {
                    this.setJustSpawned(true);
                    level.playSound(this, this.blockPosition(), SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.PLAYERS, 2F, 1F);
                }
                if (this.lifeTicks < 20) {
                    this.setJustSpawned(false);
                    this.setDespawning(true);
                }
                if (this.lifeTicks < 0) {
                    this.discard();
                }
            }
        }
    }

    public void onDeath() {

    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2.0D);
    }

    public void setJustSpawned(boolean justSpawned) {
        this.entityData.set(JUST_SPAWNED, justSpawned);
    }

    public boolean justSpawned() {
        return this.entityData.get(JUST_SPAWNED);
    }

    public void setDespawning(boolean despawning) {
        this.entityData.set(DESPAWNING, despawning);
    }

    public boolean despawning() {
        return this.entityData.get(DESPAWNING);
    }

}
