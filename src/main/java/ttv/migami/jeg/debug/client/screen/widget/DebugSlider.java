package ttv.migami.jeg.debug.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ttv.migami.jeg.debug.IDebugWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.gui.widget.ForgeSlider;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class DebugSlider extends ForgeSlider implements IDebugWidget
{
    private final Consumer<Double> callback;

    public DebugSlider(double minValue, double maxValue, double currentValue, double stepSize, int precision, Consumer<Double> callback)
    {
        super(0, 0, 0, 14, TextComponent.EMPTY, TextComponent.EMPTY, minValue, maxValue, currentValue, stepSize, precision, true);
        this.callback = callback;
    }

    @Override
    protected void applyValue()
    {
        this.callback.accept(this.getValue());
    }

    @Override
    protected void renderBg(PoseStack poseStack, Minecraft mc, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.isHoveredOrFocused() ? 2 : 1) * 20;
        this.blit(poseStack, this.x + (int) (this.value * (double) (this.width - 8)), this.y, 0, 46 + i, 4, this.height);
        this.blit(poseStack, this.x + (int) (this.value * (double) (this.width - 8)) + 4, this.y, 196, 46 + i, 4, this.height);
    }
}
