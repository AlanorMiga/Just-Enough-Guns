package ttv.migami.jeg.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import ttv.migami.jeg.Reference;

public class GhoulRenderer extends ZombieRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/zombie/ghoul.png");

    public GhoulRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    protected void scale(Zombie pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = 1.0F;
        pPoseStack.scale(f, f, f);
        super.scale(pLivingEntity, pPoseStack, pPartialTickTime);
    }

    @Override
    protected int getBlockLightLevel(Zombie pEntity, BlockPos pPos) {
        return 7;
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(Zombie pEntity) {
        return TEXTURE;
    }
}
