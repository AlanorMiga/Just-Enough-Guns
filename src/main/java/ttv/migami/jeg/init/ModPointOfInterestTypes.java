package ttv.migami.jeg.init;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.migami.jeg.Reference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModPointOfInterestTypes
{
    public static final DeferredRegister<PoiType> REGISTER = DeferredRegister.create(ForgeRegistries.POI_TYPES, Reference.MOD_ID);

    public static final RegistryObject<PoiType> BOO_NEST = register("boo_nest", () -> {
        List<RegistryObject<Block>> blocks = new ArrayList<>();
        blocks.add(ModBlocks.BOO_NEST);
        blocks.add(ModBlocks.BOOHIVE);
        return blocks;
    }, 1);

    private static RegistryObject<PoiType> register(String name, RegistryObject<Block> block, int maxFreeTickets) {
        List<RegistryObject<Block>> blocks = new ArrayList<>();
        blocks.add(block);
        return register(name, blocks, maxFreeTickets);
    }

    private static RegistryObject<PoiType> register(String name, Supplier<List<RegistryObject<Block>>> supplier, int maxFreeTickets) {
        return register(name, supplier.get(), maxFreeTickets);
    }

    private static RegistryObject<PoiType> register(String name, List<RegistryObject<Block>> blocks, int maxFreeTickets) {
        return register(name, () -> {
            Set<BlockState> blockStates = new HashSet<>();
            for (RegistryObject<Block> block : blocks) {
                blockStates.addAll(PoiType.getBlockStates(block.get()));
            }
            return new PoiType(name, blockStates, maxFreeTickets, 1);
        });
    }

    private static RegistryObject<PoiType> register(String name, Supplier<PoiType> supplier) {
        return REGISTER.register(name, supplier);
    }
}
