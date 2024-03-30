package ttv.migami.jeg.world.gen;

import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.init.ModBlocks;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures
{
    public static final DeferredRegister<ConfiguredFeature<?, ?>> REGISTER =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Reference.MOD_ID);

    static RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    static RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    static RuleTest dripstoneReplaceables = new BlockMatchTest(Blocks.DRIPSTONE_BLOCK);
    static RuleTest blackstoneReplaceables = new BlockMatchTest(Blocks.BLACKSTONE);
    static RuleTest basaltReplaceables = new BlockMatchTest(Blocks.BASALT);

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_SCRAP_TARGET_LIST = Suppliers.memoize(() ->
            List.of(
                    OreConfiguration.target(stoneReplaceables, ModBlocks.SCRAP_ORE.get().defaultBlockState()),
                    OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_SCRAP_ORE.get().defaultBlockState()))
    );
    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_BRIMSTONE_ORE_TARGET_LIST   = Suppliers.memoize(() ->
            List.of(
                    OreConfiguration.target(stoneReplaceables, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState()),
                    OreConfiguration.target(deepslateReplaceables, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState()))
    );
    private static final Supplier<List<OreConfiguration.TargetBlockState>> DRIPSTONE_BRIMSTONE_ORE_TARGET_LIST   = Suppliers.memoize(() ->
            List.of(
                    OreConfiguration.target(dripstoneReplaceables, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState()))
    );
    private static final Supplier<List<OreConfiguration.TargetBlockState>> NETHER_BRIMSTONE_ORE_TARGET_LIST   = Suppliers.memoize(() ->
            List.of(
                    OreConfiguration.target(blackstoneReplaceables, ModBlocks.BLACKSTONE_BRIMSTONE_ORE.get().defaultBlockState()),
                    OreConfiguration.target(basaltReplaceables, ModBlocks.BASALT_BRIMSTONE_ORE.get().defaultBlockState()))
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> OVERWORLD_SCRAP_ORE = REGISTER.register("overworld_scrap_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_SCRAP_TARGET_LIST.get(), 10)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> OVERWORLD_BRIMSTONE_ORE = REGISTER.register("overworld_brimstone_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_BRIMSTONE_ORE_TARGET_LIST.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> DRIPSTONE_BRIMSTONE_ORE = REGISTER.register("dripstone_brimstone_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DRIPSTONE_BRIMSTONE_ORE_TARGET_LIST.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> NETHER_BRIMSTONE_ORE = REGISTER.register("nether_brimstone_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHER_BRIMSTONE_ORE_TARGET_LIST.get(), 12)));

}
