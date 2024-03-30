package ttv.migami.jeg.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import ttv.migami.jeg.client.GunModel;
import ttv.migami.jeg.client.SpecialModels;
import ttv.migami.jeg.client.render.gun.IOverrideModel;
import ttv.migami.jeg.client.util.RenderUtil;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.init.ModItems;
import ttv.migami.jeg.item.attachment.IAttachment;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class RevolverModel implements IOverrideModel
{
    @Override
    public void render(float partialTicks, ItemDisplayContext display, ItemStack stack, ItemStack parent, @Nullable LivingEntity entity, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        BakedModel bakedModel = SpecialModels.REVOLVER_MAIN.getModel();

        if(Gun.hasAttachmentEquipped(stack, IAttachment.Type.STOCK)) {

            if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.MAKESHIFT_STOCK.get())
                RenderUtil.renderModel(SpecialModels.REVOLVER_STOCK_MAKESHIFT.getModel(), stack, poseStack, buffer, light, overlay);

        }

        if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.BARREL)) {
            if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.SILENCER.get())
                RenderUtil.renderModel(SpecialModels.REVOLVER_SILENCER.getModel(), stack, poseStack, buffer, light, overlay);
        }

        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE, false, poseStack, buffer, light, overlay, GunModel.wrap(bakedModel));

        float cooldown = 0F;
        float cooldown2 = 0F;
        if(entity != null && entity.equals(Minecraft.getInstance().player))
        {
            ItemCooldowns tracker = Minecraft.getInstance().player.getCooldowns();
            cooldown = tracker.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());
            cooldown = (float) easeInOutBack(cooldown);
            cooldown2 = tracker.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());
            cooldown2 = (float) ease(cooldown2);
        }

        poseStack.pushPose();
        poseStack.translate(0, -5.8 * 0.0625, 0);
        poseStack.mulPose(Axis.ZN.rotationDegrees(45F * cooldown));
        poseStack.translate(0, 5.8 * 0.0625, 0);
        RenderUtil.renderModel(SpecialModels.REVOLVER_CHAMBER.getModel(), display, null, stack, parent, poseStack, buffer, light, overlay);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0, -5.8 * 0.0625, 0);
        /**
         * We are moving whatever part is moving.
         * X,Y,Z, use Z for moving back and forth.
         * The higher the number, the shorter the distance.
         */
        poseStack.translate(0, 0, cooldown2 / - 20);
        poseStack.translate(0, 5.8 * 0.0625, 0);
        //Renders the moving part of the gun.
        RenderUtil.renderModel(SpecialModels.REVOLVER_BOLT.getModel(), stack, poseStack, buffer, light, overlay);
        //Always pop
        poseStack.popPose();

    }

    private double easeInOutBack(double x)
    {
        double c1 = 1.70158;
        double c2 = c1 * 1.525;
        return (x < 0.5 ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2 : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2);
    }

    private double ease(double x) {

        return 1 - Math.pow(1 - (2 * x), 4);

    }
}
