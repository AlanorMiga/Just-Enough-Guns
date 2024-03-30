package ttv.migami.jeg.event;

import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;

public class BeeReleaseEvent extends Event {
    private final Level level;
    private final Bee beeEntity;
    private final BlockEntity blockEntity;
    private final BlockState state;
    private final BeehiveBlockEntity.BeeReleaseStatus beeState;

    public BeeReleaseEvent(Level level, Bee beeEntity, BlockEntity blockEntity, BlockState state, BeehiveBlockEntity.BeeReleaseStatus beeState) {
        this.level = level;
        this.beeEntity = beeEntity;
        this.blockEntity = blockEntity;
        this.state = state;
        this.beeState = beeState;
    }

    public Level getLevel() {
        return this.level;
    }

    public Bee getBee() {
        return this.beeEntity;
    }

    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public BlockState getState() {
        return this.state;
    }

    public BeehiveBlockEntity.BeeReleaseStatus getBeeState() {
        return this.beeState;
    }
}