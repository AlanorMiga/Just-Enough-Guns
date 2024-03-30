package ttv.migami.jeg.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

/**
 * Author: MrCrayfish
 */
public class FireStarterEnchantment extends GunEnchantment
{
    public FireStarterEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTypes.GUN, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, Type.PROJECTILE);
    }

    @Override
    public int getMinCost(int level)
    {
        return 15;
    }

    @Override
    public int getMaxCost(int level)
    {
        return super.getMinCost(level) + 30;
    }
}
