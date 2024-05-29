package ttv.migami.jeg.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.entity.Splash;

public class SplashRenderer extends EntityRenderer<Splash> {
    private static int index = 0;
    private static ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Reference.MOD_ID, "textures/entity/splash_" + index + ".png");
    private final SplashModel<Splash> model;

    public SplashRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new SplashModel<>(pContext.bakeLayer(ModModelLayers.SPLASH));
    }

    @Override
    public ResourceLocation getTextureLocation(Splash pEntity) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/splash_" + index + ".png");
    }

    @Override
    public void render(Splash pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pEntity.tickCount * 10));
        //pPoseStack.scale(1, -1, 1);
        //pPoseStack.translate(0.0D, -1.5D, 0.0D);

        index ++;
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(new ResourceLocation(Reference.MOD_ID, "textures/entity/splash_" + pEntity.getTextureIndex() + ".png")));

        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();

        //super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
