package ttv.migami.jeg.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import ttv.migami.jeg.init.ModRecipeTypes;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class GunmetalWorkbenchRecipes
{
    public static boolean isEmpty(Level world)
    {
        return world.getRecipeManager().getRecipes().stream().noneMatch(recipe -> recipe.getType() == ModRecipeTypes.GUNNITE_WORKBENCH.get());
    }

    public static NonNullList<GunmetalWorkbenchRecipe> getAll(Level world)
    {
        return world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == ModRecipeTypes.GUNMETAL_WORKBENCH.get())
                .map(recipe -> (GunmetalWorkbenchRecipe) recipe)
                .collect(Collectors.toCollection(NonNullList::create));
    }

    @Nullable
    public static GunmetalWorkbenchRecipe getRecipeById(Level world, ResourceLocation id)
    {
        return world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == ModRecipeTypes.GUNMETAL_WORKBENCH.get())
                .map(recipe -> (GunmetalWorkbenchRecipe) recipe)
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst().orElse(null);
    }
}
