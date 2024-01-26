package ttv.alanorMiga.jeg.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class GunmetalWorkbenchRecipes
{
    public static boolean isEmpty(Level world)
    {
        return world.getRecipeManager().getRecipes().stream().noneMatch(recipe -> recipe.getType() == ModRecipeType.GUNNITE_WORKBENCH);
    }

    public static NonNullList<GunmetalWorkbenchRecipe> getAll(Level world)
    {
        return world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == ModRecipeType.GUNMETAL_WORKBENCH)
                .map(recipe -> (GunmetalWorkbenchRecipe) recipe)
                .collect(Collectors.toCollection(NonNullList::create));
    }

    @Nullable
    public static GunniteWorkbenchRecipe getRecipeById(Level world, ResourceLocation id)
    {
        return world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == ModRecipeType.GUNNITE_WORKBENCH)
                .map(recipe -> (GunniteWorkbenchRecipe) recipe)
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst().orElse(null);
    }
}
