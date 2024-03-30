package ttv.migami.jeg.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ttv.migami.jeg.client.handler.BulletTrailRenderingHandler;

/**
 * Author: MrCrayfish
 */
@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;checkPoseStack(Lcom/mojang/blaze3d/vertex/PoseStack;)V", ordinal = 0))
    private void renderBullets(PoseStack stack, float partialTicks, long finishTimeNano, boolean drawBlockOutline, Camera info, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projection, CallbackInfo ci)
    {
        BulletTrailRenderingHandler.get().render(stack, partialTicks);
    }
}
