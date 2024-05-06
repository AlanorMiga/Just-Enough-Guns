package ttv.migami.jeg.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.common.FireMode;
import ttv.migami.jeg.common.Gun;
import ttv.migami.jeg.common.ModTags;
import ttv.migami.jeg.init.ModEffects;
import ttv.migami.jeg.init.ModItems;
import ttv.migami.jeg.init.ModParticleTypes;
import ttv.migami.jeg.init.ModSounds;
import ttv.migami.jeg.item.GunItem;
import ttv.migami.jeg.item.TyphooneeItem;
import ttv.migami.jeg.item.UnderwaterFirearmItem;
import ttv.migami.jeg.item.attachment.IAttachment;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GunEventBus
{
    @SubscribeEvent
    public static void preShoot(GunFireEvent.Pre event)
    {

        Player player = event.getEntity();
        Level level = event.getEntity().level;
        ItemStack heldItem = player.getMainHandItem();
        CompoundTag tag = heldItem.getTag();

        if(heldItem.getItem() instanceof GunItem gunItem)
        {
            Gun gun = gunItem.getModifiedGun(heldItem);
            if (!(heldItem.getItem() instanceof UnderwaterFirearmItem) && player.isUnderWater())
            {
                event.setCanceled(true);
            }

            ItemCooldowns tracker = player.getCooldowns();
            if(tracker.isOnCooldown(heldItem.getItem()) && gun.getGeneral().getFireMode() == FireMode.PULSE)
            {
                event.setCanceled(true);
            }

            if (heldItem.isDamageableItem() && tag != null) {
                if (heldItem.getDamageValue() == (heldItem.getMaxDamage() - 1)) {
                    level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                    event.getEntity().getCooldowns().addCooldown(event.getStack().getItem(), gun.getGeneral().getRate());
                    event.setCanceled(true);
                }
                //This is the Jam function
                int maxDamage = heldItem.getMaxDamage();
                int currentDamage = heldItem.getDamageValue();
                if (currentDamage >= maxDamage / 1.5) {
                    if (Math.random() >= 0.975) {
                        event.getEntity().playSound(ModSounds.ITEM_PISTOL_COCK.get(), 1.0F, 1.0F);
                        int coolDown = gun.getGeneral().getRate() * 10;
                        if (coolDown > 60) {
                            coolDown = 60;
                        }
                        event.getEntity().getCooldowns().addCooldown(event.getStack().getItem(), (coolDown));
                        event.setCanceled(true);
                    }
                } else if (tag.getInt("AmmoCount") >= 1) {
                    broken(heldItem, level, player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void postShoot(GunFireEvent.Post event)
    {
        Player player = event.getEntity();
        Level level = event.getEntity().level;
        ItemStack heldItem = player.getMainHandItem();
        CompoundTag tag = heldItem.getTag();
        if(heldItem.getItem() instanceof GunItem gunItem)
        {
            Gun gun = gunItem.getModifiedGun(heldItem);
            if (gun.getProjectile().ejectsCasing() && tag != null)
            {
                if (tag.getInt("AmmoCount") >= 1) {
                    //event.getEntity().level.playSound(player, player.blockPosition(), SoundInit.GARAND_PING.get(), SoundSource.MASTER, 3.0F, 1.0F);
                    ejectCasing(level, player);
                }
            }

            if (heldItem.isDamageableItem() && tag != null) {
                if (tag.getInt("AmmoCount") >= 1) {
                    damageGun(heldItem, level, player);
                    damageAttachments(heldItem, level, player);
                }
                if (heldItem.getDamageValue() >= (heldItem.getMaxDamage() / 1.5)) {
                    level.playSound(player, player.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 1.75F);
                }
            }

            float damage = gun.getProjectile().getDamage();
            if(gunItem instanceof UnderwaterFirearmItem && tag != null) {
                if (gunItem instanceof TyphooneeItem) {
                    typhooneeBlast(level, player, damage);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level;

        ItemCooldowns tracker = player.getCooldowns();
        ItemStack heldItem = player.getMainHandItem();
        CompoundTag tag = heldItem.getTag();
        Minecraft mc = Minecraft.getInstance();
        /*if(heldItem.getItem() instanceof GunItem gunItem) {
            if(gunItem instanceof AtlaneanSpearItem && tag != null) {
                if (player.isUnderWater() && !mc.options.keyAttack.isDown() && !tracker.isOnCooldown(gunItem))
                {
                    if (tag.getInt("AmmoCount") < gunItem.getGun().getReloads().getMaxAmmo())
                    {
                        tag.putInt("AmmoCount", Math.max(0, tag.getInt("AmmoCount") + 1));
                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.PLAYERS, 3.0F, 1.0F);
                    }
                }
            }
        }*/
    }

    public static void broken(ItemStack stack, Level level, Player player) {
        int maxDamage = stack.getMaxDamage();
        int currentDamage = stack.getDamageValue();
        if (currentDamage >= (maxDamage - 2)) {
            level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    public static void damageGun(ItemStack stack, Level level, Player player) {
        if (!player.getAbilities().instabuild) {
            if (stack.isDamageableItem()) {
                int maxDamage = stack.getMaxDamage();
                int currentDamage = stack.getDamageValue();
                if (currentDamage >= (maxDamage - 1)) {
                    if (currentDamage >= (maxDamage - 2)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        //stack.shrink(1);
                    }
                } else {
                    stack.hurtAndBreak(1, player, null);
                }
            }
        }
    }

    public static void damageAttachments(ItemStack stack, Level level, Player player) {
        if (!player.getAbilities().instabuild) {
            if (stack.getItem() instanceof GunItem) {

                //Scope
                ItemStack scopeStack = Gun.getAttachment(IAttachment.Type.SCOPE, stack);
                if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.SCOPE) && scopeStack.isDamageableItem()) {
                    int maxDamage = scopeStack.getMaxDamage();
                    int currentDamage = scopeStack.getDamageValue();
                    if (currentDamage == (maxDamage - 1)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        Gun.removeAttachment(stack, "Scope");
                    } else {
                        scopeStack.hurtAndBreak(1, player, null);
                    }
                }

                //Barrel
                ItemStack barrelStack = Gun.getAttachment(IAttachment.Type.BARREL, stack);
                if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.BARREL) && barrelStack.isDamageableItem()) {
                    int maxDamage = barrelStack.getMaxDamage();
                    int currentDamage = barrelStack.getDamageValue();
                    if (currentDamage == (maxDamage - 1)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        Gun.removeAttachment(stack, "Barrel");
                    } else {
                        barrelStack.hurtAndBreak(1, player, null);
                    }
                }

                //Stock
                ItemStack stockStack = Gun.getAttachment(IAttachment.Type.STOCK, stack);
                if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.STOCK) && stockStack.isDamageableItem()) {
                    int maxDamage = stockStack.getMaxDamage();
                    int currentDamage = stockStack.getDamageValue();
                    if (currentDamage == (maxDamage - 1)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        Gun.removeAttachment(stack, "Stock");
                    } else {
                        stockStack.hurtAndBreak(1, player, null);
                    }
                }

                //Under Barrel
                ItemStack underBarrelStack = Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack);
                if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.UNDER_BARREL) && underBarrelStack.isDamageableItem()) {
                    int maxDamage = underBarrelStack.getMaxDamage();
                    int currentDamage = underBarrelStack.getDamageValue();
                    if (currentDamage == (maxDamage - 1)) {
                        level.playSound(player, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        Gun.removeAttachment(stack, "Under_Barrel");
                    } else {
                        underBarrelStack.hurtAndBreak(1, player, null);
                    }
                }
            }
        }
    }

    public static void typhooneeBlast(Level level, Player player, float damage) {
        if(!level.isClientSide()) {

            HitResult result = player.pick(64, 0, false);
            Vec3 userPos = player.getEyePosition();
            Vec3 targetPos = result.getLocation();
            Vec3 distanceTo = targetPos.subtract(userPos);
            Vec3 normal = distanceTo.normalize();

            for(int i = 3; i < Mth.floor(distanceTo.length()); ++i) {
                Vec3 vec33 = userPos.add(normal.scale((double)i));
                if (!player.isUnderWater()) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.SPLASH, vec33.x, vec33.y, vec33.z, 3, 0.3D, 0.3D, 0.3D, 1.0D);
                    ((ServerLevel) level).sendParticles(ParticleTypes.FALLING_WATER, vec33.x, vec33.y, vec33.z, 1, 0.3D, 0.3D, 0.3D, 0.0D);
                    ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, vec33.x, vec33.y, vec33.z, 1, 0.4D, 0.4D, 0.4D, 0.4D);
                }
                else {
                    ((ServerLevel) level).sendParticles(ParticleTypes.BUBBLE, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    ((ServerLevel) level).sendParticles(ParticleTypes.BUBBLE, vec33.x, vec33.y, vec33.z, 3, 0.4D, 0.4D, 0.4D, 0.4D);
                }
                ((ServerLevel) level).sendParticles(ModParticleTypes.TYPHOONEE_BEAM.get(), vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }

            // Extinguish fire in a 5 diameter (2 blocks radius)
            int radius = 2;
            BlockPos blockPos = BlockPos.containing(result.getLocation());
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos blockPos2 = blockPos.offset(x, y, z);
                        BlockState blockState = level.getBlockState(blockPos2);
                        if (blockState.getBlock() instanceof FireBlock) {
                            level.setBlockAndUpdate(blockPos2, Blocks.AIR.defaultBlockState());
                            level.playSound(null, BlockPos.containing(result.getLocation()), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.2F, 1F);
                        }
                    }
                }
            }

            EntityHitResult e = ProjectileUtil.getEntityHitResult(level, player, userPos, targetPos, new AABB(userPos, targetPos), TyphooneeItem::canDamage);


            if(e != null && e.getEntity() instanceof LivingEntity entity) {

                float advantageMultiplier = 1F;
                if (entity.getType().is(ModTags.Entities.FIRE))
                {
                    advantageMultiplier = 2.0F;
                }

                if (entity.isOnFire()) {
                    entity.extinguishFire();
                    ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, entity.getX(), entity.getY() + 1, entity.getZ(), 6, 0.3D, 0.3D, 0.3D, 0.0D);
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.FALLING_WATER, entity.getX(), entity.getY() + 1, entity.getZ(), 6, 0.3D, 0.3D, 0.3D, 0.0D);

                entity.hurt(player.damageSources().sonicBoom(player), damage * advantageMultiplier);
                double d1 = 0.5D * (1.0D - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double d0 = 2.5D * (1.0D - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                entity.push(normal.x() * d0, normal.y() * d1, normal.z() * d0);

                entity.addEffect(new MobEffectInstance(ModEffects.DEAFENED.get(), 100, 0, false, false));
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50));
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));

            }
        }
    }

    public static void ejectCasing(Level level, LivingEntity livingEntity)
    {
        Player playerEntity = (Player) livingEntity;
        ItemStack heldItem = playerEntity.getMainHandItem();
        Gun gun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);

        Vec3 lookVec = playerEntity.getLookAngle(); //Get the player's look vector
        Vec3 rightVec = new Vec3(-lookVec.z, 0, lookVec.x).normalize();
        Vec3 forwardVec = new Vec3(lookVec.x, 0, lookVec.z).normalize();

        double offsetX = rightVec.x * 0.5 + forwardVec.x * 0.5; //Move the particle 0.5 blocks to the right and 0.5 blocks forward
        double offsetY = playerEntity.getEyeHeight() - 0.4; //Move the particle slightly below the player's head
        double offsetZ = rightVec.z * 0.5 + forwardVec.z * 0.5; //Move the particle 0.5 blocks to the right and 0.5 blocks forward

        Vec3 particlePos = playerEntity.getPosition(1).add(offsetX, offsetY, offsetZ); //Add the offsets to the player's position

        ResourceLocation pistolAmmoLocation = ModItems.PISTOL_AMMO.getId();
        ResourceLocation rifleAmmoLocation = ModItems.RIFLE_AMMO.getId();
        ResourceLocation shotgunShellLocation = ModItems.SHOTGUN_SHELL.getId();
        ResourceLocation spectreAmmoLocation = ModItems.SPECTRE_AMMO.getId();
        ResourceLocation projectileLocation = gun.getProjectile().getItem();

        SimpleParticleType casingType = ModParticleTypes.CASING_PARTICLE.get();

        if (projectileLocation != null) {
            if (projectileLocation.equals(pistolAmmoLocation) || projectileLocation.equals(rifleAmmoLocation)) {
                casingType = ModParticleTypes.CASING_PARTICLE.get();
            } else if (projectileLocation.equals(shotgunShellLocation)) {
                casingType = ModParticleTypes.SHELL_PARTICLE.get();
            }
            else if (projectileLocation.equals(spectreAmmoLocation)) {
                casingType = ModParticleTypes.SPECTRE_CASING_PARTICLE.get();
            }
        }

        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(casingType,
                    particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);
        }
    }

}