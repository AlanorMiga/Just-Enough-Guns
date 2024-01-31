package ttv.alanorMiga.jeg.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class ScrapWorkbenchIngredient extends Ingredient
{
    private final Value itemList;
    private final int count;

    protected ScrapWorkbenchIngredient(Stream<? extends Value> itemList, int count)
    {
        super(itemList);
        this.itemList = null;
        this.count = count;
    }

    private ScrapWorkbenchIngredient(Value itemList, int count)
    {
        super(Stream.of(itemList));
        this.itemList = itemList;
        this.count = count;
    }

    public int getCount()
    {
        return this.count;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    public static ScrapWorkbenchIngredient fromJson(JsonObject object)
    {
        Ingredient.Value value = valueFromJson(object);
        int count = GsonHelper.getAsInt(object, "count", 1);
        return new ScrapWorkbenchIngredient(Stream.of(value), count);
    }

    @Override
    public JsonElement toJson()
    {
        JsonObject object = this.itemList.serialize();
        object.addProperty("count", this.count);
        return object;
    }

    public static ScrapWorkbenchIngredient of(ItemLike provider, int count)
    {
        return new ScrapWorkbenchIngredient(new Ingredient.ItemValue(new ItemStack(provider)), count);
    }

    public static ScrapWorkbenchIngredient of(ItemStack stack, int count)
    {
        return new ScrapWorkbenchIngredient(new Ingredient.ItemValue(stack), count);
    }

    public static ScrapWorkbenchIngredient of(TagKey<Item> tag, int count)
    {
        return new ScrapWorkbenchIngredient(new Ingredient.TagValue(tag), count);
    }

    public static ScrapWorkbenchIngredient of(ResourceLocation id, int count)
    {
        return new ScrapWorkbenchIngredient(new UnknownValue(id), count);
    }

    public static class Serializer implements IIngredientSerializer<ScrapWorkbenchIngredient>
    {
        public static final ScrapWorkbenchIngredient.Serializer INSTANCE = new ScrapWorkbenchIngredient.Serializer();

        @Override
        public ScrapWorkbenchIngredient parse(FriendlyByteBuf buffer)
        {
            int itemCount = buffer.readVarInt();
            int count = buffer.readVarInt();
            Stream<Ingredient.ItemValue> values = Stream.generate(() -> new ItemValue(buffer.readItem())).limit(itemCount);
            return new ScrapWorkbenchIngredient(values, count);
        }

        @Override
        public ScrapWorkbenchIngredient parse(JsonObject object)
        {
            return ScrapWorkbenchIngredient.fromJson(object);
        }

        @Override
        public void write(FriendlyByteBuf buffer, ScrapWorkbenchIngredient ingredient)
        {
            buffer.writeVarInt(ingredient.getItems().length);
            buffer.writeVarInt(ingredient.count);
            for(ItemStack stack : ingredient.getItems())
            {
                buffer.writeItem(stack);
            }
        }
    }

    /**
     * Allows ability to define an ingredient from another mod without adding it as a dependency in
     * the development environment. Serializes the data to be read by the regular
     * {@link ItemValue}. Only use this for generating data.
     */
    @SuppressWarnings("ClassCanBeRecord")
    public static class UnknownValue implements Ingredient.Value
    {
        private final ResourceLocation id;

        public UnknownValue(ResourceLocation id)
        {
            this.id = id;
        }

        @Override
        public Collection<ItemStack> getItems()
        {
            return Collections.emptyList();
        }

        @Override
        public JsonObject serialize()
        {
            JsonObject object = new JsonObject();
            object.addProperty("item", this.id.toString());
            return object;
        }
    }
}
