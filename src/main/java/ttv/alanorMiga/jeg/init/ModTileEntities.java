package ttv.alanorMiga.jeg.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.blockentity.GunmetalWorkbenchBlockEntity;
import ttv.alanorMiga.jeg.blockentity.GunniteWorkbenchBlockEntity;
import ttv.alanorMiga.jeg.blockentity.RecyclerBlockEntity;
import ttv.alanorMiga.jeg.blockentity.ScrapWorkbenchBlockEntity;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ModTileEntities
{
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MOD_ID);

    public static final RegistryObject<BlockEntityType<ScrapWorkbenchBlockEntity>> SCRAP_WORKBENCH = register("scrap_workbench", ScrapWorkbenchBlockEntity::new,
            () -> new Block[]{ModBlocks.SCRAP_WORKBENCH.get()});
    public static final RegistryObject<BlockEntityType<GunmetalWorkbenchBlockEntity>> GUNMETAL_WORKBENCH = register("gunmetal_workbench", GunmetalWorkbenchBlockEntity::new,
            () -> new Block[]{ModBlocks.GUNMETAL_WORKBENCH.get()});
    public static final RegistryObject<BlockEntityType<GunniteWorkbenchBlockEntity>> GUNNITE_WORKBENCH = register("gunnite_workbench", GunniteWorkbenchBlockEntity::new,
            () -> new Block[]{ModBlocks.GUNNITE_WORKBENCH.get()});
    public static final RegistryObject<BlockEntityType<RecyclerBlockEntity>> RECYCLER = register("recycler", RecyclerBlockEntity::new,
            () -> new Block[]{ModBlocks.RECYCLER.get()});

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> factoryIn, Supplier<Block[]> validBlocksSupplier)
    {
        return REGISTER.register(id, () -> BlockEntityType.Builder.of(factoryIn, validBlocksSupplier.get()).build(null));
    }
}
