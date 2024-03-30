package ttv.migami.jeg.common.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import ttv.migami.jeg.init.ModRecipeTypes;
import ttv.migami.jeg.init.ModContainers;

public class RecyclerMenu extends AbstractRecyclerMenu {
    public RecyclerMenu(int p_39532_, Inventory p_39533_) {
        super(ModContainers.RECYCLER.get(), ModRecipeTypes.RECYCLING, p_39532_, p_39533_);
    }

    public RecyclerMenu(int p_39535_, Inventory p_39536_, Container p_39537_, ContainerData p_39538_) {
        super(ModContainers.RECYCLER.get(), ModRecipeTypes.RECYCLING, p_39535_, p_39536_, p_39537_, p_39538_);
    }
}