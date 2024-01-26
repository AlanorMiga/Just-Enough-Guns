package ttv.alanorMiga.jeg.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import ttv.alanorMiga.jeg.client.SpecialModels;
import ttv.alanorMiga.jeg.client.render.gun.IOverrideModel;
import ttv.alanorMiga.jeg.client.util.RenderUtil;

/**
 * Since we want to have an animation for the charging handle, we will be overriding the standard model rendering.
 * This also allows us to replace the model for the different stocks.
 */
public class HkG36Model implements IOverrideModel {

    @SuppressWarnings("resource")
    @Override
    public void render(float partialTicks, ItemTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {

        //Renders the static parts of the model.
        RenderUtil.renderModel(SpecialModels.HK_G36.getModel(), stack, matrixStack, buffer, light, overlay);

    }

    private double ease(double x) {

        return 1 - Math.pow(1 - (2 * x), 4);

    }

}