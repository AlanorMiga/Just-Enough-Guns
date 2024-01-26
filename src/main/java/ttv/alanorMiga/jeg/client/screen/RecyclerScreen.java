package ttv.alanorMiga.jeg.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ttv.alanorMiga.jeg.common.container.RecyclerMenu;

@OnlyIn(Dist.CLIENT)
public class RecyclerScreen extends AbstractRecyclerScreen<RecyclerMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("jeg:textures/gui/recycler.png");

    public RecyclerScreen(RecyclerMenu p_98776_, Inventory p_98777_, Component p_98778_) {
        super(p_98776_, p_98777_, p_98778_, TEXTURE);
    }
}