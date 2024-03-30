package ttv.migami.jeg.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ttv.migami.jeg.client.handler.AimingHandler;
import ttv.migami.jeg.client.handler.GunRenderingHandler;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.item.GunItem;

/**
 * Author: MrCrayfish
 */
@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin
{
    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "renderArmWithItem", at = @At(value = "HEAD"), cancellable = true)
    private void renderArmWithItemHead(LivingEntity entity, ItemStack stack, ItemDisplayContext display, HumanoidArm arm, PoseStack poseStack, MultiBufferSource source, int light, CallbackInfo ci)
    {
        if(entity.getType() == EntityType.PLAYER)
        {
            InteractionHand hand = Minecraft.getInstance().options.mainHand().get() == arm ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            if(hand == InteractionHand.OFF_HAND)
            {
                if(stack.getItem() instanceof GunItem)
                {
                    ci.cancel();
                    return;
                }

                if(entity.getMainHandItem().getItem() instanceof GunItem gunItem)
                {
                    Gun modifiedGun = gunItem.getModifiedGun(entity.getMainHandItem());
                    if(!modifiedGun.getGeneral().getGripType().getHeldAnimation().canRenderOffhandItem())
                    {
                        ci.cancel();
                        return;
                    }
                }
            }
            if(stack.getItem() instanceof GunItem gunItem)
            {
                ci.cancel();
                PlayerItemInHandLayer<?, ?> layer = (PlayerItemInHandLayer<?, ?>) (Object) this;
                mrCrayfishGunMod$renderArmWithGun(layer, (Player) entity, stack, gunItem, display, hand, arm, poseStack, source, light, Minecraft.getInstance().getFrameTime());
            }
        }
    }

    @Unique
    private static void mrCrayfishGunMod$renderArmWithGun(PlayerItemInHandLayer<?, ?> layer, Player player, ItemStack stack, GunItem item, ItemDisplayContext display, InteractionHand hand, HumanoidArm arm, PoseStack poseStack, MultiBufferSource source, int light, float deltaTicks)
    {
        poseStack.pushPose();
        layer.getParentModel().translateToHand(arm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180F));
        poseStack.translate(((float) (arm == HumanoidArm.LEFT ? -1 : 1) / 16F), 0.125, -0.625);
        GunRenderingHandler.get().applyWeaponScale(stack, poseStack);
        Gun gun = item.getModifiedGun(stack);
        gun.getGeneral().getGripType().getHeldAnimation().applyHeldItemTransforms(player, hand, AimingHandler.get().getAimProgress(player, deltaTicks), poseStack, source);
        GunRenderingHandler.get().renderWeapon(player, stack, display, poseStack, source, light, deltaTicks);
        poseStack.popPose();
    }
}
