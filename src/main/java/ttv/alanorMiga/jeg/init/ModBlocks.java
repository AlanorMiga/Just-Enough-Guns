package ttv.alanorMiga.jeg.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.block.GunmetalWorkbenchBlock;
import ttv.alanorMiga.jeg.block.GunniteWorkbenchBlock;
import ttv.alanorMiga.jeg.block.RecyclerBlock;
import ttv.alanorMiga.jeg.block.ScrapWorkbenchBlock;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 * Author: MrCrayfish
 */
public class ModBlocks {

    private static final ToIntFunction<BlockState> light_Level_3 = BlockState -> 3;

    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);

    public static final RegistryObject<Block> SCRAP_WORKBENCH = register("scrap_workbench",
            () -> new ScrapWorkbenchBlock(Block.Properties.of(Material.WOOD)
                    .strength(1.5F)));
    public static final RegistryObject<Block> GUNMETAL_WORKBENCH = register("gunmetal_workbench",
            () -> new GunmetalWorkbenchBlock(Block.Properties.of(Material.STONE).noOcclusion()
                    .strength(3.0F)));
    public static final RegistryObject<Block> GUNNITE_WORKBENCH = register("gunnite_workbench",
            () -> new GunniteWorkbenchBlock(Block.Properties.of(Material.METAL).noOcclusion()
                    .strength(5.0F)));
    public static final RegistryObject<Block> RECYCLER = register("recycler",
            () -> new RecyclerBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(2.0F)));
    public static final RegistryObject<Block> SCRAP_ORE = register("scrap_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F)));
    public static final RegistryObject<Block> DEEPSLATE_SCRAP_ORE = register("deepslate_scrap_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(4.5F)));
    public static final RegistryObject<Block> SCRAP_BLOCK = register("scrap_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F)));
    public static final RegistryObject<Block> GUNMETAL_BLOCK = register("gunmetal_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(4.0F)));
    public static final RegistryObject<Block> GUNNITE_BLOCK = register("gunnite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F)));
    public static final RegistryObject<Block> BRIMSTONE_ORE = register("brimstone_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F)
                    .lightLevel(light_Level_3)));
    public static final RegistryObject<Block> BLACKSTONE_BRIMSTONE_ORE = register("blackstone_brimstone_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F)
                    .lightLevel(light_Level_3)));
    public static final RegistryObject<Block> BASALT_BRIMSTONE_ORE = register("basalt_brimstone_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F)
                    .lightLevel(light_Level_3)));

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier) {
        return register(id, blockSupplier, block1 -> new BlockItem(block1, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier, @Nullable Function<T, BlockItem> supplier) {
        RegistryObject<T> registryObject = REGISTER.register(id, blockSupplier);
        if (supplier != null) {
            ModItems.REGISTER.register(id, () -> supplier.apply(registryObject.get()));
        }
        return registryObject;
    }
}
