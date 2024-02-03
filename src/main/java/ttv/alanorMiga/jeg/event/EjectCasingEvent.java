package ttv.alanorMiga.jeg.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.event.GunFireEvent.Post;
import ttv.alanorMiga.jeg.init.ModParticleTypes;
import ttv.alanorMiga.jeg.item.MakeshiftRifleItem;
import ttv.alanorMiga.jeg.item.MakeshiftShotgunItem;
import ttv.alanorMiga.jeg.item.RifleItem;
import ttv.alanorMiga.jeg.item.ShotgunItem;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EjectCasingEvent
{

    @SubscribeEvent
    public static void postShoot(Post event)
    {
        Player player = event.getEntity();
        Level level = event.getEntity().level();
        ItemStack heldItem = player.getMainHandItem();
        CompoundTag tag = heldItem.getTag();

        if ((heldItem.getItem() instanceof RifleItem || heldItem.getItem() instanceof MakeshiftRifleItem) && tag != null)
        {
            if (tag.getInt("AmmoCount") >= 1){

                //event.getEntity().level.playSound(player, player.blockPosition(), SoundInit.GARAND_PING.get(), SoundSource.MASTER, 3.0F, 1.0F);
                ejectCasing(level, player);
            }
        }

        if ((heldItem.getItem() instanceof ShotgunItem || heldItem.getItem() instanceof MakeshiftShotgunItem) && tag != null)
        {
            if (tag.getInt("AmmoCount") >= 1){

                //event.getEntity().level.playSound(player, player.blockPosition(), SoundInit.GARAND_PING.get(), SoundSource.MASTER, 3.0F, 1.0F);
                ejectShell(level, player);
            }
        }
    }

    public static void ejectCasing(Level level, LivingEntity livingEntity)
    {
        Player playerEntity = (Player) livingEntity;
        double x = playerEntity.getX();
        double y = playerEntity.getY();
        double z = playerEntity.getZ();

        Vec3 lookVec = playerEntity.getLookAngle(); //Get the player's look vector
        Vec3 rightVec = new Vec3(-lookVec.z, 0, lookVec.x).normalize();
        Vec3 forwardVec = new Vec3(lookVec.x, 0, lookVec.z).normalize();

        double offsetX = rightVec.x * 0.5 + forwardVec.x * 0.5; //Move the particle 0.5 blocks to the right and 0.5 blocks forward
        double offsetY = playerEntity.getEyeHeight() - 0.4; //Move the particle slightly below the player's head
        double offsetZ = rightVec.z * 0.5 + forwardVec.z * 0.5; //Move the particle 0.5 blocks to the right and 0.5 blocks forward

        Vec3 particlePos = playerEntity.getPosition(1).add(offsetX, offsetY, offsetZ); //Add the offsets to the player's position

        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(ModParticleTypes.CASING_PARTICLE.get(),
                    particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);
        }
    }

    public static void ejectShell(Level level, LivingEntity livingEntity)
    {
        Player playerEntity = (Player) livingEntity;
        double x = playerEntity.getX();
        double y = playerEntity.getY();
        double z = playerEntity.getZ();

        Vec3 lookVec = playerEntity.getLookAngle(); //Get the player's look vector
        Vec3 rightVec = new Vec3(-lookVec.z, 0, lookVec.x).normalize();
        Vec3 forwardVec = new Vec3(lookVec.x, 0, lookVec.z).normalize();

        double offsetX = rightVec.x * 0.5 + forwardVec.x * 0.5; //Move the particle 0.5 blocks to the right and 0.5 blocks forward
        double offsetY = playerEntity.getEyeHeight() - 0.4; //Move the particle slightly below the player's head
        double offsetZ = rightVec.z * 0.5 + forwardVec.z * 0.5; //Move the particle 0.5 blocks to the right and 0.5 blocks forward

        Vec3 particlePos = playerEntity.getPosition(1).add(offsetX, offsetY, offsetZ); //Add the offsets to the player's position

        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(ModParticleTypes.SHELL_PARTICLE.get(),
                    particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);
        }
    }
}
