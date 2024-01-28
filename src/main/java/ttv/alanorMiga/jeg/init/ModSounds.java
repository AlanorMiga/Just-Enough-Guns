package ttv.alanorMiga.jeg.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.alanorMiga.jeg.Reference;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);

    public static final RegistryObject<SoundEvent> SCAR_L_FIRE = register("item.scar_l.fire");
    public static final RegistryObject<SoundEvent> SCAR_L_SILENCED_FIRE = register("item.scar_l.silenced_fire");
    public static final RegistryObject<SoundEvent> ASSAULT_RIFLE_FIRE = register("item.assault_rifle.fire");
    public static final RegistryObject<SoundEvent> ASSAULT_RIFLE_SILENCED_FIRE = register("item.assault_rifle.silenced_fire");
    public static final RegistryObject<SoundEvent> REVOLVER_FIRE = register("item.revolver.fire");
    public static final RegistryObject<SoundEvent> REVOLVER_SILENCED_FIRE = register("item.revolver.silenced_fire");
    public static final RegistryObject<SoundEvent> WATERPIPE_SHOTGUN_FIRE = register("item.waterpipe_shotgun.fire");
    public static final RegistryObject<SoundEvent> SEMI_AUTO_RIFLE_FIRE = register("item.semi_auto_rifle.fire");
    public static final RegistryObject<SoundEvent> SEMI_AUTO_RIFLE_SILENCED_FIRE = register("item.semi_auto_rifle.silenced_fire");
    public static final RegistryObject<SoundEvent> HK_G36_FIRE = register("item.hk_g36.fire");
    public static final RegistryObject<SoundEvent> HK_G36_SILENCED_FIRE = register("item.hk_g36.silenced_fire");
    public static final RegistryObject<SoundEvent> ITEM_PISTOL_SILENCED_FIRE = register("item.pistol.silenced_fire");
    public static final RegistryObject<SoundEvent> ITEM_PISTOL_ENCHANTED_FIRE = register("item.pistol.enchanted_fire");
    public static final RegistryObject<SoundEvent> ITEM_PISTOL_RELOAD = register("item.pistol.reload");
    public static final RegistryObject<SoundEvent> ITEM_PISTOL_COCK = register("item.pistol.cock");
    public static final RegistryObject<SoundEvent> ITEM_GRENADE_LAUNCHER_FIRE = register("item.grenade_launcher.fire");
    public static final RegistryObject<SoundEvent> ITEM_BAZOOKA_FIRE = register("item.bazooka.fire");
    public static final RegistryObject<SoundEvent> ITEM_GRENADE_PIN = register("item.grenade.pin");
    public static final RegistryObject<SoundEvent> ENTITY_STUN_GRENADE_EXPLOSION = register("entity.stun_grenade.explosion");
    public static final RegistryObject<SoundEvent> ENTITY_STUN_GRENADE_RING = register("entity.stun_grenade.ring");
    public static final RegistryObject<SoundEvent> UI_WEAPON_ATTACH = register("ui.weapon.attach");
    public static final RegistryObject<SoundEvent> RECYCLER_LOOP = register("block.recycler_loop");
    public static final RegistryObject<SoundEvent> RECYCLER_SHREDDING = register("block.recycler_shredding");

    private static RegistryObject<SoundEvent> register(String key) {
        return REGISTER.register(key, () -> new SoundEvent(new ResourceLocation(Reference.MOD_ID, key)));
    }
}
