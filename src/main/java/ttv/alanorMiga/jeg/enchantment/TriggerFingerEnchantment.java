package ttv.alanorMiga.jeg.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

/**
 * Author: MrCrayfish
 */
public class TriggerFingerEnchantment extends GunEnchantment
{
    public TriggerFingerEnchantment()
    {
        super(Rarity.RARE, EnchantmentTypes.SEMI_AUTO_GUN, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, Type.WEAPON);
    }

    @Override
    public int getMaxLevel()
    {
        return 2;
    }

    @Override
    public int getMinCost(int level)
    {
        return 15 + (level - 1) * 10;
    }

    @Override
    public int getMaxCost(int level)
    {
        return this.getMinCost(level) + 40;
    }
}
