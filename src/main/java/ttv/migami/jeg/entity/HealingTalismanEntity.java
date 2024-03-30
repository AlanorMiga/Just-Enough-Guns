package ttv.migami.jeg.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import ttv.migami.jeg.init.ModEntities;
import ttv.migami.jeg.init.ModItems;
import ttv.migami.jeg.init.ModParticleTypes;


public class HealingTalismanEntity extends ThrowableItemProjectile {
    protected int life = 30;
    protected int particleCounter = 0;
    public HealingTalismanEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public HealingTalismanEntity(Level pLevel) {
        super(ModEntities.HEALING_TALISMAN.get(), pLevel);
    }

    public HealingTalismanEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.HEALING_TALISMAN.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.HEALING_TALISMAN.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (!isNoGravity())
            setNoGravity(true);

        if (particleCounter >= 2) {
            this.level.addParticle(ModParticleTypes.HEALING_GLINT.get(), true, this.getX(), this.getY() + 0.1, this.getZ(), 0, 0, 0);
            particleCounter = 0;
        } else {
            particleCounter++;
        }

        if(this.tickCount >= this.life)
        {
            this.remove(RemovalReason.KILLED);
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = this.getOwner();
        Entity entityHit = pResult.getEntity();
        LivingEntity livingEntity1 = (LivingEntity)entity;
        LivingEntity livingEntity2 = (LivingEntity)entityHit;
        Level level = entity.level;

        livingEntity1.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, false, false), this.getEffectSource());
        livingEntity2.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, false, false), this.getEffectSource());

        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(ParticleTypes.HEART,
                    this.getX(), this.getY(), this.getZ(), 2, 0.5, 0.5, 0.5, 0);
            serverLevel.sendParticles(ParticleTypes.HEART,
                    livingEntity1.getX(), livingEntity1.getY() + 1, livingEntity1.getZ(), 2, 0.5, 0.5, 0.5, 0);
        }

        livingEntity2.invulnerableTime = 0;
        this.discard();
    }

    @Override
    public void setNoGravity(boolean pNoGravity) {
        super.setNoGravity(true);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        this.discard();
    }

    public void shootFromRotation(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, pVelocity, pInaccuracy);
    }

}