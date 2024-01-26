package ttv.alanorMiga.jeg.crafting;

import net.minecraft.world.item.crafting.RecipeType;

/**
 * Author: MrCrayfish
 */
public class ModRecipeType {

    public static final RecipeType<ScrapWorkbenchRecipe> SCRAP_WORKBENCH = RecipeType.register("jeg:scrap_workbench");
    public static final RecipeType<GunmetalWorkbenchRecipe> GUNMETAL_WORKBENCH = RecipeType.register("jeg:gunmetal_workbench");
    public static final RecipeType<GunniteWorkbenchRecipe> GUNNITE_WORKBENCH = RecipeType.register("jeg:gunnite_workbench");
    public static final RecipeType<RecyclingRecipe> RECYCLING = RecipeType.register("jeg:recycling");

    // Does nothing but trigger loading of static fields
    public static void init() {
    }

}
