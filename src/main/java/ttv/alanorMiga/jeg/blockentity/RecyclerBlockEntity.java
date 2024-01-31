package ttv.alanorMiga.jeg.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import ttv.alanorMiga.jeg.common.container.RecyclerMenu;
import ttv.alanorMiga.jeg.init.ModRecipeTypes;
import ttv.alanorMiga.jeg.init.ModTileEntities;

public class RecyclerBlockEntity extends AbstractRecyclerBlockEntity {
    public RecyclerBlockEntity(BlockPos p_155545_, BlockState p_155546_) {
        super(ModTileEntities.RECYCLER.get(), p_155545_, p_155546_, ModRecipeTypes.RECYCLING.get());
    }

    protected Component getDefaultName() {
        return Component.translatable("container.jeg.recycler");
    }

    protected AbstractContainerMenu createMenu(int p_59293_, Inventory p_59294_) {
        return new RecyclerMenu(p_59293_, p_59294_, this, this.dataAccess);
    }
}
