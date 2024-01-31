package ttv.alanorMiga.jeg.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import ttv.alanorMiga.jeg.init.ModBlocks;
import ttv.alanorMiga.jeg.init.ModRecipeSerializers;
import ttv.alanorMiga.jeg.init.ModRecipeTypes;

public class RecyclingRecipe extends AbstractRecyclingRecipe {
    public RecyclingRecipe(ResourceLocation p_44460_, String p_44461_, Ingredient p_44462_, ItemStack p_44463_, float p_44464_, int p_44465_) {
        super(ModRecipeTypes.RECYCLING, p_44460_, p_44461_, p_44462_, p_44463_, p_44464_, p_44465_);
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.RECYCLER.get());
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.RECYCLING_RECIPE.get();
    }
}