package ttv.migami.jeg.common.network;

import com.mrcrayfish.framework.api.network.LevelLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import ttv.migami.jeg.Config;
import ttv.migami.jeg.JustEnoughGuns;
import ttv.migami.jeg.blockentity.GunmetalWorkbenchBlockEntity;
import ttv.migami.jeg.blockentity.GunniteWorkbenchBlockEntity;
import ttv.migami.jeg.blockentity.ScrapWorkbenchBlockEntity;
import ttv.migami.jeg.common.*;
import ttv.migami.jeg.common.container.AttachmentContainer;
import ttv.migami.jeg.common.container.GunmetalWorkbenchContainer;
import ttv.migami.jeg.common.container.GunniteWorkbenchContainer;
import ttv.migami.jeg.common.container.ScrapWorkbenchContainer;
import ttv.migami.jeg.crafting.*;
import ttv.migami.jeg.entity.projectile.ProjectileEntity;
import ttv.migami.jeg.event.BurstFireEvent;
import ttv.migami.jeg.event.GunFireEvent;
import ttv.migami.jeg.init.ModEnchantments;
import ttv.migami.jeg.init.ModSyncedDataKeys;
import ttv.migami.jeg.interfaces.IProjectileFactory;
import ttv.migami.jeg.item.GunItem;
import ttv.migami.jeg.item.IColored;
import ttv.migami.jeg.item.attachment.IAttachment;
import ttv.migami.jeg.network.PacketHandler;
import ttv.migami.jeg.network.message.C2SMessagePreFireSound;
import ttv.migami.jeg.network.message.C2SMessageShoot;
import ttv.migami.jeg.network.message.S2CMessageBulletTrail;
import ttv.migami.jeg.network.message.S2CMessageGunSound;
import ttv.migami.jeg.util.GunEnchantmentHelper;
import ttv.migami.jeg.util.GunModifierHelper;

import java.util.List;
import java.util.function.Predicate;

/**
 * Author: MrCrayfish
 */
public class ServerPlayHandler
{
    private static final Predicate<LivingEntity> HOSTILE_ENTITIES = entity -> entity.getSoundSource() == SoundSource.HOSTILE && !(entity instanceof NeutralMob) && !Config.COMMON.aggroMobs.exemptEntities.get().contains(EntityType.getKey(entity.getType()).toString());

    /**
     * Fires the weapon the player is currently holding.
     * This is only intended for use on the logical server.
     *
     * @param player the player for who's weapon to fire
     */
    public static void handleShoot(C2SMessageShoot message, ServerPlayer player)
    {
        if(player.isSpectator())
            return;

        if(player.getUseItem().getItem() == Items.SHIELD)
            return;

        Level world = player.level();
        ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(heldItem.getItem() instanceof GunItem item && (Gun.hasAmmo(heldItem) || player.isCreative()))
        {
            Gun modifiedGun = item.getModifiedGun(heldItem);
            if(modifiedGun != null)
            {
                if(MinecraftForge.EVENT_BUS.post(new GunFireEvent.Pre(player, heldItem)))
                    return;

                /* Updates the yaw and pitch with the clients current yaw and pitch */
                player.setYRot(Mth.wrapDegrees(message.getRotationYaw()));
                player.setXRot(Mth.clamp(message.getRotationPitch(), -90F, 90F));

                // Bingo bango.

                ShootTracker tracker = ShootTracker.getShootTracker(player);
                if(tracker.hasCooldown(item) && tracker.getRemaining(item) > Config.SERVER.cooldownThreshold.get())
                {
                    JustEnoughGuns.LOGGER.warn(player.getName().getContents() + "(" + player.getUUID() + ") tried to fire before cooldown finished or server is lagging? Remaining milliseconds: " + tracker.getRemaining(item));
                    return;
                }
                tracker.putCooldown(heldItem, item, modifiedGun);

                if(ModSyncedDataKeys.RELOADING.getValue(player))
                {
                    ModSyncedDataKeys.RELOADING.setValue(player, false);
                }

                if(!modifiedGun.getGeneral().isAlwaysSpread() && modifiedGun.getGeneral().getSpread() > 0.0F)
                {
                    SpreadTracker.get(player).update(player, item);
                }

                if (modifiedGun.getGeneral().getFireMode() != FireMode.PULSE)
                {
                    int count = modifiedGun.getGeneral().getProjectileAmount();
                    Gun.Projectile projectileProps = modifiedGun.getProjectile();
                    ProjectileEntity[] spawnedProjectiles = new ProjectileEntity[count];
                    for(int i = 0; i < count; i++)
                    {
                        IProjectileFactory factory = ProjectileManager.getInstance().getFactory(projectileProps.getItem());
                        ProjectileEntity projectileEntity = factory.create(world, player, heldItem, item, modifiedGun);
                        projectileEntity.setWeapon(heldItem);
                        projectileEntity.setAdditionalDamage(Gun.getAdditionalDamage(heldItem));
                        world.addFreshEntity(projectileEntity);
                        spawnedProjectiles[i] = projectileEntity;
                        projectileEntity.tick();
                    }
                    if(!projectileProps.isVisible())
                    {
                        double spawnX = player.getX();
                        double spawnY = player.getY() + 1.0;
                        double spawnZ = player.getZ();
                        double radius = Config.COMMON.network.projectileTrackingRange.get();
                        ParticleOptions data = GunEnchantmentHelper.getParticle(heldItem);
                        S2CMessageBulletTrail messageBulletTrail = new S2CMessageBulletTrail(spawnedProjectiles, projectileProps, player.getId(), data);
                        PacketHandler.getPlayChannel().sendToNearbyPlayers(() -> LevelLocation.create(player.level(), spawnX, spawnY, spawnZ, radius), messageBulletTrail);
                    }
                }

                MinecraftForge.EVENT_BUS.post(new GunFireEvent.Post(player, heldItem));

                if(Config.COMMON.aggroMobs.enabled.get())
                {
                    double radius = GunModifierHelper.getModifiedFireSoundRadius(heldItem, Config.COMMON.aggroMobs.unsilencedRange.get());
                    double x = player.getX();
                    double y = player.getY() + 0.5;
                    double z = player.getZ();
                    AABB box = new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
                    radius *= radius;
                    double dx, dy, dz;
                    for(LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, box, HOSTILE_ENTITIES))
                    {
                        dx = x - entity.getX();
                        dy = y - entity.getY();
                        dz = z - entity.getZ();
                        if(dx * dx + dy * dy + dz * dz <= radius)
                        {
                            entity.setLastHurtByMob(Config.COMMON.aggroMobs.angerHostileMobs.get() ? player : entity);
                        }
                    }
                }

                ResourceLocation fireSound = getFireSound(heldItem, modifiedGun);
                if(fireSound != null)
                {
                    double posX = player.getX();
                    double posY = player.getY() + player.getEyeHeight();
                    double posZ = player.getZ();
                    float volume = GunModifierHelper.getFireSoundVolume(heldItem);
                    float pitch = 0.9F + world.random.nextFloat() * 0.2F;
                    double radius = GunModifierHelper.getModifiedFireSoundRadius(heldItem, Config.SERVER.gunShotMaxDistance.get());
                    boolean muzzle = modifiedGun.getDisplay().getFlash() != null;
                    S2CMessageGunSound messageSound = new S2CMessageGunSound(fireSound, SoundSource.PLAYERS, (float) posX, (float) posY, (float) posZ, volume, pitch, player.getId(), muzzle, false);
                    PacketHandler.getPlayChannel().sendToNearbyPlayers(() -> LevelLocation.create(player.level(), posX, posY, posZ, radius), messageSound);
                }

                if(!player.isCreative())
                {
                    CompoundTag tag = heldItem.getOrCreateTag();
                    if(!tag.getBoolean("IgnoreAmmo"))
                    {
                        int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RECLAIMED.get(), heldItem);
                        if(level == 0 || player.level().random.nextInt(4 - Mth.clamp(level, 1, 2)) != 0)
                        {
                            tag.putInt("AmmoCount", Math.max(0, tag.getInt("AmmoCount") - 1));
                        }
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(item));
            }
        }
        else
        {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.8F);
        }
    }

    public static void handlePreFireSound(C2SMessagePreFireSound message, ServerPlayer player) {
        Level world = player.level();
        ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(heldItem.getItem() instanceof GunItem item && (Gun.hasAmmo(heldItem) || player.isCreative()))
        {
            Gun modifiedGun = item.getModifiedGun(heldItem);
            ResourceLocation fireSound = getPreFireSound(heldItem, modifiedGun);
            if(fireSound != null)
            {
                double posX = player.getX();
                double posY = player.getY() + player.getEyeHeight();
                double posZ = player.getZ();
                float volume = GunModifierHelper.getFireSoundVolume(heldItem);
                float pitch = 0.9F + world.random.nextFloat() * 0.2F;
                double radius = GunModifierHelper.getModifiedFireSoundRadius(heldItem, Config.SERVER.gunShotMaxDistance.get());
                S2CMessageGunSound messageSound = new S2CMessageGunSound(fireSound, SoundSource.PLAYERS, (float) posX, (float) posY, (float) posZ, volume, pitch, player.getId(), false, false);
                PacketHandler.getPlayChannel().sendToNearbyPlayers(() -> LevelLocation.create(player.level(), posX, posY, posZ, radius), messageSound);
            }
        }
    }

    public static void handleBurst(ServerPlayer player) {

        ItemStack heldItem = player.getMainHandItem();
        if(heldItem.getItem() instanceof GunItem gunItem)
        {

            Gun gun = gunItem.getModifiedGun(heldItem);
            if (gun.getGeneral().getFireMode() == FireMode.BURST)
            {
                BurstFireEvent.resetBurst(heldItem);
            }

        }
    }

    private static ResourceLocation getFireSound(ItemStack stack, Gun modifiedGun)
    {
        ResourceLocation fireSound = null;
        if(GunModifierHelper.isSilencedFire(stack))
        {
            fireSound = modifiedGun.getSounds().getSilencedFire();
        }
        else if(stack.isEnchanted())
        {
            fireSound = modifiedGun.getSounds().getEnchantedFire();
        }
        if(fireSound != null)
        {
            return fireSound;
        }
        return modifiedGun.getSounds().getFire();
    }

    private static ResourceLocation getPreFireSound(ItemStack stack, Gun modifiedGun)
    {
        return modifiedGun.getSounds().getPreFire();
    }

    /**
     * Crafts the specified item at the workstation the player is currently using.
     * This is only intended for use on the logical server.
     *
     * @param player the player who is crafting
     * @param id     the id of an item which is registered as a valid workstation recipe
     * @param pos    the block position of the workstation the player is using
     */
    public static void handleCraft(ServerPlayer player, ResourceLocation id, BlockPos pos) {
        Level world = player.level();

        if (player.containerMenu instanceof ScrapWorkbenchContainer workbench) {
            if (workbench.getPos().equals(pos)) {
                ScrapWorkbenchRecipe recipe = ScrapWorkbenchRecipes.getRecipeById(world, id);
                if (recipe == null || !recipe.hasMaterials(player))
                    return;

                recipe.consumeMaterials(player);

                ScrapWorkbenchBlockEntity scrapWorkbenchBlockEntity = workbench.getWorkbench();

                /* Gets the color based on the dye */
                ItemStack stack = recipe.getItem();
                ItemStack dyeStack = scrapWorkbenchBlockEntity.getInventory().get(0);
                if (dyeStack.getItem() instanceof DyeItem) {
                    DyeItem dyeItem = (DyeItem) dyeStack.getItem();
                    int color = dyeItem.getDyeColor().getTextColor();

                    if (IColored.isDyeable(stack)) {
                        IColored colored = (IColored) stack.getItem();
                        colored.setColor(stack, color);
                        scrapWorkbenchBlockEntity.getInventory().set(0, ItemStack.EMPTY);
                    }
                }

                Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 1.125, pos.getZ() + 0.5, stack);
            }
        }

        if (player.containerMenu instanceof GunmetalWorkbenchContainer workbench) {
            if (workbench.getPos().equals(pos)) {
                GunmetalWorkbenchRecipe recipe = GunmetalWorkbenchRecipes.getRecipeById(world, id);
                if (recipe == null || !recipe.hasMaterials(player))
                    return;

                recipe.consumeMaterials(player);

                GunmetalWorkbenchBlockEntity gunmetalWorkbenchBlockEntity = workbench.getWorkbench();

                /* Gets the color based on the dye */
                ItemStack stack = recipe.getItem();
                ItemStack dyeStack = gunmetalWorkbenchBlockEntity.getInventory().get(0);
                if (dyeStack.getItem() instanceof DyeItem) {
                    DyeItem dyeItem = (DyeItem) dyeStack.getItem();
                    int color = dyeItem.getDyeColor().getTextColor();

                    if (IColored.isDyeable(stack)) {
                        IColored colored = (IColored) stack.getItem();
                        colored.setColor(stack, color);
                        gunmetalWorkbenchBlockEntity.getInventory().set(0, ItemStack.EMPTY);
                    }
                }

                Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 1.125, pos.getZ() + 0.5, stack);
            }
        }

        if (player.containerMenu instanceof GunniteWorkbenchContainer workbench) {
            if (workbench.getPos().equals(pos)) {
                GunniteWorkbenchRecipe recipe = GunniteWorkbenchRecipes.getRecipeById(world, id);
                if (recipe == null || !recipe.hasMaterials(player))
                    return;

                recipe.consumeMaterials(player);

                GunniteWorkbenchBlockEntity gunniteWorkbenchBlockEntity = workbench.getWorkbench();

                /* Gets the color based on the dye */
                ItemStack stack = recipe.getItem();
                ItemStack dyeStack = gunniteWorkbenchBlockEntity.getInventory().get(0);
                if (dyeStack.getItem() instanceof DyeItem) {
                    DyeItem dyeItem = (DyeItem) dyeStack.getItem();
                    int color = dyeItem.getDyeColor().getTextColor();

                    if (IColored.isDyeable(stack)) {
                        IColored colored = (IColored) stack.getItem();
                        colored.setColor(stack, color);
                        gunniteWorkbenchBlockEntity.getInventory().set(0, ItemStack.EMPTY);
                    }
                }

                Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 1.125, pos.getZ() + 0.5, stack);
            }
        }
    }

    /*
    public static void handleRecycle(ServerPlayer player, ResourceLocation id, BlockPos pos)
    {
        Level world = player.level;

        if(player.containerMenu instanceof RecyclerContainer recycler)
        {
            if(recycler.getPos().equals(pos))
            {
                RecyclerRecipe recipe = RecyclerRecipes.getRecipeById(world, id);
                if(recipe == null)
                    return;

                ItemStack stack = recipe.getItem();
                Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 1.125, pos.getZ() + 0.5, stack);
            }
        }
    }
    */

    /**
     * @param player
     */
    public static void stopSprinting(Player player) {
        player.setSprinting(false);

        if(player.level().isClientSide) {
            Minecraft.getInstance().options.keySprint.setDown(false);
        }
    }


    /**
     * @param player
     */
    public static void handleMelee(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();
        ItemCooldowns tracker = player.getCooldowns();
        double offsetX;
        double offsetY;
        double offsetZ;

        if (stack.getItem() instanceof GunItem gunItem && !tracker.isOnCooldown(stack.getItem())) {

            Level level = player.level();

            ItemStack bayonet = Gun.getAttachment(IAttachment.Type.BARREL, player.getMainHandItem());
            int maxDamage = bayonet.getMaxDamage();
            int currentDamage = bayonet.getDamageValue();

            double attackRange = 2.0;
            double sweepAngle = Math.toRadians(100);

            Vec3 playerPos = player.position();
            Vec3 lookVec = player.getLookAngle();

            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(attackRange));

            level.playSound(null, player.getOnPos(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2F, 1F);
            tracker.addCooldown(stack.getItem(), 15);

            for (LivingEntity entity : entities) {
                Vec3 entityPos = entity.position().subtract(playerPos);
                double angle = Math.acos(entityPos.normalize().dot(lookVec.normalize()));

                if (angle < sweepAngle / 2 && entity != player) {

                    if (!player.level().isClientSide) {

                        Vec3 direction = entity.position().subtract(player.position()).normalize();

                        entity.push(direction.x * GunModifierHelper.getSwordKnockBack(player), 0.5, direction.z * GunModifierHelper.getSwordKnockBack(player));

                        if (currentDamage <= maxDamage / 1.5) {
                            entity.hurt(player.damageSources().playerAttack(player), GunModifierHelper.getSwordDamage(player) / 1.5F);

                            if (GunModifierHelper.getSwordFireAspect(player) > 0) {
                                entity.setSecondsOnFire(2 * GunModifierHelper.getSwordFireAspect(player));
                            }
                        } else {
                            level.playSound(player, player.getOnPos(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 3.0F, 1.0F);
                        }

                        if (!player.getAbilities().instabuild && Config.COMMON.gameplay.gunDurability.get() && currentDamage <= maxDamage / 1.5) {
                            bayonet.hurtAndBreak(8, player, null);
                        }
                    }
                }
            }

            offsetX = lookVec.x * 1.8;
            offsetY = lookVec.y * 1.8 + player.getEyeHeight();
            offsetZ = lookVec.z * 1.8;
            playerPos = player.getPosition(1F).add(offsetX, offsetY, offsetZ);

            if (!level.isClientSide) {
                ((ServerLevel) level).sendParticles(ParticleTypes.SWEEP_ATTACK, playerPos.x, playerPos.y, playerPos.z, 1, 0, 0, 0, 0.0);
            }

        }
    }

    /**
     * @param player
     */
    public static void handleUnload(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof GunItem gunItem) {
            Gun gun = gunItem.getModifiedGun(stack);
            CompoundTag tag = stack.getTag();
            if (gun.getReloads().getReloadType() != ReloadType.SINGLE_ITEM) {
                if (tag != null && tag.contains("AmmoCount", Tag.TAG_INT)) {
                    int count = tag.getInt("AmmoCount");
                    tag.putInt("AmmoCount", 0);

                    ResourceLocation id = gun.getProjectile().getItem();

                    Item item = ForgeRegistries.ITEMS.getValue(id);
                    if (item == null) {
                        return;
                    }

                    int maxStackSize = item.getMaxStackSize();
                    int stacks = count / maxStackSize;
                    for (int i = 0; i < stacks; i++) {
                        spawnAmmo(player, new ItemStack(item, maxStackSize));
                    }

                    int remaining = count % maxStackSize;
                    if (remaining > 0) {
                        spawnAmmo(player, new ItemStack(item, remaining));
                    }
                }
            }
        }
    }

    /**
     * @param player
     */
    public static void handleExtraAmmo(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof GunItem gunItem) {
            Gun gun = gunItem.getModifiedGun(stack);
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("AmmoCount", Tag.TAG_INT)) {
                int currentAmmo = tag.getInt("AmmoCount");

                if (currentAmmo > GunModifierHelper.getModifiedAmmoCapacity(stack, gun)) {

                    ResourceLocation id = gun.getProjectile().getItem();
                    Item item = ForgeRegistries.ITEMS.getValue(id);
                    if (item == null) {
                        return;
                    }
                    int residue = currentAmmo - gun.getReloads().getMaxAmmo();

                    tag.putInt("AmmoCount", currentAmmo - residue);

                    JustEnoughGuns.LOGGER.atInfo().log(String.valueOf(residue));
                    spawnAmmo(player, new ItemStack(item, residue));

                }
            }
        }
    }

    /**
     * @param player
     * @param stack
     */
    private static void spawnAmmo(ServerPlayer player, ItemStack stack) {
        player.getInventory().add(stack);
        if (stack.getCount() > 0) {
            player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), stack.copy()));
        }
    }

    /**
     * @param player
     */
    public static void handleAttachments(ServerPlayer player) {
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.getItem() instanceof GunItem) {
            NetworkHooks.openScreen(player, new SimpleMenuProvider((windowId, playerInventory, player1) -> new AttachmentContainer(windowId, playerInventory, heldItem), Component.translatable("container.jeg.attachments")));
        }
    }
}
