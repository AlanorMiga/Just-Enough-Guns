package ttv.migami.jeg.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import ttv.migami.jeg.init.ModTileEntities;

public class BooNestBlockEntity extends BeehiveBlockEntityAbstract
{
    public BooNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModTileEntities.BOO_NEST.get(), pos, state);
    }
}
