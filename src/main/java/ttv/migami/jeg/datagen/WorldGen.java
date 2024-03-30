package ttv.migami.jeg.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.world.gen.ModConfiguredFeatures;
import ttv.migami.jeg.world.gen.ModPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGen extends DatapackBuiltinEntriesProvider
{
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);

    public WorldGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Reference.MOD_ID));
    }
}
