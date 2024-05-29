package ttv.migami.jeg.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.jeg.Config;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.item.GunItem;
import ttv.migami.jeg.util.GunModifierHelper;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
/*
 * Credit: NineZero
 */
public class AmmoCounterRenderer {
    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");

    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 10;
    private static final int PADDING = 75;
    private static final int SEGMENT_PADDING = 0;

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.isCanceled()) return;

        if(!Config.CLIENT.display.displayAmmoGUI.get())
            return;

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player != null) {
            Item heldItem = player.getMainHandItem().getItem();
            ItemStack itemStack = player.getMainHandItem();

            if (heldItem instanceof GunItem) {
                Gun gun = ((GunItem) player.getMainHandItem().getItem()).getModifiedGun(itemStack);
                CompoundTag tagCompound = itemStack.getTag();
                if (minecraft.screen != null)
                    return;
                if (tagCompound != null) {
                    Window window = minecraft.getWindow();

                    int x = (minecraft.getWindow().getGuiScaledWidth() - BAR_WIDTH - PADDING) + Config.CLIENT.display.displayAmmoGUIXOffset.get();
                    int y = (int) ((window.getGuiScaledHeight() * 0.85)) + Config.CLIENT.display.displayAmmoGUIYOffset.get();

                    int currentAmmo = tagCompound.getInt("AmmoCount");
                    int maxAmmo = GunModifierHelper.getModifiedAmmoCapacity(itemStack, gun);
                    MutableComponent name = new TranslatableComponent(itemStack.getDescriptionId()).withStyle(ChatFormatting.WHITE);

                    PoseStack poseStack = event.getMatrixStack();

                    if(!Config.CLIENT.display.classicAmmoGUI.get()) {
                        if (maxAmmo > 100) {
                            drawAmmoBar(poseStack, x, y, currentAmmo, maxAmmo, name, 255);
                        } else {
                            drawSegmentedBar(poseStack, x, y, currentAmmo, maxAmmo, name, 255);
                        }
                    } else {
                        MutableComponent ammoCountValue = (new TextComponent(currentAmmo + " / " + GunModifierHelper.getModifiedAmmoCapacity(itemStack, gun)).withStyle(ChatFormatting.BOLD));
                        minecraft.font.draw(poseStack, ammoCountValue, x, y, 0xFFFFFF);
                    }

                    RenderSystem.disableBlend();
                }
            }
        }
    }

    private static void drawAmmoBar(PoseStack poseStack, int x, int y, int currentAmount, int maxAmount, MutableComponent gunName, int alpha) {
        RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);

        int barColor = (alpha << 24) | 0xFFFFFF;
        int backgroundColor = (alpha << 24) | 0x3F3F3F;

        int barWidth = (int) ((BAR_WIDTH - 2) * ((double) currentAmount / maxAmount));

        fill(poseStack, x, y + 1, x + 1 + barWidth, y + BAR_HEIGHT, backgroundColor);
        fill(poseStack, x, y + 1, x + 1 + barWidth, y + BAR_HEIGHT - 1, barColor);

        Minecraft.getInstance().font.draw(poseStack, gunName, x, y - 10, 0xFFFFFF);
    }

    private static void drawSegmentedBar(PoseStack poseStack, int x, int y, int currentAmount, int maxAmount, MutableComponent gunName, int alpha) {
        RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);

        int segmentWidth = (BAR_WIDTH - (Math.min(maxAmount, 30) - 1) * SEGMENT_PADDING) / Math.min(maxAmount, 30);
        int barColor = (alpha << 24) | 0xFFFFFF;
        int backgroundColor = (alpha << 24) | 0x3F3F3F;
        int segmentsPerRow = Math.min(maxAmount, 30);
        int numRows = (int) Math.ceil((double) maxAmount / segmentsPerRow);

        for (int row = 0; row < numRows; row++) {
            int rowStartIndex = row * segmentsPerRow;
            int rowEndIndex = Math.min(rowStartIndex + segmentsPerRow, maxAmount);
            int rowY = y + row * (BAR_HEIGHT + SEGMENT_PADDING);

            int backgroundWidth = (row < numRows - 1) ? segmentsPerRow * segmentWidth + (segmentsPerRow - 1) * SEGMENT_PADDING
                    : (maxAmount % segmentsPerRow) * segmentWidth + ((maxAmount % segmentsPerRow) - 1) * SEGMENT_PADDING;

            if (backgroundWidth == 0) {
                backgroundWidth = segmentsPerRow * segmentWidth + (segmentsPerRow - 1) * SEGMENT_PADDING;
            }

            fill(poseStack, x, rowY + 1, x + backgroundWidth, rowY + BAR_HEIGHT, backgroundColor);
        }

        for (int row = 0; row < numRows; row++) {
            int rowStartIndex = row * segmentsPerRow;
            int rowEndIndex = Math.min(rowStartIndex + segmentsPerRow, maxAmount);
            int rowY = y + row * (BAR_HEIGHT + SEGMENT_PADDING);

            for (int i = rowStartIndex; i < rowEndIndex; i++) {
                int segmentX = x - 1 + (i - rowStartIndex) * (segmentWidth + SEGMENT_PADDING);
                if (i < currentAmount) {
                    fill(poseStack, segmentX + 1, rowY + 1, segmentX + segmentWidth, rowY + BAR_HEIGHT - 1, barColor);
                }
            }
        }

        Minecraft.getInstance().font.drawShadow(poseStack, gunName, x, y - 10, 0xFFFFFF);
    }

    private static void fill(PoseStack poseStack, int x1, int y1, int x2, int y2, int color) {
        Gui.fill(poseStack, x1, y1, x2, y2, color);
    }
}