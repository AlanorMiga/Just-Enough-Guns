package ttv.migami.jeg.client.render.entity;

import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Bee;
import ttv.migami.jeg.Reference;

public class BooRenderer extends BeeRenderer {
    public BooRenderer(EntityRendererProvider.Context p_173931_) {
        super(p_173931_);
    }

    private static final ResourceLocation ANGRY_BOO_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/boo/boo_angry.png");
    private static final ResourceLocation ANGRY_NECTAR_BOO_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/boo/boo_angry_nectar.png");
    private static final ResourceLocation BOO_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/boo/boo.png");
    private static final ResourceLocation NECTAR_BOO_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/boo/boo_nectar.png");

    @Override
    public ResourceLocation getTextureLocation(Bee pEntity) {
        if (pEntity.isAngry()) {
            return pEntity.hasNectar() ? ANGRY_NECTAR_BOO_TEXTURE : ANGRY_BOO_TEXTURE;
        } else {
            return pEntity.hasNectar() ? NECTAR_BOO_TEXTURE : BOO_TEXTURE;
        }
    }

    @Override
    protected int getBlockLightLevel(Bee pEntity, BlockPos pPos) {
        return 10;
    }

}
