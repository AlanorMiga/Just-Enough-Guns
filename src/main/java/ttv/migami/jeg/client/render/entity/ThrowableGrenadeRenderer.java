package ttv.migami.jeg.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import ttv.migami.jeg.entity.throwable.ThrowableGrenadeEntity;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class ThrowableGrenadeRenderer extends EntityRenderer<ThrowableGrenadeEntity>
{
    public ThrowableGrenadeRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(ThrowableGrenadeEntity entity)
    {
        return null;
    }

    @Override
    public void render(ThrowableGrenadeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light)
    {
        poseStack.pushPose();

        /* */
        poseStack.translate(0.0, 0.0, 0.0);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(), ItemTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, renderTypeBuffer, 0);

        poseStack.popPose();
    }
}
