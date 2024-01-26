package ttv.alanorMiga.jeg.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import ttv.alanorMiga.jeg.init.ModBlocks;

import java.util.List;
import java.util.Set;

public class OreFeatures {

    public static Holder<PlacedFeature> SCRAP_ORE_REPLACEABLES;
    public static Holder<PlacedFeature> BRIMSTONE_ORE_REPLACEABLES;
    public static Holder<PlacedFeature> BRIMSTONE_ORE_DRIPSTONE_REPLACEABLES;
    public static Holder<PlacedFeature> BRIMSTONE_ORE_NETHER_REPLACEABLES;


    private static final RuleTest STONE = new BlockMatchTest(Blocks.STONE);
    private static final RuleTest DEEPSLATE = new BlockMatchTest(Blocks.DEEPSLATE);
    private static final RuleTest DRIPSTONE_BLOCK = new BlockMatchTest(Blocks.DRIPSTONE_BLOCK);
    private static final RuleTest BLACKSTONE = new BlockMatchTest(Blocks.BLACKSTONE);
    private static final RuleTest BASALT = new BlockMatchTest(Blocks.BASALT);

    private static final List<OreConfiguration.TargetBlockState> ORE_SCRAP_TARGET_LIST = List.of(
            OreConfiguration.target(STONE, ModBlocks.SCRAP_ORE.get().defaultBlockState()),
            OreConfiguration.target(DEEPSLATE, ModBlocks.DEEPSLATE_SCRAP_ORE.get().defaultBlockState()));
    private static final List<OreConfiguration.TargetBlockState> ORE_BRIMSTONE_TARGET_LIST = List.of(
            OreConfiguration.target(STONE, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState()),
            OreConfiguration.target(DEEPSLATE, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState()));

    private static final List<OreConfiguration.TargetBlockState> ORE_DRIPSTONE_BRIMSTONE_TARGET_LIST = List.of(
            OreConfiguration.target(DRIPSTONE_BLOCK, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState()));
    private static final List<OreConfiguration.TargetBlockState> ORE_NETHER_BRIMSTONE_TARGET_LIST = List.of(
            OreConfiguration.target(BLACKSTONE, ModBlocks.BLACKSTONE_BRIMSTONE_ORE.get().defaultBlockState()),
            OreConfiguration.target(BASALT, ModBlocks.BASALT_BRIMSTONE_ORE.get().defaultBlockState()));

    public static void registerOreFeatures() {
        OreConfiguration scrap_ore = new OreConfiguration(ORE_SCRAP_TARGET_LIST, 10, 0);
        SCRAP_ORE_REPLACEABLES = registerPlacedOreFeature("overworld_scrap_ore", new ConfiguredFeature<>(Feature.ORE, scrap_ore),
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(128)));

        OreConfiguration overworld_brimstone_ore = new OreConfiguration(ORE_BRIMSTONE_TARGET_LIST, 9, 0);
        BRIMSTONE_ORE_REPLACEABLES = registerPlacedOreFeature("overworld_brimstone_ore", new ConfiguredFeature<>(Feature.ORE, overworld_brimstone_ore),
                CountPlacement.of(30),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)));

        OreConfiguration dripstone_brimstone_ore = new OreConfiguration(ORE_DRIPSTONE_BRIMSTONE_TARGET_LIST, 9, 0);
        BRIMSTONE_ORE_DRIPSTONE_REPLACEABLES = registerPlacedOreFeature("dripstone_brimstone_ore", new ConfiguredFeature<>(Feature.ORE, dripstone_brimstone_ore),
                CountPlacement.of(100),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)));

        OreConfiguration nether_brimstone_ore = new OreConfiguration(ORE_NETHER_BRIMSTONE_TARGET_LIST, 12, 0);
        BRIMSTONE_ORE_NETHER_REPLACEABLES = registerPlacedOreFeature("nether_brimstone_ore", new ConfiguredFeature<>(Feature.ORE, nether_brimstone_ore),
                CountPlacement.of(30),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(128)));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedOreFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, BRIMSTONE_ORE_NETHER_REPLACEABLES);
        }
        else if (event.getCategory() == Biome.BiomeCategory.THEEND) {

        }
        else {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SCRAP_ORE_REPLACEABLES);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, BRIMSTONE_ORE_DRIPSTONE_REPLACEABLES);

            // Badlands filter.
            if (types.contains(BiomeDictionary.Type.MESA)) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, BRIMSTONE_ORE_REPLACEABLES);
            }

        }
    }
}
