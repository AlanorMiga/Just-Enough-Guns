package ttv.alanorMiga.jeg.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import ttv.alanorMiga.jeg.blockentity.AbstractRecyclerBlockEntity;
import ttv.alanorMiga.jeg.blockentity.RecyclerBlockEntity;
import ttv.alanorMiga.jeg.init.ModParticleTypes;
import ttv.alanorMiga.jeg.init.ModSounds;
import ttv.alanorMiga.jeg.init.ModTileEntities;

import javax.annotation.Nullable;
import java.util.Random;

public class RecyclerBlock extends AbstractRecyclerBlock {
    //private static final VoxelShape INSIDE = box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    //protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);
    private static final VoxelShape SHAPE = makeShape();

    private static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0.5625, 0.125, 0.875, 0.875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 0.25, 0.1875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.75, 0.125, 0.1875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.875, 0.875, 0.1875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.75, 1, 0.1875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0, 1, 0.1875, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.125, 1, 0.1875, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0, 0.25, 0.1875, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 0.1875, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.1875, 0, 1, 1, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0.875, 0.875, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0, 0.125, 1, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.1875, 0.125, 1, 1, 1), BooleanOp.OR);
        return shape;
    }

    private static final VoxelShape COVER = makeCover();

    public static VoxelShape makeCover() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 1.1875, -0.1875, 1, 1.4375, -0.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1, -0.0625, 1, 1.25, 0.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1.1875, 1.0625, 1, 1.4375, 1.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1, 0.9375, 1, 1.25, 1.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.9375, 1, 0, 1.0625, 1.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.0625, 1, 0, 0.0625, 1.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.1875, 1.1875, 0, -0.0625, 1.4375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.0625, 1.1875, 0, 1.1875, 1.4375, 1), BooleanOp.OR);
        return shape;
    }

    private static final VoxelShape SHAPE_COLLISION = Shapes.or(SHAPE, COVER);

    @Override
    public VoxelShape getShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return SHAPE_COLLISION;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return SHAPE;
    }

    public VoxelShape getOcclusionShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return SHAPE;
    }

    public boolean useShapeForLightOcclusion(BlockState p_151964_) {
        return true;
    }


    public RecyclerBlock(BlockBehaviour.Properties p_53627_) {
        super(p_53627_);
    }

    public BlockEntity newBlockEntity(BlockPos p_153277_, BlockState p_153278_) {
        return new RecyclerBlockEntity(p_153277_, p_153278_);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_) {
        return createRecyclerTicker(p_153273_, p_153275_, ModTileEntities.RECYCLER.get());
    }

    protected void openContainer(Level p_53631_, BlockPos p_53632_, Player p_53633_) {
        BlockEntity blockentity = p_53631_.getBlockEntity(p_53632_);
        if (blockentity instanceof RecyclerBlockEntity) {
            p_53633_.openMenu((MenuProvider) blockentity);
        }
    }

    public void animateTick(BlockState p_53635_, Level p_53636_, BlockPos p_53637_, Random p_53638_) {
        AbstractRecyclerBlockEntity p_155017_ = (AbstractRecyclerBlockEntity) p_53636_.getBlockEntity(p_53637_);
        ItemStack itemStack = p_155017_.items.get(0);
        //if (p_53635_.getValue(LIT) && itemStack.isEmpty())
        if (p_53635_.getValue(LIT))
        {
            double d0 = (double) p_53637_.getX() + 0.5D;
            double d1 = (double) p_53637_.getY();
            double d2 = (double) p_53637_.getZ() + 0.5D;
            if (p_53638_.nextDouble() < 0.1D) {
                p_53636_.playLocalSound(d0, d1, d2, ModSounds.RECYCLER_SHREDDING.get(), SoundSource.BLOCKS, 0.2F, 1.0F, false);
                p_53636_.playLocalSound(d0, d1, d2, ModSounds.RECYCLER_LOOP.get(), SoundSource.BLOCKS, 0.15F, 1.0F, false);
            }
            double x = p_53637_.getX() + 0.5;
            double y = p_53637_.getY() + 0.9;
            double z = p_53637_.getZ() + 0.5;
            p_53636_.addParticle(ModParticleTypes.SCRAP.get(), true, x, y, z, 1, 0, 0);
        }
    }
}