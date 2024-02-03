package ttv.alanorMiga.jeg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.client.particle.BloodParticle;
import ttv.alanorMiga.jeg.client.particle.BulletHoleParticle;
import ttv.alanorMiga.jeg.client.particle.TrailParticle;
import ttv.alanorMiga.jeg.init.ModParticleTypes;
import ttv.alanorMiga.jeg.particles.CasingParticle;
import ttv.alanorMiga.jeg.particles.ScrapParticle;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleFactoryRegistry
{
    @SubscribeEvent
    public static void onRegisterParticleFactory(ParticleFactoryRegisterEvent event)
    {
        ParticleEngine particleManager = Minecraft.getInstance().particleEngine;
        particleManager.register(ModParticleTypes.BULLET_HOLE.get(), (typeIn, worldIn, x, y, z, xSpeed, ySpeed, zSpeed) -> new BulletHoleParticle(worldIn, x, y, z, typeIn.getDirection(), typeIn.getPos()));
        particleManager.register(ModParticleTypes.BLOOD.get(), BloodParticle.Factory::new);
        particleManager.register(ModParticleTypes.TRAIL.get(), TrailParticle.Factory::new);
        particleManager.register(ModParticleTypes.CASING_PARTICLE.get(), CasingParticle.Provider::new);
        particleManager.register(ModParticleTypes.SHELL_PARTICLE.get(), CasingParticle.Provider::new);
        particleManager.register(ModParticleTypes.SCRAP.get(), ScrapParticle.Provider::new);
    }
}
