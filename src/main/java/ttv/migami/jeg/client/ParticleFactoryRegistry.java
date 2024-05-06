package ttv.migami.jeg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.client.particle.*;
import ttv.migami.jeg.init.ModParticleTypes;
import ttv.migami.jeg.client.particle.CasingParticle;
import ttv.migami.jeg.client.particle.ScrapParticle;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleFactoryRegistry
{
    @SubscribeEvent
    public static void onRegisterParticleFactory(RegisterParticleProvidersEvent event)
    {
        ParticleEngine particleManager = Minecraft.getInstance().particleEngine;
        particleManager.register(ModParticleTypes.BULLET_HOLE.get(), (typeIn, worldIn, x, y, z, xSpeed, ySpeed, zSpeed) -> new BulletHoleParticle(worldIn, x, y, z, typeIn.getDirection(), typeIn.getPos()));
        particleManager.register(ModParticleTypes.BLOOD.get(), BloodParticle.Factory::new);
        particleManager.register(ModParticleTypes.TRAIL.get(), TrailParticle.Factory::new);
        particleManager.register(ModParticleTypes.CASING_PARTICLE.get(), CasingParticle.Provider::new);
        particleManager.register(ModParticleTypes.SHELL_PARTICLE.get(), CasingParticle.Provider::new);
        particleManager.register(ModParticleTypes.SPECTRE_CASING_PARTICLE.get(), CasingParticle.Provider::new);
        particleManager.register(ModParticleTypes.SCRAP.get(), ScrapParticle.Provider::new);
        particleManager.register(ModParticleTypes.HEALING_GLINT.get(), HealingGlintParticle.Provider::new);
        particleManager.register(ModParticleTypes.GHOST_FLAME.get(), GhostFlameParticle.Provider::new);
        particleManager.register(ModParticleTypes.GHOST_GLINT.get(), HealingGlintParticle.GhostProvider::new);
        particleManager.register(ModParticleTypes.TYPHOONEE_BEAM.get(), TyphooneeBeamParticle.Provider::new);
    }
}
