package ttv.migami.jeg.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.jeg.Config;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.entity.throwable.ThrowableGrenadeEntity;
import ttv.migami.jeg.init.ModSounds;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EntityKillEventHandler {

    private static final Random RANDOM = new Random();
    private static final double GRENADE_SPAWN_CHANCE = 0.05; // 5% chance

    @SubscribeEvent
    public static void onCreeperKilled(LivingDeathEvent event) {
        if (event.getEntity().level.isClientSide) {
            return;
        }

        if ((event.getEntity() instanceof Creeper)) {
            if (RANDOM.nextDouble() < GRENADE_SPAWN_CHANCE && Config.COMMON.world.creepersDropLiveGrenades.get()) {
                ServerLevel serverLevel = (ServerLevel) event.getEntity().level;
                LivingEntity creeper = event.getEntity();
                BlockPos pos = creeper.blockPosition();
                ThrowableGrenadeEntity grenade = new ThrowableGrenadeEntity(creeper.level, creeper, 60);
                LivingEntity killer = creeper.getKillCredit();
                if (killer instanceof Player) {
                    killer.level.playLocalSound(killer.getX(), killer.getY(), killer.getZ(), ModSounds.ITEM_GRENADE_PIN.get(), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                }
                serverLevel.playSound(null, creeper.blockPosition(), ModSounds.ITEM_GRENADE_PIN.get(), SoundSource.HOSTILE, 10F, 1F);
                grenade.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                serverLevel.addFreshEntity(grenade);
            }
        }
    }
}