package ttv.migami.jeg.entity.animal;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import ttv.migami.jeg.blockentity.BeehiveBlockEntityAbstract;
import ttv.migami.jeg.common.ModTags;
import ttv.migami.jeg.init.ModEntities;
import ttv.migami.jeg.init.ModParticleTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Boo extends Bee
{
    protected Predicate<Holder<PoiType>> beehiveInterests = (poi) -> poi.is(PoiTypeTags.BEE_HOME);
    protected FollowParentGoal followParentGoal;
    protected BreedGoal breedGoal;
    protected EnterHiveGoal enterHiveGoal;
    public final float VANISHING_TIME = 600;
    public float vanishingTime = 600;
    public boolean isExposed = false;

    public Boo(EntityType<? extends Bee> entityType, Level world) {
        super(entityType, world);
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D));
        this.setPathfindingMalus(BlockPathTypes.TRAPDOOR, -1.0F);
    }

    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    protected void registerGoals() {
        registerBaseGoals();

        this.beePollinateGoal = new Boo.PollinateGoal();
        this.goalSelector.addGoal(4, this.beePollinateGoal);
        this.goToKnownFlowerGoal = new BeeGoToKnownFlowerGoal();
        this.goalSelector.addGoal(6, this.goToKnownFlowerGoal);
        this.goalSelector.addGoal(7, new BeeGrowCropGoal());
    }

    protected void registerBaseGoals() {
        this.goalSelector.addGoal(0, new BeeAttackGoal(this, 1.4D, true));
        this.enterHiveGoal = new EnterHiveGoal();
        this.goalSelector.addGoal(1, this.enterHiveGoal);
        this.breedGoal = new BreedGoal(this, 1.0D, Boo.class);
        this.goalSelector.addGoal(2, this.breedGoal);
        this.followParentGoal = new FollowParentGoal(this, 1.25D);
        this.goalSelector.addGoal(5, this.followParentGoal);
        this.goalSelector.addGoal(5, new Boo.UpdateNestGoal());
        this.goToHiveGoal = new Boo.BooGoToHiveGoal();
        this.goalSelector.addGoal(5, this.goToHiveGoal);
        this.goalSelector.addGoal(8, new BooWanderGoal());
        this.goalSelector.addGoal(9, new FloatGoal(this));
        this.targetSelector.addGoal(1, (new BeeHurtByOtherGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new BeeBecomeAngryTargetGoal(this));

        // Empty default goals
        this.beePollinateGoal = new EmptyPollinateGoal();
        this.goToKnownFlowerGoal = new EmptyFindFlowerGoal();
    }

    @Override
    public boolean doHurtTarget(Entity p_27722_) {
        boolean flag = p_27722_.hurt(this.damageSources().sting(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, p_27722_);
            if (p_27722_ instanceof LivingEntity) {
                ((LivingEntity)p_27722_).setStingerCount(((LivingEntity)p_27722_).getStingerCount() + 1);
                int i = 0;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 18;
                }

                if (i > 0) {
                    ((LivingEntity)p_27722_).addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
                }
            }

            this.setHasStung(false);
            this.playSound(SoundEvents.BEE_STING, 1.0F, 1.0F);
        }

        return flag;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasNectar() && this.random.nextFloat() < 0.05F) {
            for(int i = 0; i < this.random.nextInt(2) + 1; ++i) {
                this.spawnFluidParticle(this.level(), this.getX() - (double)0.3F, this.getX() + (double)0.3F, this.getZ() - (double)0.3F, this.getZ() + (double)0.3F, this.getY(0.5D), ParticleTypes.FALLING_NECTAR);
            }
        }
    }

    @Override
    public void spawnFluidParticle(Level pLevel, double pStartX, double pEndX, double pStartZ, double pEndZ, double pPosY, ParticleOptions pParticleOption) {
        pLevel.addParticle(pParticleOption, Mth.lerp(pLevel.random.nextDouble(), pStartX, pEndX), pPosY, Mth.lerp(pLevel.random.nextDouble(), pStartZ, pEndZ), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            Level level = this.level();
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                this.isExposed = true;
            }

            if (this.isExposed)
            {
                this.vanishingTime --;
            }

            if (level instanceof ServerLevel serverLevel)
            {
                double d = 0.1D;
                float rng = getRandom().nextFloat();
                if (this.isExposed)
                {
                    if (rng <= d)
                    {
                        serverLevel.sendParticles(ParticleTypes.CLOUD,
                                this.getX(), this.getY() + 0.2, this.getZ(), 2, 0.3, 0.25, 0.3, 0.0);
                    }
                }

                if (rng <= d)
                {
                    serverLevel.sendParticles(ModParticleTypes.GHOST_FLAME.get(),
                            this.getX(), this.getY() + 0.5, this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
                }
            }
        }
        super.aiStep();
    }

    // Ectoplasm on right click; DISCARDED
    /*public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() == Items.AIR) {
            if (!this.level().isClientSide && this.hasNectar()) {
                this.collect(SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    public void collect(SoundSource pCategory) {
        this.level().playSound(null, this, SoundEvents.BEEHIVE_ENTER, pCategory, 1.0F, 1.0F);
        this.setHasNectar(false);
        //int i = 1 + this.random.nextInt(3);
        int i = 1;

        for(int j = 0; j < i; ++j) {
            ItemEntity itementity = this.spawnAtLocation(ModItems.ECTOPLASM.get(), 0);
            if (itementity != null) {
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }
    }*/

    void pathfindRandomlyTowards(BlockPos p_27881_) {
        Vec3 vec3 = Vec3.atBottomCenterOf(p_27881_);
        int i = 0;
        BlockPos blockpos = this.blockPosition();
        int j = (int)vec3.y - blockpos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int i1 = blockpos.distManhattan(p_27881_);
        if (i1 < 15) {
            k = i1 / 2;
            l = i1 / 2;
        }

        Vec3 vec31 = AirRandomPos.getPosTowards(this, k, l, i, vec3, (float)Math.PI / 10F);
        if (vec31 != null) {
            this.navigation.setMaxVisitedNodesMultiplier(0.5F);
            this.navigation.moveTo(vec31.x, vec31.y, vec31.z, 1.0D);
        }
    }

    boolean isHiveValid() {
        if (!this.hasHive()) {
            return false;
        } else if (this.isTooFarAway(this.hivePos)) {
            return false;
        } else {
            BlockEntity blockentity = this.level().getBlockEntity(this.hivePos);
            return blockentity instanceof BeehiveBlockEntityAbstract;
        }
    }

    @Override
    public boolean wantsToEnterHive() {
        if (this.stayOutOfHiveCountdown <= 0 && !this.beePollinateGoal.isPollinating() && !this.hasStung() && this.getTarget() == null) {
            boolean flag = this.isTiredOfLookingForNectar() || this.level().isRaining() || this.level().isDay() || this.hasNectar();
            return flag && !this.isHiveNearFire();
        } else {
            return false;
        }
    }

    boolean isTooFarAway(BlockPos p_27890_) {
        return !this.closerThan(p_27890_, 32);
    }

    public int getTimeInHive(boolean hasNectar) {
        return 4800;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.equals(this.level().damageSources().inWall()) || source.equals(this.level().damageSources().sweetBerryBush()) || super.isInvulnerableTo(source);
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level worldIn) {
        PathNavigation navigator = super.createNavigation(worldIn);

        if (navigator instanceof FlyingPathNavigation) {
            navigator.setCanFloat(false);
            ((FlyingPathNavigation) navigator).setCanPassDoors(false);
        }
        return navigator;
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();

        if (!this.isBaby()) {
            BlockPos pos = blockPosition();
            if (level().isEmptyBlock(pos)) {
                this.setPos(pos.getX(), pos.getY(), pos.getZ());
            } else if (level().isEmptyBlock(pos.below())) {
                pos = pos.below();
                this.setPos(pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }

    @Nullable
    @Override
    public Boo getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.BOO.get().create(serverLevel);
    }

    public String getFlowerType() {
        return "block";
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.25F : sizeIn.height * 0.5F;
    }

    public boolean isFlowerBlock(BlockState flowerBlock) {
        return flowerBlock.is(BlockTags.FLOWERS);
    }

    public class EnterHiveGoal extends BeeEnterHiveGoal
    {
        public EnterHiveGoal() {
            super();
        }

        public boolean canBeeUse() {
            if (Boo.this.hivePos != null && Boo.this.wantsToEnterHive() && Boo.this.hivePos.closerToCenterThan(Boo.this.position(), 2.0D)) {
                BlockEntity blockEntity = Boo.this.level().getBlockEntity(Boo.this.hivePos);
                if (blockEntity instanceof BeehiveBlockEntityAbstract boohiveBlockEntity) {
                    if (!boohiveBlockEntity.isFull()) {
                        return true;
                    }

                    Boo.this.hivePos = null;
                }
            }
            return false;
        }
    }

    public class PollinateGoal extends BeePollinateGoal
    {
        public Predicate<BlockPos> flowerPredicate = (blockPos) -> {
            BlockState blockState = Boo.this.level().getBlockState(blockPos);
            boolean isInterested = false;
            try {
                if (!getFlowerType().equals("entity_type")) {
                    isInterested = Boo.this.isFlowerBlock(blockState);
                    if (isInterested && blockState.is(BlockTags.TALL_FLOWERS)) {
                        if (blockState.getBlock() == Blocks.SUNFLOWER) {
                            isInterested = blockState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER;
                        }
                    }
                }
            } catch (Exception e) {
            }

            return isInterested;
        };

        public PollinateGoal() {
            super();
        }

        @Override
        public void tick() {
            if (Boo.this.hasSavedFlowerPos()) {
                super.tick();
            }

        }

    }

    public class BooGoToHiveGoal extends BeeGoToHiveGoal {
        int travellingTicks = Boo.this.level().random.nextInt(10);
        public final List<BlockPos> blacklistedTargets = Lists.newArrayList();
        @Nullable
        private Path lastPath;
        private int ticksStuck;

        public BooGoToHiveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canBeeUse() {
            return Boo.this.hivePos != null && !Boo.this.hasRestriction() && Boo.this.wantsToEnterHive() && !this.hasReachedTarget(Boo.this.hivePos) && Boo.this.level().getBlockState(Boo.this.hivePos).is(ModTags.Blocks.BEEHIVES);
        }

        public boolean canBeeContinueToUse() {
            return this.canBeeUse();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.travellingTicks = 0;
            this.ticksStuck = 0;
            super.start();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.travellingTicks = 0;
            this.ticksStuck = 0;
            Boo.this.navigation.stop();
            Boo.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (Boo.this.hivePos != null) {
                ++this.travellingTicks;
                if (this.travellingTicks > this.adjustedTickDelay(600)) {
                    this.dropAndBlacklistHive();
                } else if (!Boo.this.navigation.isInProgress()) {
                    if (!Boo.this.closerThan(Boo.this.hivePos, 16)) {
                        if (Boo.this.isTooFarAway(Boo.this.hivePos)) {
                            this.dropHive();
                        } else {
                            Boo.this.pathfindRandomlyTowards(Boo.this.hivePos);
                        }
                    } else {
                        boolean flag = this.pathfindDirectlyTowards(Boo.this.hivePos);
                        if (!flag) {
                            this.dropAndBlacklistHive();
                        } else if (this.lastPath != null && Boo.this.navigation.getPath().sameAs(this.lastPath)) {
                            ++this.ticksStuck;
                            if (this.ticksStuck > 60) {
                                this.dropHive();
                                this.ticksStuck = 0;
                            }
                        } else {
                            this.lastPath = Boo.this.navigation.getPath();
                        }

                    }
                }
            }
        }

        private boolean pathfindDirectlyTowards(BlockPos pPos) {
            Boo.this.navigation.setMaxVisitedNodesMultiplier(10.0F);
            Boo.this.navigation.moveTo(pPos.getX(), pPos.getY(), pPos.getZ(), 1.0D);
            return Boo.this.navigation.getPath() != null && Boo.this.navigation.getPath().canReach();
        }

        public boolean isTargetBlacklisted(BlockPos pPos) {
            return this.blacklistedTargets.contains(pPos);
        }

        protected void blacklistTarget(BlockPos pPos) {
            this.blacklistedTargets.add(pPos);

            while(this.blacklistedTargets.size() > 3) {
                this.blacklistedTargets.remove(0);
            }

        }

        public void clearBlacklist() {
            this.blacklistedTargets.clear();
        }

        private void dropAndBlacklistHive() {
            if (Boo.this.hivePos != null) {
                this.blacklistTarget(Boo.this.hivePos);
            }

            this.dropHive();
        }

        private void dropHive() {
            Boo.this.hivePos = null;
            Boo.this.remainingCooldownBeforeLocatingNewHive = 200;
        }

        private boolean hasReachedTarget(BlockPos pPos) {
            if (Boo.this.closerThan(pPos, 2)) {
                return true;
            } else {
                Path path = Boo.this.navigation.getPath();
                return path != null && path.getTarget().equals(pPos) && path.canReach() && path.isDone();
            }
        }
    }

    public class UpdateNestGoal extends BeeLocateHiveGoal
    {
        public UpdateNestGoal() {
            super();
        }

        @Override
        public void start() {
            Boo.this.remainingCooldownBeforeLocatingNewHive = 200;
            List<BlockPos> nearbyNests = this.getNearbyFreeNests();
            if (!nearbyNests.isEmpty()) {
                Iterator<BlockPos> iterator = nearbyNests.iterator();
                BlockPos blockPos;
                do {
                    if (!iterator.hasNext()) {
                        Boo.this.goToHiveGoal.clearBlacklist();
                        Boo.this.hivePos = nearbyNests.get(0);
                        return;
                    }

                    blockPos = iterator.next();
                } while (Boo.this.goToHiveGoal.isTargetBlacklisted(blockPos));

                Boo.this.hivePos = blockPos;
            }
        }

        private List<BlockPos> getNearbyFreeNests() {
            BlockPos pos = Boo.this.blockPosition();

            PoiManager poiManager = ((ServerLevel) Boo.this.level()).getPoiManager();

            Stream<PoiRecord> stream = poiManager.getInRange(Boo.this.beehiveInterests, pos, 30, PoiManager.Occupancy.ANY);

            return stream
                    .map(PoiRecord::getPos)
                    .filter(Boo.this::doesHiveHaveSpace)
                    .sorted(Comparator.comparingDouble((vec) -> vec.distSqr(pos)))
                    .collect(Collectors.toList());
        }
    }

    public class BeeAttackGoal extends MeleeAttackGoal
    {
        BeeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        public boolean canUse() {
            return super.canUse() && Boo.this.isAngry() && !Boo.this.hasStung();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && Boo.this.isAngry() && !Boo.this.hasStung();
        }
    }

    public class TemptGoal extends net.minecraft.world.entity.ai.goal.TemptGoal
    {
        public TemptGoal(PathfinderMob entity, double speed) {
            super(entity, speed, Ingredient.EMPTY, false);
            List<ItemStack> listOfStuff = Arrays.asList(Ingredient.of(ItemTags.FLOWERS).getItems());
//            listOfStuff.addAll(Arrays.asList(Boo.this.getBreedingItem().getItems())); TODO
            items = Ingredient.of(listOfStuff.stream());
        }
    }

    public class EmptyPollinateGoal extends PollinateGoal
    {
        @Override
        public boolean canBeeUse() {
            return false;
        }
    }

    public class EmptyFindFlowerGoal extends BeeGoToKnownFlowerGoal
    {
        @Override
        public boolean canBeeUse() {
            return false;
        }
    }

    public class BooWanderGoal extends Goal {
        private static final int WANDER_THRESHOLD = 22;

        public BooWanderGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return Boo.this.navigation.isDone() && Boo.this.random.nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
            return Boo.this.navigation.isInProgress();
        }

        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                Boo.this.navigation.moveTo(Boo.this.navigation.createPath(BlockPos.containing(vec3), 1), 1.0D);
            }

        }

        @Nullable
        private Vec3 findPos() {
            Vec3 vec3;
            if (Boo.this.isHiveValid() && !Boo.this.closerThan(Boo.this.hivePos, 22)) {
                Vec3 vec31 = Vec3.atCenterOf(Boo.this.hivePos);
                vec3 = vec31.subtract(Boo.this.position()).normalize();
            } else {
                vec3 = Boo.this.getViewVector(0.0F);
            }

            int i = 8;
            Vec3 vec32 = HoverRandomPos.getPos(Boo.this, 8, 7, vec3.x, vec3.z, ((float)Math.PI / 2F), 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(Boo.this, 8, 4, -2, vec3.x, vec3.z, (float)Math.PI / 2F);
        }
    }
}
