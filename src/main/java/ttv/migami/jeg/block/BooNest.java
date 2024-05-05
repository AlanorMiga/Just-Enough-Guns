package ttv.migami.jeg.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import ttv.migami.jeg.blockentity.BooNestBlockEntity;
import ttv.migami.jeg.init.ModItems;
import ttv.migami.jeg.init.ModTileEntities;

import javax.annotation.Nullable;

public class BooNest extends BeehiveBlock
{
    public BooNest(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModTileEntities.BOO_NEST.get(), BooNestBlockEntity::tick);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BooNestBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        int i = state.getValue(HONEY_LEVEL);
        if (i >= 5) {
            if (itemstack.getItem() == Items.AIR || itemstack.getItem() == Items.SHEARS) {
                worldIn.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.NEUTRAL, 1.0F, 1.0F);
                popResource(worldIn, pos, new ItemStack(ModItems.ECTOPLASM.get(), 5));
                this.resetHoneyLevel(worldIn, state, pos);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}
