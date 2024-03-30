package ttv.migami.jeg.world.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ScrapOnSimpleDungeonModifier extends LootModifier {
    private final Item addition;

    protected ScrapOnSimpleDungeonModifier(LootItemCondition[] conditionsIn, Item addition) {
        super(conditionsIn);
        this.addition = addition;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if(context.getRandom().nextFloat() > 0.25F) { //75% chance of the item spawning in.
            int random = new Random().nextInt(6) + 1; //Min 1, Max 6.
            generatedLoot.add(new ItemStack(addition, random));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<ScrapOnSimpleDungeonModifier> {

        @Override
        public ScrapOnSimpleDungeonModifier read(ResourceLocation name, JsonObject object, LootItemCondition[] conditionsIn) {
            Item addition = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object, "addition")));
            return new ScrapOnSimpleDungeonModifier(conditionsIn, addition);
        }

        @Override
        public JsonObject write(ScrapOnSimpleDungeonModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());
            return json;
        }
    }
}
