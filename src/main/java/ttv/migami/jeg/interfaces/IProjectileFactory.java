package ttv.migami.jeg.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.common.ProjectileManager;
import ttv.migami.jeg.entity.projectile.ProjectileEntity;
import ttv.migami.jeg.item.GunItem;

/**
 * This class allows weapons to fire custom projectiles instead of the default implementation. The
 * grenade launcher uses this to spawn a grenade entity with custom physics. Use {@link ProjectileManager}
 * to register a factory.
 *
 * Author: MrCrayfish
 */
public interface IProjectileFactory
{
    /**
     * Creates a new projectile entity.
     *
     * @param worldIn     the world the projectile is going to be spawned into
     * @param entity      the entity who fired the weapon
     * @param weapon      the item stack of the weapon
     * @param item        the gun item
     * @param modifiedGun the properties of the gun
     * @return a projectile entity
     */
    ProjectileEntity create(Level worldIn, LivingEntity entity, ItemStack weapon, GunItem item, Gun modifiedGun);
}
