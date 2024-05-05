package ttv.migami.jeg.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.client.render.entity.GrenadeRenderer;
import ttv.migami.jeg.client.render.entity.MissileRenderer;
import ttv.migami.jeg.client.render.entity.ProjectileRenderer;
import ttv.migami.jeg.client.render.entity.ThrowableGrenadeRenderer;
import ttv.migami.jeg.init.ModEntities;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GunEntityRenderers
{
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntities.PROJECTILE.get(), ProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.SPECTRE_PROJECTILE.get(), ProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.WATER_PROJECTILE.get(), ProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.WATER_BOMB.get(), ProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.POCKET_BUBBLE.get(), ProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.GRENADE.get(), GrenadeRenderer::new);
        event.registerEntityRenderer(ModEntities.MISSILE.get(), MissileRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWABLE_GRENADE.get(), ThrowableGrenadeRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWABLE_STUN_GRENADE.get(), ThrowableGrenadeRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWABLE_MOLOTOV_COCKTAIL.get(), ThrowableGrenadeRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWABLE_WATER_BOMB.get(), ThrowableGrenadeRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWABLE_POCKET_BUBBLE.get(), ThrowableGrenadeRenderer::new);
    }
}
