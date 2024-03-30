package ttv.migami.jeg.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import ttv.migami.jeg.client.handler.GunRenderingHandler;

/**
 * Author: MrCrayfish
 */
public class GunItemStackRenderer extends BlockEntityWithoutLevelRenderer
{
    public GunItemStackRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType display, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        // Hack to remove transforms created by ItemRenderer#render
        poseStack.popPose();

        poseStack.pushPose();
        {
            Minecraft mc = Minecraft.getInstance();
            if(display == ItemTransforms.TransformType.GROUND)
            {
                GunRenderingHandler.get().applyWeaponScale(stack, poseStack);
            }
            GunRenderingHandler.get().renderWeapon(mc.player, stack, display, poseStack, source, light, Minecraft.getInstance().getDeltaFrameTime());
        }
        poseStack.popPose();

        // Push the stack again since we popped the pose prior
        poseStack.pushPose();
    }
}
