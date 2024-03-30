package ttv.migami.jeg.debug.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import org.jetbrains.annotations.NotNull;
import ttv.migami.jeg.debug.IDebugWidget;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class DebugSlider extends ForgeSlider implements IDebugWidget
{
    private final Consumer<Double> callback;

    public DebugSlider(double minValue, double maxValue, double currentValue, double stepSize, int precision, Consumer<Double> callback)
    {
        super(0, 0, 0, 14, Component.empty(), Component.empty(), minValue, maxValue, currentValue, stepSize, precision, true);
        this.callback = callback;
    }

    @Override
    protected void applyValue()
    {
        this.callback.accept(this.getValue());
    }

    @Override
    public void renderWidget(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.isHoveredOrFocused() ? 2 : 1) * 20;
        blit(poseStack, this.getX() + (int) (this.value * (double) (this.width - 8)), this.getY(), 0, 46 + i, 4, this.height);
        blit(poseStack, this.getX() + (int) (this.value * (double) (this.width - 8)) + 4, this.getY(), 196, 46 + i, 4, this.height);
    }
}
