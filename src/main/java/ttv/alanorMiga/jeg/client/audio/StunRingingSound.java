package ttv.alanorMiga.jeg.client.audio;

import ttv.alanorMiga.jeg.Config;
import ttv.alanorMiga.jeg.init.ModEffects;
import ttv.alanorMiga.jeg.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;

public class StunRingingSound extends AbstractTickableSoundInstance
{
    public StunRingingSound()
    {
        super(ModSounds.ENTITY_STUN_GRENADE_RING.get(), SoundSource.MASTER);
        this.looping = true;
        this.attenuation = Attenuation.NONE;
        this.tick();
    }

    @Override
    public void tick()
    {
        Player player = Minecraft.getInstance().player;
        if(player != null && player.isAlive())
        {
            MobEffectInstance effect = player.getEffect(ModEffects.DEAFENED.get());
            if(effect != null)
            {
                this.x = (float) player.getX();
                this.y = (float) player.getY();
                this.z = (float) player.getZ();
                float percent = Math.min((effect.getDuration() / (float) Config.SERVER.soundFadeThreshold.get()), 1);
                this.volume = (float) (percent * Config.SERVER.ringVolume.get());
                return;
            }
        }

        //Stops playing the sound
        this.stop();
    }
}