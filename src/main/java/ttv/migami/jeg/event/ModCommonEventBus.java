package ttv.migami.jeg.event;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.entity.monster.Ghoul;
import ttv.migami.jeg.init.ModEntities;
import ttv.migami.jeg.network.PacketHandler;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEventBus {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GHOUL.get(), Ghoul.createAttributes().build());
        event.put(ModEntities.BOO.get(), Bee.createAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.init();
            SpawnPlacements.register(ModEntities.GHOUL.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.WORLD_SURFACE, Zombie::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntities.BOO.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.WORLD_SURFACE, Animal::checkAnimalSpawnRules);
        });
    }

}
