package ttv.migami.jeg.world.gen;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ttv.migami.jeg.Reference;

import static ttv.migami.jeg.world.gen.ModOrePlacement.commonOrePlacement;

public class ModPlacedFeatures
{
    public static final DeferredRegister<PlacedFeature> REGISTER =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Reference.MOD_ID);

    public static final RegistryObject<PlacedFeature> OVERWORLD_SCRAP_ORE = REGISTER.register("overworld_scrap_ore",
            () -> new PlacedFeature(ModConfiguredFeatures.OVERWORLD_SCRAP_ORE.getHolder().get(),
                    commonOrePlacement(15, HeightRangePlacement.uniform(
                            VerticalAnchor.aboveBottom(-64),
                            VerticalAnchor.absolute(128)
                    ))));

    public static final RegistryObject<PlacedFeature> OVERWORLD_BRIMSTONE_ORE = REGISTER.register("overworld_brimstone_ore",
            () -> new PlacedFeature(ModConfiguredFeatures.OVERWORLD_BRIMSTONE_ORE.getHolder().get(),
                    commonOrePlacement(30, HeightRangePlacement.uniform(
                            VerticalAnchor.aboveBottom(-64),
                            VerticalAnchor.absolute(64)
                    ))));

    public static final RegistryObject<PlacedFeature> DRIPSTONE_BRIMSTONE_ORE = REGISTER.register("dripstone_brimstone_ore",
            () -> new PlacedFeature(ModConfiguredFeatures.DRIPSTONE_BRIMSTONE_ORE.getHolder().get(),
                    commonOrePlacement(100, HeightRangePlacement.uniform(
                            VerticalAnchor.aboveBottom(-64),
                            VerticalAnchor.absolute(64)
                    ))));

    public static final RegistryObject<PlacedFeature> NETHER_BRIMSTONE_ORE = REGISTER.register("nether_brimstone_ore",
            () -> new PlacedFeature(ModConfiguredFeatures.NETHER_BRIMSTONE_ORE.getHolder().get(),
                    commonOrePlacement(30, HeightRangePlacement.uniform(
                            VerticalAnchor.aboveBottom(10),
                            VerticalAnchor.belowTop(10)
                    ))));
}