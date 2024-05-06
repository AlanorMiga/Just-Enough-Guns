package ttv.migami.jeg.client;

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
        event.registerSpecial(ModParticleTypes.BULLET_HOLE.get(), (typeIn, worldIn, x, y, z, xSpeed, ySpeed, zSpeed) -> new BulletHoleParticle(worldIn, x, y, z, typeIn.getDirection(), typeIn.getPos()));
        event.registerSpriteSet(ModParticleTypes.BLOOD.get(), BloodParticle.Factory::new);
        event.registerSpriteSet(ModParticleTypes.TRAIL.get(), TrailParticle.Factory::new);
        event.registerSpriteSet(ModParticleTypes.CASING_PARTICLE.get(), CasingParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SHELL_PARTICLE.get(), CasingParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SPECTRE_CASING_PARTICLE.get(), CasingParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SCRAP.get(), ScrapParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.HEALING_GLINT.get(), HealingGlintParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.GHOST_FLAME.get(), GhostFlameParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.GHOST_GLINT.get(), HealingGlintParticle.GhostProvider::new);
        event.registerSpriteSet(ModParticleTypes.TYPHOONEE_BEAM.get(), TyphooneeBeamParticle.Provider::new);
    }
}
