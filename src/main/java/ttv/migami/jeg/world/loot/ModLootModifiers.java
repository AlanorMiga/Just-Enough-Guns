package ttv.migami.jeg.world.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.jeg.Reference;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModLootModifiers {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().registerAll(
                new TechTrashOnSimpleDungeonModifier.Serializer().setRegistryName(new ResourceLocation(Reference.MOD_ID,"tech_trash_on_simple_dungeon")),
                new ScrapOnSimpleDungeonModifier.Serializer().setRegistryName(new ResourceLocation(Reference.MOD_ID,"scrap_on_simple_dungeon"))
        );
    }
}