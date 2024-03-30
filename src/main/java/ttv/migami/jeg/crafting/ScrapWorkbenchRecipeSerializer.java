package ttv.migami.jeg.crafting;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class ScrapWorkbenchRecipeSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ScrapWorkbenchRecipe>
{
    @Override
    public ScrapWorkbenchRecipe fromJson(ResourceLocation recipeId, JsonObject parent)
    {
        ImmutableList.Builder<ScrapWorkbenchIngredient> builder = ImmutableList.builder();
        JsonArray input = GsonHelper.getAsJsonArray(parent, "materials");
        for(int i = 0; i < input.size(); i++)
        {
            JsonObject object = input.get(i).getAsJsonObject();
            builder.add(ScrapWorkbenchIngredient.fromJson(object));
        }
        if(!parent.has("result"))
        {
            throw new JsonSyntaxException("Missing result item entry");
        }
        JsonObject resultObject = GsonHelper.getAsJsonObject(parent, "result");
        ItemStack resultItem = ShapedRecipe.itemStackFromJson(resultObject);
        return new ScrapWorkbenchRecipe(recipeId, resultItem, builder.build());
    }

    @Nullable
    @Override
    public ScrapWorkbenchRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
    {
        ItemStack result = buffer.readItem();
        ImmutableList.Builder<ScrapWorkbenchIngredient> builder = ImmutableList.builder();
        int size = buffer.readVarInt();
        for(int i = 0; i < size; i++)
        {
            builder.add((ScrapWorkbenchIngredient) Ingredient.fromNetwork(buffer));
        }
        return new ScrapWorkbenchRecipe(recipeId, result, builder.build());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ScrapWorkbenchRecipe recipe)
    {
        buffer.writeItem(recipe.getItem());
        buffer.writeVarInt(recipe.getMaterials().size());
        for(ScrapWorkbenchIngredient ingredient : recipe.getMaterials())
        {
            ingredient.toNetwork(buffer);
        }
    }
}
