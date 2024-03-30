package ttv.migami.jeg.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.init.ModParticleTypes;
import ttv.migami.jeg.item.GunItem;

public class SpectreProjectileEntity extends ProjectileEntity {

    public SpectreProjectileEntity(EntityType<? extends Entity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public SpectreProjectileEntity(EntityType<? extends Entity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon, GunItem item, Gun modifiedGun) {
        super(entityType, worldIn, shooter, weapon, item, modifiedGun);
    }

    @Override
    protected void onProjectileTick()
    {
        if(this.level.isClientSide && (this.tickCount > 2 && this.tickCount < this.life)) {

            for(int i = 0; i < 5; i++) {
                this.level.addParticle(ModParticleTypes.GHOST_GLINT.get(), true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }

        }
    }

}
