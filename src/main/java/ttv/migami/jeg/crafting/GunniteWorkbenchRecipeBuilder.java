package ttv.migami.jeg.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.ForgeRegistries;
import ttv.migami.jeg.init.ModRecipeSerializers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Ocelot
 */
public class GunniteWorkbenchRecipeBuilder
{
    private final Item result;
    private final int count;
    private final List<ScrapWorkbenchIngredient> ingredients;
    private final Advancement.Builder advancementBuilder;
    private final List<ICondition> conditions = new ArrayList<>();

    private GunniteWorkbenchRecipeBuilder(ItemLike item, int count)
    {
        this.result = item.asItem();
        this.count = count;
        this.ingredients = new ArrayList<>();
        this.advancementBuilder = Advancement.Builder.advancement();
    }

    public static GunniteWorkbenchRecipeBuilder crafting(ItemLike item)
    {
        return new GunniteWorkbenchRecipeBuilder(item, 1);
    }

    public static GunniteWorkbenchRecipeBuilder crafting(ItemLike item, int count)
    {
        return new GunniteWorkbenchRecipeBuilder(item, count);
    }

    public GunniteWorkbenchRecipeBuilder addIngredient(ItemLike item, int count)
    {
        this.ingredients.add(ScrapWorkbenchIngredient.of(item, count));
        return this;
    }

    public GunniteWorkbenchRecipeBuilder addIngredient(ScrapWorkbenchIngredient ingredient)
    {
        this.ingredients.add(ingredient);
        return this;
    }

    public GunniteWorkbenchRecipeBuilder addCriterion(String name, CriterionTriggerInstance criterionIn)
    {
        this.advancementBuilder.addCriterion(name, criterionIn);
        return this;
    }

    public GunniteWorkbenchRecipeBuilder addCondition(ICondition condition)
    {
        this.conditions.add(condition);
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer)
    {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
        this.build(consumer, resourcelocation);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id)
    {
        this.validate(id);
        this.advancementBuilder.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        consumer.accept(new GunniteWorkbenchRecipeBuilder.Result(id, this.result, this.count, this.ingredients, this.conditions, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
    }

    /**
     * Makes sure that this recipe is valid and obtainable.
     */
    private void validate(ResourceLocation id)
    {
        if(this.advancementBuilder.getCriteria().isEmpty())
        {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Item item;
        private final int count;
        private final List<ScrapWorkbenchIngredient> ingredients;
        private final List<ICondition> conditions;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, ItemLike item, int count, List<ScrapWorkbenchIngredient> ingredients, List<ICondition> conditions, Advancement.Builder advancement, ResourceLocation advancementId)
        {
            this.id = id;
            this.item = item.asItem();
            this.count = count;
            this.ingredients = ingredients;
            this.conditions = conditions;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json)
        {
            JsonArray conditions = new JsonArray();
            this.conditions.forEach(condition -> conditions.add(CraftingHelper.serialize(condition)));
            if(conditions.size() > 0)
            {
                json.add("conditions", conditions);
            }

            JsonArray materials = new JsonArray();
            this.ingredients.forEach(ingredient -> materials.add(ingredient.toJson()));
            json.add("materials", materials);

            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.item).toString());
            if(this.count > 1)
            {
                resultObject.addProperty("count", this.count);
            }
            json.add("result", resultObject);
        }

        @Override
        public ResourceLocation getId()
        {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return ModRecipeSerializers.GUNNITE_WORKBENCH.get();
        }

        @Override
        public JsonObject serializeAdvancement()
        {
            return this.advancement.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId()
        {
            return this.advancementId;
        }
    }
}
