package ttv.migami.jeg.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.entity.Bubble;

public class BubbleRenderer extends EntityRenderer<Bubble> {
    private static int index = 0;
    private static ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Reference.MOD_ID, "textures/entity/bubble.png");
    private final BubbleModel<Bubble> model;

    public BubbleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BubbleModel<>(pContext.bakeLayer(ModModelLayers.BUBBLE));
    }

    @Override
    public ResourceLocation getTextureLocation(Bubble pEntity) {
        return TEXTURE_LOCATION;
    }

    @Override
    protected int getBlockLightLevel(Bubble pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public void render(Bubble pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        // Calculate rotation to face the camera
        Quaternion rotation = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();
        pPoseStack.mulPose(rotation);
        pPoseStack.scale(1.5F, 1.5F, 1.5F);
        pPoseStack.translate(0, -0.5, 0);

        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_LOCATION));

        // Render with billboarding
        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        pPoseStack.popPose();
    }
}
