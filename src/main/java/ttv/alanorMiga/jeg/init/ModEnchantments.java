package ttv.alanorMiga.jeg.init;

import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.enchantment.AcceleratorEnchantment;
import ttv.alanorMiga.jeg.enchantment.CollateralEnchantment;
import ttv.alanorMiga.jeg.enchantment.FireStarterEnchantment;
import ttv.alanorMiga.jeg.enchantment.LightweightEnchantment;
import ttv.alanorMiga.jeg.enchantment.OverCapacityEnchantment;
import ttv.alanorMiga.jeg.enchantment.PuncturingEnchantment;
import ttv.alanorMiga.jeg.enchantment.QuickHandsEnchantment;
import ttv.alanorMiga.jeg.enchantment.ReclaimedEnchantment;
import ttv.alanorMiga.jeg.enchantment.TriggerFingerEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Author: MrCrayfish
 */
public class ModEnchantments
{
    public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Reference.MOD_ID);

    public static final RegistryObject<Enchantment> QUICK_HANDS = REGISTER.register("quick_hands", QuickHandsEnchantment::new);
    public static final RegistryObject<Enchantment> TRIGGER_FINGER = REGISTER.register("trigger_finger", TriggerFingerEnchantment::new);
    public static final RegistryObject<Enchantment> LIGHTWEIGHT = REGISTER.register("lightweight", LightweightEnchantment::new);
    public static final RegistryObject<Enchantment> COLLATERAL = REGISTER.register("collateral", CollateralEnchantment::new);
    public static final RegistryObject<Enchantment> OVER_CAPACITY = REGISTER.register("over_capacity", OverCapacityEnchantment::new);
    public static final RegistryObject<Enchantment> RECLAIMED = REGISTER.register("reclaimed", ReclaimedEnchantment::new);
    public static final RegistryObject<Enchantment> ACCELERATOR = REGISTER.register("accelerator", AcceleratorEnchantment::new);
    public static final RegistryObject<Enchantment> PUNCTURING = REGISTER.register("puncturing", PuncturingEnchantment::new);
    public static final RegistryObject<Enchantment> FIRE_STARTER = REGISTER.register("fire_starter", FireStarterEnchantment::new);
    //Gravity Impulse (3 levels) - nearby entities will get knocked away from the target location
}
