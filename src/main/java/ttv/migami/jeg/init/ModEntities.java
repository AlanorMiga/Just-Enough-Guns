package ttv.migami.jeg.init;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.entity.Bubble;
import ttv.migami.jeg.entity.HealingTalismanEntity;
import ttv.migami.jeg.entity.Splash;
import ttv.migami.jeg.entity.animal.Boo;
import ttv.migami.jeg.entity.monster.Ghoul;
import ttv.migami.jeg.entity.projectile.*;
import ttv.migami.jeg.entity.throwable.*;

import java.util.function.BiFunction;

/**
 * Author: MrCrayfish
 */
public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MOD_ID);

    public static final RegistryObject<EntityType<ProjectileEntity>> PROJECTILE = registerProjectile("projectile", ProjectileEntity::new);
    public static final RegistryObject<EntityType<SpectreProjectileEntity>> SPECTRE_PROJECTILE = registerBasic("spectre_projectile", SpectreProjectileEntity::new);
    public static final RegistryObject<EntityType<WaterProjectileEntity>> WATER_PROJECTILE = registerBasic("water_projectile", WaterProjectileEntity::new);
    public static final RegistryObject<EntityType<WhirpoolEntity>> WATER_BOMB = registerBasic("water_bomb", WhirpoolEntity::new);
    public static final RegistryObject<EntityType<PocketBubbleEntity>> POCKET_BUBBLE = registerBasic("pocket_bubble", PocketBubbleEntity::new);
    public static final RegistryObject<EntityType<GrenadeEntity>> GRENADE = registerBasic("grenade", GrenadeEntity::new);
    public static final RegistryObject<EntityType<MissileEntity>> MISSILE = registerBasic("missile", MissileEntity::new);
    public static final RegistryObject<EntityType<ThrowableGrenadeEntity>> THROWABLE_GRENADE = registerBasic("throwable_grenade", ThrowableGrenadeEntity::new);
    public static final RegistryObject<EntityType<ThrowableStunGrenadeEntity>> THROWABLE_STUN_GRENADE = registerBasic("throwable_stun_grenade", ThrowableStunGrenadeEntity::new);
    public static final RegistryObject<EntityType<ThrowableMolotovCocktailEntity>> THROWABLE_MOLOTOV_COCKTAIL = registerBasic("throwable_molotov_cocktail", ThrowableMolotovCocktailEntity::new);
    public static final RegistryObject<EntityType<HealingTalismanEntity>> HEALING_TALISMAN = registerBasic("healing_talisman", HealingTalismanEntity::new);
    public static final RegistryObject<EntityType<ThrowableWaterBombEntity>> THROWABLE_WATER_BOMB = registerBasic("throwable_water_bomb", ThrowableWaterBombEntity::new);
    public static final RegistryObject<EntityType<ThrowablePocketBubbleEntity>> THROWABLE_POCKET_BUBBLE = registerBasic("throwable_pocket_bubble", ThrowablePocketBubbleEntity::new);

    /* Mobs */
    public static final RegistryObject<EntityType<Ghoul>> GHOUL = REGISTER.register("ghoul", () -> EntityType.Builder.of(Ghoul::new, MobCategory.MONSTER).sized(0.6F, 1.95F).build("ghoul"));
    public static final RegistryObject<EntityType<Boo>> BOO = REGISTER.register("boo", () -> EntityType.Builder.of(Boo::new, MobCategory.CREATURE).sized(0.7F, 0.6F).fireImmune().build("boo"));

    /* Custom */
    public static final RegistryObject<EntityType<Splash>> SPLASH = REGISTER.register("splash", () -> EntityType.Builder.<Splash>of(Splash::new, MobCategory.MISC).sized(5.0F, 5.0F).noSave().noSummon().fireImmune().build("splash"));
    public static final RegistryObject<EntityType<Bubble>> BUBBLE = REGISTER.register("bubble", () -> EntityType.Builder.<Bubble>of(Bubble::new, MobCategory.MISC).sized(3.0F, 1.0F).noSave().noSummon().build("bubble"));

    private static <T extends Entity> RegistryObject<EntityType<T>> registerBasic(String id, BiFunction<EntityType<T>, Level, T> function)
    {
        return REGISTER.register(id, () -> EntityType.Builder.of(function::apply, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .setTrackingRange(100)
                .setUpdateInterval(1)
                .noSummon()
                .fireImmune()
                .noSave()
                .setShouldReceiveVelocityUpdates(true).build(id));
    }

    /**
     * Entity registration that prevents the entity from being sent and tracked by clients. Projectiles
     * are rendered separately from Minecraft's entity rendering system and their logic is handled
     * exclusively by the server, why send them to the client. Projectiles also have very short time
     * in the world and are spawned many times a tick. There is no reason to send unnecessary packets
     * when it can be avoided to drastically improve the performance of the game.
     *
     * @param id       the id of the projectile
     * @param function the factory to spawn the projectile for the server
     * @param <T>      an entity that is a projectile entity
     * @return A registry object containing the new entity type
     */
    private static <T extends ProjectileEntity> RegistryObject<EntityType<T>> registerProjectile(String id, BiFunction<EntityType<T>, Level, T> function)
    {
        return REGISTER.register(id, () -> EntityType.Builder.of(function::apply, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .setTrackingRange(0)
                .noSummon()
                .fireImmune()
                .noSave()
                .setShouldReceiveVelocityUpdates(false)
                .setCustomClientFactory((spawnEntity, world) -> null)
                .build(id));
    }
}
