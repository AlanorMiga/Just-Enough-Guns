package ttv.alanorMiga.jeg.crafting;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.alanorMiga.jeg.Reference;

/**
 * Author: MrCrayfish
 */
public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);

    public static final RegistryObject<SimpleRecipeSerializer<DyeItemRecipe>> DYE_ITEM = REGISTER.register("dye_item", () -> new SimpleRecipeSerializer<>(DyeItemRecipe::new));
    public static final RegistryObject<ScrapWorkbenchRecipeSerializer> SCRAP_WORKBENCH = REGISTER.register("scrap_workbench", ScrapWorkbenchRecipeSerializer::new);
    public static final RegistryObject<GunmetalWorkbenchRecipeSerializer> GUNMETAL_WORKBENCH = REGISTER.register("gunmetal_workbench", GunmetalWorkbenchRecipeSerializer::new);
    public static final RegistryObject<GunniteWorkbenchRecipeSerializer> GUNNITE_WORKBENCH = REGISTER.register("gunnite_workbench", GunniteWorkbenchRecipeSerializer::new);
    public static final RegistryObject<SimpleRecyclingSerializer<RecyclingRecipe>> RECYCLING_RECIPE = REGISTER.register("recycling", () -> new SimpleRecyclingSerializer<>(RecyclingRecipe::new, 1200));
}