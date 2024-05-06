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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import ttv.migami.jeg.common.ModTags;
import ttv.migami.jeg.init.ModEntities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Splash extends Entity implements TraceableEntity {
    private int warmupDelayTicks;
    private int lifeTicks = 70;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;
    private float damage = 2.5F;
    private Set<Entity> damagedEntities = new HashSet<>();
    public int textureIndex = 0;
    private int tickCounter = 0;

    private static final EntityDataAccessor<Integer> TEXTURE_INDEX =
            SynchedEntityData.defineId(Splash.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> JUST_SPAWNED =
            SynchedEntityData.defineId(Splash.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DESPAWNING =
            SynchedEntityData.defineId(Splash.class, EntityDataSerializers.BOOLEAN);

    public Splash(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Splash(Level pLevel, LivingEntity pPlayer, BlockPos pPos) {
        super(ModEntities.SPLASH.get(), pLevel);
        this.setPos(pPos.getCenter().add(0, -0.5, 0));
        this.setOwner(pPlayer);
    }

    public Splash(Level pLevel, LivingEntity pPlayer, BlockPos pPos, int lifetime) {
        super(ModEntities.SPLASH.get(), pLevel);
        this.setPos(pPos.getCenter().add(0, -0.5, 0));
        this.setOwner(pPlayer);
        this.lifeTicks = lifetime;
        this.damage = 8.0F;
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
        if (this.owner == null && this.ownerUUID != null && this.level instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TEXTURE_INDEX, 0);
        this.entityData.define(JUST_SPAWNED, true);
        this.entityData.define(DESPAWNING, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public void tick() {
        super.tick();

        Level level = this.level;

        if (!level.isClientSide)
        {
            tickCounter++;

            // Check if X number of ticks have passed
            if (tickCounter % 3 == 0) {
                tickCounter = 0;
                textureIndex = (textureIndex + 1) % 6; // Modulus ensures textureIndex stays within 0-5
            }
            this.setTextureIndex(textureIndex);

            // Extinguish fire in an 11 diameter (5 blocks radius)
            int radius = 5;
            BlockPos entityPos = this.blockPosition();
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos blockPos = entityPos.offset(x, y, z);
                        BlockState blockState = level.getBlockState(blockPos);
                        if (blockState.getBlock() instanceof FireBlock) {
                            level.playSound(this, this.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.05F, 1F);
                            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }

            List<Entity> collidedEntities = level.getEntities(this, this.getBoundingBox());
            for (Entity entity : collidedEntities) {
                if (entity instanceof LivingEntity && entity != owner) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (livingEntity.isOnFire()) {
                        livingEntity.extinguishFire();
                        ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 6, 0.3D, 0.3D, 0.3D, 0.0D);
                    }
                    // Deal Damage
                    if (this.getTextureIndex() == 3) {
                        if (livingEntity.getType().is(ModTags.Entities.FIRE)) {
                            livingEntity.hurt(this.damageSources().generic(), damage * 2);
                        }
                        else {
                            livingEntity.hurt(this.damageSources().generic(), damage);
                        }
                    }
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3, false, true));
                    this.markAsDamaged(livingEntity);
                }
            }
            ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY() + 0.5, this.getZ(), 3, 1.5D, 2.0D, 1.5D, 0.1D);

            if (this.getTextureIndex() == 3 && tickCounter % 4 == 0 && this.tickCount > 20) {
                level.playSound(this, this.blockPosition(), SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.PLAYERS, 2F, 1F);
            }

            if (--this.warmupDelayTicks < 0) {
                --this.lifeTicks;
                if (this.warmupDelayTicks == -1) {
                    this.setJustSpawned(true);
                    ((ServerLevel) level).sendParticles(ParticleTypes.SPLASH, this.getX(), this.getY() + 2, this.getZ(), 50, 1.5D, 2.0D, 1.5D, 1.0D);
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

    private boolean hasBeenDamaged(Entity entity) {
        return damagedEntities.contains(entity);
    }

    private void markAsDamaged(Entity entity) {
        damagedEntities.add(entity);
    }

    public void setTextureIndex(int textureIndex) {
        this.entityData.set(TEXTURE_INDEX, textureIndex);
    }

    public int getTextureIndex() {
        return this.entityData.get(TEXTURE_INDEX);
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
