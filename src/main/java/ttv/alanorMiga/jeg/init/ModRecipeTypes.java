package ttv.alanorMiga.jeg.init;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.crafting.GunmetalWorkbenchRecipe;
import ttv.alanorMiga.jeg.crafting.GunniteWorkbenchRecipe;
import ttv.alanorMiga.jeg.crafting.RecyclingRecipe;
import ttv.alanorMiga.jeg.crafting.ScrapWorkbenchRecipe;

/**
 * Author: MrCrayfish
 */
public class ModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Reference.MOD_ID);

    public static final RegistryObject<RecipeType<ScrapWorkbenchRecipe>> SCRAP_WORKBENCH = create("scrap_workbench");
    public static final RegistryObject<RecipeType<GunmetalWorkbenchRecipe>> GUNMETAL_WORKBENCH = create("gunmetal_workbench");
    public static final RegistryObject<RecipeType<GunniteWorkbenchRecipe>> GUNNITE_WORKBENCH = create("gunnite_workbench");
    public static final RegistryObject<RecipeType<RecyclingRecipe>> RECYCLING = create("recycling");

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> create(String name)
    {
        return REGISTER.register(name, () -> new RecipeType<>()
        {
            @Override
            public String toString()
            {
                return name;
            }
        });
    }
}
