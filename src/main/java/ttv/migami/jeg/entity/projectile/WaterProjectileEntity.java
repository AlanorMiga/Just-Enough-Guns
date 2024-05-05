package ttv.migami.jeg.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.item.GunItem;

public class WaterProjectileEntity extends ProjectileEntity {

    public WaterProjectileEntity(EntityType<? extends Entity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public WaterProjectileEntity(EntityType<? extends Entity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon, GunItem item, Gun modifiedGun) {
        super(entityType, worldIn, shooter, weapon, item, modifiedGun);
    }

    @Override
    protected void onProjectileTick()
    {
        if(this.level().isClientSide && (this.tickCount > 2 && this.tickCount < this.life)) {

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
