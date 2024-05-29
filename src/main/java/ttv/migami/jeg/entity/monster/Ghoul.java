package ttv.migami.jeg.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import ttv.migami.jeg.Config;
import ttv.migami.jeg.init.ModParticleTypes;
import ttv.migami.jeg.init.ModSounds;

public class Ghoul extends Zombie {

   protected final float VANISHING_TIME = 200;
   protected float vanishingTime = 200;
   protected boolean isExposed = false;

   public Ghoul(EntityType<? extends Zombie> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   protected boolean isSunSensitive() {
      return true;
   }

   protected SoundEvent getAmbientSound() {
      return ModSounds.ENTITY_GHOUL_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource pDamageSource) {
      return ModSounds.ENTITY_GHOUL_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ModSounds.ENTITY_GHOUL_DEATH.get();
   }

   protected SoundEvent getStepSound() {
      return SoundEvents.HUSK_STEP;
   }

   @Override
   public MobCategory getClassification(boolean forSpawnCount) {
      return super.getClassification(forSpawnCount);
   }

   @Override
   public void aiStep() {
      if (this.isAlive()) {
         Level level = this.level();

         if (level instanceof ServerLevel serverLevel)
         {
            double d = 0.2D;
            float rng = getRandom().nextFloat();

            if (rng <= d)
            {
               serverLevel.sendParticles(ModParticleTypes.GHOST_FLAME.get(),
                       this.getX(), this.getBbHeight() * 0.5, this.getZ(), 1, 0.2, 1.2, 0.2, 0.0);
            }
         }
      }

      super.aiStep();
   }

   public boolean doHurtTarget(Entity pEntity) {
      boolean flag = super.doHurtTarget(pEntity);
      if (flag && this.getMainHandItem().isEmpty() && pEntity instanceof LivingEntity) {
         float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
         //((LivingEntity)pEntity).addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int)f), this);
      }

      return flag;
   }

   protected boolean convertsInWater() {
      return true;
   }

   protected void doUnderWaterConversion() {
      this.convertToZombieType(EntityType.ZOMBIE);
      if (!this.isSilent()) {
         this.level().levelEvent(null, 1041, this.blockPosition(), 0);
      }

   }

   protected ItemStack getSkull() {
      return ItemStack.EMPTY;
   }

   public static AttributeSupplier.Builder createAttributes()
   {
      return Monster.createMonsterAttributes()
              .add(Attributes.MAX_HEALTH, 25.0D)
              .add(Attributes.FOLLOW_RANGE, 35.0D)
              .add(Attributes.MOVEMENT_SPEED, 0.17F)
              .add(Attributes.ATTACK_DAMAGE, 2.0D)
              .add(Attributes.ARMOR, 4.0D)
              .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
   }

   public static boolean checkMonsterSpawnRules(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
      return Config.COMMON.world.ghoulSpawning.get() && pLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(pLevel, pPos, pRandom) && checkMobSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom);
   }

}