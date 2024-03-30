package ttv.migami.jeg.world.gen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.init.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures
{
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCRAP_ORE_KEY = registerKey("scrap_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRIMSTONE_ORE_KEY = registerKey("brimstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRIPSTONE_BRIMSTONE_ORE_KEY = registerKey("dripstone_brimstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BRIMSTONE_ORE_KEY = registerKey("nether_brimstone_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest dripstoneReplaceables = new BlockMatchTest(Blocks.DRIPSTONE_BLOCK);
        RuleTest blackstoneReplaceables = new BlockMatchTest(Blocks.BLACKSTONE);
        RuleTest basaltReplaceables = new BlockMatchTest(Blocks.BASALT);

        List<OreConfiguration.TargetBlockState> overworldScrapOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.SCRAP_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_SCRAP_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworldBrimstoneOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> netherBrimstoneOres = List.of(
                OreConfiguration.target(blackstoneReplaceables, ModBlocks.BLACKSTONE_BRIMSTONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(basaltReplaceables, ModBlocks.BASALT_BRIMSTONE_ORE.get().defaultBlockState()));

        register(context, SCRAP_ORE_KEY, Feature.ORE, new OreConfiguration(overworldScrapOres, 10));
        register(context, BRIMSTONE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldBrimstoneOres, 9));
        register(context, DRIPSTONE_BRIMSTONE_ORE_KEY, Feature.ORE, new OreConfiguration(dripstoneReplaceables,
                ModBlocks.BRIMSTONE_ORE.get().defaultBlockState(), 9));
        register(context, NETHER_BRIMSTONE_ORE_KEY, Feature.ORE, new OreConfiguration(netherBrimstoneOres, 12));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MOD_ID, name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
