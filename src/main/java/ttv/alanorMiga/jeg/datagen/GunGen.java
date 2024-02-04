package ttv.alanorMiga.jeg.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.common.GripType;
import ttv.alanorMiga.jeg.common.Gun;
import ttv.alanorMiga.jeg.init.ModItems;
import ttv.alanorMiga.jeg.init.ModSounds;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class GunGen extends GunProvider
{
    public GunGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
    }

    @Override
    protected void registerGuns()
    {
        this.addGun(new ResourceLocation(Reference.MOD_ID, "revolver"), Gun.Builder.create()

                // General
                .setAuto(false)
                .setFireRate(3)
                .setGripType(GripType.ONE_HANDED)
                .setRecoilKick(0.33F)
                .setRecoilAngle(4.0F)
                .setAlwaysSpread(false)
                .setSpread(3.0F)

                // Reloads
                .setMaxAmmo(8)
                .setMagFed(false)
                .setReloadTimer(30)
                .setEmptyMagTimer(10)

                // Projectile
                .setAmmo(ModItems.PISTOL_AMMO.get())
                .setProjectileVisible(false)
                .setDamage(5F)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(10F)
                .setProjectileLife(40)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(true)

                // Sounds
                .setFireSound(ModSounds.REVOLVER_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setSilencedFireSound(ModSounds.REVOLVER_SILENCED_FIRE.get())
                .setEnchantedFireSound(ModSounds.REVOLVER_FIRE.get())

                // Attachments
                .setMuzzleFlash(0.8, 0.0, 2.9, 1.7)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.6F)
                        .setOffset(0.0, 3.45, -1.75))
                .setBarrel(1.0F, 0.0, 3, -1.6)
                .setStock(0.0F, 0.0, 0.0, 0.0)

                .build());

        this.addGun(new ResourceLocation(Reference.MOD_ID, "waterpipe_shotgun"), Gun.Builder.create()

                // General
                .setAuto(false)
                .setFireRate(8)
                .setGripType(GripType.TWO_HANDED)
                .setRecoilKick(0.5F)
                .setRecoilAngle(10.0F)
                .setAlwaysSpread(true)
                .setSpread(15.0F)
                .setProjectileAmount(18)

                // Reloads
                .setMaxAmmo(1)
                .setMagFed(true)
                .setReloadTimer(45)
                .setEmptyMagTimer(0)

                // Projectile
                .setAmmo(ModItems.HANDMADE_SHELL.get())
                .setProjectileVisible(false)
                .setDamage(20F)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(6F)
                .setProjectileLife(10)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(false)

                // Sounds
                .setFireSound(ModSounds.WATERPIPE_SHOTGUN_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setEnchantedFireSound(ModSounds.WATERPIPE_SHOTGUN_FIRE.get())

                // Attachments
                .setMuzzleFlash(1.2, 0.0, 2.05, -2.6)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.7F)
                        .setOffset(0.0, 3.0, 0.75))

                .build());

        this.addGun(new ResourceLocation(Reference.MOD_ID, "custom_smg"), Gun.Builder.create()

                // General
                .setAuto(true)
                .setFireRate(2)
                .setGripType(GripType.TWO_HANDED)
                .setRecoilKick(0.33F)
                .setRecoilAngle(1.0F)
                .setAlwaysSpread(true)
                .setSpread(4.0F)

                // Reloads
                .setMaxAmmo(24)
                .setMagFed(true)
                .setReloadTimer(30)
                .setEmptyMagTimer(10)

                // Projectile
                .setAmmo(ModItems.PISTOL_AMMO.get())
                .setProjectileVisible(false)
                .setDamage(4.0F)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(12F)
                .setProjectileLife(60)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(true)

                // Sounds
                .setFireSound(ModSounds.CUSTOM_SMG_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setSilencedFireSound(ModSounds.CUSTOM_SMG_SILENCED_FIRE.get())
                .setEnchantedFireSound(ModSounds.CUSTOM_SMG_FIRE.get())

                // Attachments
                .setMuzzleFlash(0.8, 0.0, 2.95, 0.2)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.6F)
                        .setOffset(0.0, 3.85, -1.75))
                .setBarrel(1.0F, 0.0, 3.0, -2.0)
                .setStock(0.0F, 0.0, 0.0, 0.0)

                .build());

        this.addGun(new ResourceLocation(Reference.MOD_ID, "semi_auto_rifle"), Gun.Builder.create()

                // General
                .setAuto(false)
                .setFireRate(4)
                .setGripType(GripType.TWO_HANDED)
                .setRecoilKick(0.15F)
                .setRecoilAngle(3.0F)
                .setAlwaysSpread(false)
                .setSpread(3.0F)

                // Reloads
                .setMaxAmmo(16)
                .setMagFed(false)
                .setReloadTimer(30)
                .setEmptyMagTimer(10)

                // Projectile
                .setAmmo(ModItems.RIFLE_AMMO.get())
                .setProjectileVisible(false)
                .setDamage(5F)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(12F)
                .setProjectileLife(60)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(true)

                // Sounds
                .setFireSound(ModSounds.SEMI_AUTO_RIFLE_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setSilencedFireSound(ModSounds.SEMI_AUTO_RIFLE_SILENCED_FIRE.get())
                .setEnchantedFireSound(ModSounds.SEMI_AUTO_RIFLE_FIRE.get())

                // Attachments
                .setMuzzleFlash(0.8, 0.0, 3, -2.9)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.6F)
                        .setOffset(0.0, 3.55, -1.25))
                .setScope(1.0F, 0.0, 3.145, 6.0)
                .setBarrel(1.0F, 0.0, 3.05, -5.8)
                .setStock(0.0F, 0.0, 0.0, 0.0)
                .setUnderBarrel(1.0F, 0.0, 2.3, 1.4)

                .build());

        this.addGun(new ResourceLocation(Reference.MOD_ID, "assault_rifle"), Gun.Builder.create()

                // General
                .setAuto(true)
                .setFireRate(4)
                .setGripType(GripType.TWO_HANDED)
                .setRecoilKick(0.33F)
                .setRecoilAngle(4.0F)
                .setAlwaysSpread(true)
                .setSpread(4.0F)

                // Reloads
                .setMaxAmmo(30)
                .setMagFed(true)
                .setReloadTimer(30)
                .setEmptyMagTimer(10)

                // Projectile
                .setAmmo(ModItems.RIFLE_AMMO.get())
                .setProjectileVisible(false)
                .setDamage(6F)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(14F)
                .setProjectileLife(60)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(true)

                // Sounds
                .setFireSound(ModSounds.ASSAULT_RIFLE_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setSilencedFireSound(ModSounds.ASSAULT_RIFLE_SILENCED_FIRE.get())
                .setEnchantedFireSound(ModSounds.ASSAULT_RIFLE_FIRE.get())

                // Attachments
                .setMuzzleFlash(0.8, 0.0, 2.7, -2.7)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.6F)
                        .setOffset(0.0, 3.75, 0.75))
                .setScope(1.0F, 0.0, 3.35, 4.5)
                .setBarrel(1.0F, 0.0, 2.75, -4.9)
                .setStock(0.0F, 0.0, 0.0, 0.0)
                .setUnderBarrel(1.0F, 0.0, 1.5, 1.85)

                .build());

        this.addGun(new ResourceLocation(Reference.MOD_ID, "pump_shotgun"), Gun.Builder.create()

                // General
                .setAuto(false)
                .setFireRate(22)
                .setGripType(GripType.TWO_HANDED)
                .setRecoilKick(0.5F)
                .setRecoilAngle(10.0F)
                .setAlwaysSpread(true)
                .setSpread(25.0F)
                .setProjectileAmount(12)

                // Reloads
                .setMaxAmmo(6)
                .setMagFed(false)
                .setReloadTimer(0)
                .setEmptyMagTimer(0)

                // Projectile
                .setAmmo(ModItems.SHOTGUN_SHELL.get())
                .setProjectileVisible(false)
                .setDamage(20F)
                .setReduceDamageOverLife(true)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(6F)
                .setProjectileLife(10)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(false)

                // Sounds
                .setFireSound(ModSounds.PUMP_SHOTGUN_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setSilencedFireSound(ModSounds.PUMP_SHOTGUN_SILENCED_FIRE.get())
                .setEnchantedFireSound(ModSounds.PUMP_SHOTGUN_FIRE.get())

                // Attachments
                .setMuzzleFlash(0.8, 0.0, 2.05, -2.03)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.6F)
                        .setOffset(0.0, 3.5, -1.25))
                .setScope(1.0F, 0.0, 3.32, 3.835)
                .setBarrel(1.0F, 0.0, 3.155, -4.6)
                .setStock(0.0F, 0.0, 0.0, 0.0)
                .setUnderBarrel(0.0F, 0.0, 1.77, 2.125)

                .build());

        this.addGun(new ResourceLocation(Reference.MOD_ID, "bolt_action_rifle"), Gun.Builder.create()

                // General
                .setAuto(false)
                .setFireRate(22)
                .setGripType(GripType.TWO_HANDED)
                .setRecoilKick(0.25F)
                .setRecoilAngle(4.0F)
                .setAlwaysSpread(true)
                .setSpread(0.5F)

                // Reloads
                .setMaxAmmo(4)
                .setMagFed(false)
                .setReloadTimer(30)
                .setEmptyMagTimer(10)

                // Projectile
                .setAmmo(ModItems.RIFLE_AMMO.get())
                .setProjectileVisible(false)
                .setDamage(24F)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(24F)
                .setProjectileLife(60)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(true)

                // Sounds
                .setFireSound(ModSounds.BOLT_ACTION_RIFLE_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setEnchantedFireSound(ModSounds.BOLT_ACTION_RIFLE_FIRE.get())

                // Attachments
                .setMuzzleFlash(0.8, 0.0, 3, -9.7)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.6F)
                        .setOffset(0.0, 3.35, -1.25))
                .setScope(1.0F, 0.0, 2.94, -0.45)

                .build());

        this.addGun(new ResourceLocation(Reference.MOD_ID, "hk_g36"), Gun.Builder.create()

                // General
                .setAuto(true)
                .setBurst(true)
                .setBurstAmount(3)
                .setFireRate(2)
                .setGripType(GripType.TWO_HANDED)
                .setRecoilKick(0.25F)
                .setRecoilAngle(4.0F)
                .setAlwaysSpread(true)
                .setSpread(2.0F)

                // Reloads
                .setMaxAmmo(30)
                .setMagFed(true)
                .setReloadTimer(30)
                .setEmptyMagTimer(10)

                // Projectile
                .setAmmo(ModItems.RIFLE_AMMO.get())
                .setProjectileVisible(false)
                .setDamage(6.5F)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(16F)
                .setProjectileLife(80)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(false)

                // Sounds
                .setFireSound(ModSounds.HK_G36_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setSilencedFireSound(ModSounds.HK_G36_SILENCED_FIRE.get())
                .setEnchantedFireSound(ModSounds.HK_G36_FIRE.get())

                // Attachments
                .setMuzzleFlash(0.8, 0.0, 3.5, -3)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.6F)
                        .setOffset(0.0, 5, -1.75))
                .setScope(1.0F, 0.0, 4.645, 5.4)
                .setBarrel(1.0F, 0.0, 3.5, -6)
                .setStock(0.0F, 0.0, 0.0, 0.0)
                .setUnderBarrel(1.0F, 0.0, 2.4, 1.2)

                .build());

        this.addGun(new ResourceLocation(Reference.MOD_ID, "scar_l"), Gun.Builder.create()

                // General
                .setAuto(true)
                .setFireRate(3)
                .setGripType(GripType.TWO_HANDED)
                .setRecoilKick(0.25F)
                .setRecoilAngle(4.0F)
                .setAlwaysSpread(true)
                .setSpread(3.0F)

                // Reloads
                .setMaxAmmo(30)
                .setMagFed(true)
                .setReloadTimer(30)
                .setEmptyMagTimer(10)

                // Projectile
                .setAmmo(ModItems.RIFLE_AMMO.get())
                .setProjectileVisible(false)
                .setDamage(7.0F)
                .setProjectileSize(0.05F)
                .setProjectileSpeed(16F)
                .setProjectileLife(80)
                .setProjectileTrailLengthMultiplier(2)
                .setProjectileTrailColor(0xFFFF00)
                .setProjectileAffectedByGravity(false)

                // Sounds
                .setFireSound(ModSounds.SCAR_L_FIRE.get())
                .setReloadSound(ModSounds.ITEM_PISTOL_RELOAD.get())
                .setCockSound(ModSounds.ITEM_PISTOL_COCK.get())
                .setSilencedFireSound(ModSounds.SCAR_L_SILENCED_FIRE.get())
                .setEnchantedFireSound(ModSounds.SCAR_L_FIRE.get())

                // Attachments
                .setMuzzleFlash(0.8, 0.0, 3.7, -4.7)
                .setZoom(Gun.Modules.Zoom.builder()
                        .setFovModifier(0.6F)
                        .setOffset(0.0, 5.8, -1.75))
                .setScope(1.0F, 0.0, 4.75, 5.5)
                .setBarrel(0.8F, 0.0, 3.75, -7.45)
                .setStock(0.0F, 0.0, 0.0, 0.0)
                .setUnderBarrel(0.0F, 0.0, 2.8, -1.0)

                .build());

    }
}
