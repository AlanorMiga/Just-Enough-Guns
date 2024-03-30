package ttv.migami.jeg.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.migami.jeg.Reference;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);

    public static final RegistryObject<SoundEvent> COMBAT_RIFLE_FIRE = register("item.combat_rifle.fire");
    public static final RegistryObject<SoundEvent> COMBAT_RIFLE_SILENCED_FIRE = register("item.combat_rifle.silenced_fire");
    public static final RegistryObject<SoundEvent> ASSAULT_RIFLE_FIRE = register("item.assault_rifle.fire");
    public static final RegistryObject<SoundEvent> ASSAULT_RIFLE_SILENCED_FIRE = register("item.assault_rifle.silenced_fire");
    public static final RegistryObject<SoundEvent> REVOLVER_FIRE = register("item.revolver.fire");
    public static final RegistryObject<SoundEvent> REVOLVER_SILENCED_FIRE = register("item.revolver.silenced_fire");
    public static final RegistryObject<SoundEvent> WATERPIPE_SHOTGUN_FIRE = register("item.waterpipe_shotgun.fire");
    public static final RegistryObject<SoundEvent> SEMI_AUTO_RIFLE_FIRE = register("item.semi_auto_rifle.fire");
    public static final RegistryObject<SoundEvent> SEMI_AUTO_RIFLE_SILENCED_FIRE = register("item.semi_auto_rifle.silenced_fire");
    public static final RegistryObject<SoundEvent> BURST_RIFLE_FIRE = register("item.burst_rifle.fire");
    public static final RegistryObject<SoundEvent> BURST_RIFLE_SILENCED_FIRE = register("item.burst_rifle.silenced_fire");
    public static final RegistryObject<SoundEvent> PUMP_SHOTGUN_FIRE = register("item.pump_shotgun.fire");
    public static final RegistryObject<SoundEvent> PUMP_SHOTGUN_SILENCED_FIRE = register("item.pump_shotgun.silenced_fire");
    public static final RegistryObject<SoundEvent> BOLT_ACTION_RIFLE_FIRE = register("item.bolt_action_rifle.fire");
    public static final RegistryObject<SoundEvent> CUSTOM_SMG_FIRE = register("item.custom_smg.fire");
    public static final RegistryObject<SoundEvent> CUSTOM_SMG_SILENCED_FIRE = register("item.custom_smg.silenced_fire");
    public static final RegistryObject<SoundEvent> BLOSSOM_RIFLE_FIRE = register("item.blossom_rifle.fire");
    public static final RegistryObject<SoundEvent> BLOSSOM_RIFLE_SILENCED_FIRE = register("item.blossom_rifle.silenced_fire");
    public static final RegistryObject<SoundEvent> DOUBLE_BARREL_SHOTGUN_FIRE = register("item.double_barrel_shotgun.fire");
    public static final RegistryObject<SoundEvent> HOLY_SHOTGUN_FIRE = register("item.holy_shotgun.fire");
    public static final RegistryObject<SoundEvent> HOLY_SHOTGUN_SILENCED_FIRE = register("item.holy_shotgun.silenced_fire");

    public static final RegistryObject<SoundEvent> ITEM_PISTOL_RELOAD = register("item.pistol.reload");
    public static final RegistryObject<SoundEvent> ITEM_PISTOL_COCK = register("item.pistol.cock");
    public static final RegistryObject<SoundEvent> ITEM_GRENADE_PIN = register("item.grenade.pin");
    public static final RegistryObject<SoundEvent> ENTITY_STUN_GRENADE_EXPLOSION = register("entity.stun_grenade.explosion");
    public static final RegistryObject<SoundEvent> ENTITY_STUN_GRENADE_RING = register("entity.stun_grenade.ring");
    public static final RegistryObject<SoundEvent> UI_WEAPON_ATTACH = register("ui.weapon.attach");
    public static final RegistryObject<SoundEvent> RECYCLER_LOOP = register("block.recycler_loop");
    public static final RegistryObject<SoundEvent> RECYCLER_SHREDDING = register("block.recycler_shredding");

    /* Mob Sounds */
    public static final RegistryObject<SoundEvent> ENTITY_GHOUL_AMBIENT = register("entity.ghoul.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_GHOUL_DEATH = register("entity.ghoul.death");
    public static final RegistryObject<SoundEvent> ENTITY_GHOUL_HURT = register("entity.ghoul.hurt");

    private static RegistryObject<SoundEvent> register(String key)
    {
        return REGISTER.register(key, () -> new SoundEvent(new ResourceLocation(Reference.MOD_ID, key)));
    }
}
