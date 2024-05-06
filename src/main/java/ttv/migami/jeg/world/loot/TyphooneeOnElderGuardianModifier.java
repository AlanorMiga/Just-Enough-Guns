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

public class TyphooneeOnElderGuardianModifier extends LootModifier {
        private final Item addition;

        protected TyphooneeOnElderGuardianModifier(LootItemCondition[] conditionsIn, Item addition) {
            super(conditionsIn);
            this.addition = addition;
        }

        @Nonnull
        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            if(context.getRandom().nextFloat() > 0.00F) { // 100% chance of the item spawning in.
                generatedLoot.add(new ItemStack(addition, 1));
            }
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<TyphooneeOnElderGuardianModifier> {

            @Override
            public TyphooneeOnElderGuardianModifier read(ResourceLocation name, JsonObject object, LootItemCondition[] conditionsIn) {
                Item addition = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object, "addition")));
                return new TyphooneeOnElderGuardianModifier(conditionsIn, addition);
            }

            @Override
            public JsonObject write(TyphooneeOnElderGuardianModifier instance) {
                JsonObject json = makeConditions(instance.conditions);
                json.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());
                return json;
            }
        }
    }
