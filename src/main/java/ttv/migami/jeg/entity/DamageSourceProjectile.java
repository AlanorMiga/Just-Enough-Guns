package ttv.migami.jeg.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import ttv.migami.jeg.Reference;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class DamageSourceProjectile extends IndirectEntityDamageSource
{
    private static final String[] DEATH_TYPES = { "killed", "eliminated", "executed", "annihilated", "decimated" };
    private static final Random RAND = new Random();

    private final ItemStack weapon;

    public DamageSourceProjectile(String damageTypeIn, Entity source, @Nullable Entity indirectEntityIn, ItemStack weapon)
    {
        super(damageTypeIn, source, indirectEntityIn);
        this.weapon = weapon;
    }

    public ItemStack getWeapon()
    {
        return weapon;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity entityLivingBaseIn)
    {
        Component textComponent = this.getEntity() == null ? this.entity.getDisplayName() : this.getEntity().getDisplayName();
        String deathKey = String.format("death.attack.%s.%s.%s", Reference.MOD_ID, this.msgId, DEATH_TYPES[RAND.nextInt(DEATH_TYPES.length)]);
        return new TranslatableComponent(deathKey, entityLivingBaseIn.getDisplayName(), textComponent);
    }
}
