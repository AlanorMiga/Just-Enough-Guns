package ttv.migami.jeg.world.gen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import ttv.migami.jeg.Reference;

import java.util.List;

public class ModPlacedFeatures
{
    public static final ResourceKey<PlacedFeature> SCRAP_ORE_PLACED_KEY = createKey("scrap_ore_placed");
    public static final ResourceKey<PlacedFeature> BRIMSTONE_ORE_PLACED_KEY = createKey("brimstone_ore_placed");
    public static final ResourceKey<PlacedFeature> DRIPSTONE_BRIMSTONE_ORE_PLACED_KEY = createKey("dripstone_brimstone_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_BRIMSTONE_PLACED_KEY = createKey("nether_brimstone_ore_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, SCRAP_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SCRAP_ORE_KEY),
                ModOrePlacement.commonOrePlacement(15, // Veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(128))));

        register(context, BRIMSTONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BRIMSTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(30, // Veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(64))));

        register(context, DRIPSTONE_BRIMSTONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DRIPSTONE_BRIMSTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(100, // Veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(64))));

        register(context, NETHER_BRIMSTONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_BRIMSTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(30, // Veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10))));
    }


    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Reference.MOD_ID, name));
    }
    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}