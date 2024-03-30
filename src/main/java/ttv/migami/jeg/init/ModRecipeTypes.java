package ttv.migami.jeg.init;

import net.minecraft.world.item.crafting.RecipeType;
import ttv.migami.jeg.crafting.GunmetalWorkbenchRecipe;
import ttv.migami.jeg.crafting.GunniteWorkbenchRecipe;
import ttv.migami.jeg.crafting.RecyclingRecipe;
import ttv.migami.jeg.crafting.ScrapWorkbenchRecipe;

/**
 * Author: MrCrayfish
 */
public class ModRecipeTypes {

    public static final RecipeType<ScrapWorkbenchRecipe> SCRAP_WORKBENCH = RecipeType.register("jeg:scrap_workbench");
    public static final RecipeType<GunmetalWorkbenchRecipe> GUNMETAL_WORKBENCH = RecipeType.register("jeg:gunmetal_workbench");
    public static final RecipeType<GunniteWorkbenchRecipe> GUNNITE_WORKBENCH = RecipeType.register("jeg:gunnite_workbench");
    public static final RecipeType<RecyclingRecipe> RECYCLING = RecipeType.register("jeg:recycling");

    // Does nothing but trigger loading of static fields
    public static void init() {
    }

}
