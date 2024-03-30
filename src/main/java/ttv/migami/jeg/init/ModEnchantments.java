package ttv.migami.jeg.init;

import ttv.migami.jeg.Reference;
import ttv.migami.jeg.enchantment.AcceleratorEnchantment;
import ttv.migami.jeg.enchantment.CollateralEnchantment;
import ttv.migami.jeg.enchantment.FireStarterEnchantment;
import ttv.migami.jeg.enchantment.LightweightEnchantment;
import ttv.migami.jeg.enchantment.OverCapacityEnchantment;
import ttv.migami.jeg.enchantment.PuncturingEnchantment;
import ttv.migami.jeg.enchantment.QuickHandsEnchantment;
import ttv.migami.jeg.enchantment.ReclaimedEnchantment;
import ttv.migami.jeg.enchantment.TriggerFingerEnchantment;
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
