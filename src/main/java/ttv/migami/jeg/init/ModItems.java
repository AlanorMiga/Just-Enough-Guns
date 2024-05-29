package ttv.migami.jeg.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.common.Attachments;
import ttv.migami.jeg.common.GunModifiers;
import ttv.migami.jeg.item.*;
import ttv.migami.jeg.item.attachment.impl.Barrel;
import ttv.migami.jeg.item.attachment.impl.Magazine;
import ttv.migami.jeg.item.attachment.impl.Stock;
import ttv.migami.jeg.item.attachment.impl.UnderBarrel;

public class ModItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    /* Firearms */
    /* Scrap Tier */
    public static final RegistryObject<GunItem> REVOLVER = REGISTER.register("revolver",
            () -> new MakeshiftGunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
            ));
    public static final RegistryObject<GunItem>WATERPIPE_SHOTGUN = REGISTER.register("waterpipe_shotgun",
            () -> new MakeshiftGunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
            ));
    public static final RegistryObject<GunItem>CUSTOM_SMG = REGISTER.register("custom_smg",
            () -> new MakeshiftGunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(256)
                    .rarity(Rarity.UNCOMMON)
            ));
    public static final RegistryObject<GunItem>DOUBLE_BARREL_SHOTGUN = REGISTER.register("double_barrel_shotgun",
            () -> new MakeshiftGunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
            ));
    /* Gunmetal Tier */
    public static final RegistryObject<GunItem> SEMI_AUTO_RIFLE = REGISTER.register("semi_auto_rifle",
            () -> new MakeshiftGunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.UNCOMMON)
            ));
    public static final RegistryObject<GunItem> ASSAULT_RIFLE = REGISTER.register("assault_rifle",
            () -> new MakeshiftGunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.RARE)
            ));
    public static final RegistryObject<MakeshiftGunItem> PUMP_SHOTGUN = REGISTER.register("pump_shotgun",
            () -> new MakeshiftGunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.RARE)
            ));
    /* Gunnite Tier */
    public static final RegistryObject<GunItem> BURST_RIFLE = REGISTER.register("burst_rifle",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(640)
                    .rarity(Rarity.EPIC)
            ));
    public static final RegistryObject<GunItem> COMBAT_RIFLE = REGISTER.register("combat_rifle",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(640)
                    .rarity(Rarity.EPIC)
            ));
    public static final RegistryObject<GunItem> BOLT_ACTION_RIFLE = REGISTER.register("bolt_action_rifle",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.EPIC)
            ));

    /* Spectre Tier */
    public static final RegistryObject<GunItem> BLOSSOM_RIFLE = REGISTER.register("blossom_rifle",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(512)
                    .rarity(Rarity.EPIC)
            ));

    public static final RegistryObject<GunItem> HOLY_SHOTGUN = REGISTER.register("holy_shotgun",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.EPIC)
            ));

    /* Water Tier */
    public static final RegistryObject<AtlanteanSpearItem> ATLANTEAN_SPEAR = REGISTER.register("atlantean_spear",
            () -> new AtlanteanSpearItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
                    .rarity(Rarity.EPIC)
            ));

    public static final RegistryObject<TyphooneeItem> TYPHOONEE = REGISTER.register("typhoonee",
            () -> new TyphooneeItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
                    .rarity(Rarity.EPIC)
            ));

    /*public static final RegistryObject<UnderwaterFirearmItem> BUBBLE_CANNON = REGISTER.register("bubble_cannon",
            () -> new UnderwaterFirearmItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
                    .rarity(Rarity.EPIC)
            ));*/

    //public static final RegistryObject<Item> GRENADE_LAUNCHER = REGISTER.register("grenade_launcher", () -> new GunItem(new Item.Properties().stacksTo(1)));
    //public static final RegistryObject<Item> BAZOOKA = REGISTER.register("bazooka", () -> new GunItem(new Item.Properties().stacksTo(1)));

    /* Projectiles And Throwables */
    //public static final RegistryObject<Item> MISSILE = REGISTER.register("missile", () -> new AmmoItem(new Item.Properties()));
    public static final RegistryObject<Item> GRENADE = REGISTER.register("grenade",
            () -> new GrenadeItem(new Item.Properties()
                    .stacksTo(16)
                    , 20 * 4
            ));
    public static final RegistryObject<Item> STUN_GRENADE = REGISTER.register("stun_grenade",
            () -> new StunGrenadeItem(new Item.Properties()
                    .stacksTo(16)
                    , 72000
            ));
    public static final RegistryObject<Item> MOLOTOV_COCKTAIL = REGISTER.register("molotov_cocktail",
            () -> new MolotovCocktailItem(new Item.Properties()
                    .stacksTo(16)
                    , 72000
            ));
    public static final RegistryObject<Item> WATER_BOMB = REGISTER.register("water_bomb",
            () -> new WaterBombItem(new Item.Properties()
                    .stacksTo(16)
                    , 72000
            ));
    public static final RegistryObject<Item> POCKET_BUBBLE = REGISTER.register("pocket_bubble",
            () -> new PocketBubbleItem(new Item.Properties()
                    .stacksTo(16)
            ));

    /* Ammo */
    public static final RegistryObject<Item> RIFLE_AMMO = REGISTER.register("rifle_ammo",
            () -> new AmmoItem(new Item.Properties()
            ));
    public static final RegistryObject<Item> PISTOL_AMMO = REGISTER.register("pistol_ammo",
            () -> new AmmoItem(new Item.Properties()
            ));
    public static final RegistryObject<Item> HANDMADE_SHELL = REGISTER.register("handmade_shell",
            () -> new AmmoItem(new Item.Properties()
                    .stacksTo(16)
            ));
    public static final RegistryObject<Item> SHOTGUN_SHELL = REGISTER.register("shotgun_shell",
            () -> new AmmoItem(new Item.Properties()
                    .stacksTo(16)
            ));
    public static final RegistryObject<Item> SPECTRE_AMMO = REGISTER.register("spectre_ammo",
            () -> new AmmoItem(new Item.Properties()
            ));

    /* Healing Items */
    public static final RegistryObject<Item> HEALING_TALISMAN = REGISTER.register("healing_talisman",
            () -> new HealingTalismanItem(new Item.Properties()
                    .stacksTo(16)
            ));

    /* Utility Items */
    /*public static final RegistryObject<Item> GRAPPLING_HOOK = REGISTER.register("grappling_hook",
            () -> new GrapplingHookItem(new Item.Properties()
                    .stacksTo(1)
            ));*/

    /* Scope Attachments */
    public static final RegistryObject<Item> HOLOGRAPHIC_SIGHT = REGISTER.register("holographic_sight",
            () -> new ScopeItem(Attachments.HOLOGRAPHIC_SIGHT, new Item.Properties()
                    .stacksTo(1)
                    .durability(800)
            ));
    public static final RegistryObject<Item> TELESCOPIC_SIGHT = REGISTER.register("telescopic_sight",
            () -> new ScopeItem(Attachments.TELESCOPIC_SIGHT, new Item.Properties()
                    .stacksTo(1)
                    .durability(800)
            ));

    /* Stock Attachments */
    public static final RegistryObject<Item> MAKESHIFT_STOCK = REGISTER.register("makeshift_stock",
            () -> new MakeshiftStockItem(Stock.create(
                    GunModifiers.MAKESHIFT_CONTROL),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(300)
                            
                    , false
            ));
    public static final RegistryObject<Item> LIGHT_STOCK = REGISTER.register("light_stock",
            () -> new StockItem(Stock.create(
                    GunModifiers.BETTER_CONTROL),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(600)
                            
                    , false
            ));
    public static final RegistryObject<Item> TACTICAL_STOCK = REGISTER.register("tactical_stock",
            () -> new StockItem(Stock.create(
                    GunModifiers.STABILISED),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(800)
                            
                    , false
            ));
    public static final RegistryObject<Item> WEIGHTED_STOCK = REGISTER.register("weighted_stock",
            () -> new StockItem(Stock.create(
                    GunModifiers.SUPER_STABILISED),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(1000)
            ));

    /* Barrel Attachments */
    public static final RegistryObject<Item> SILENCER = REGISTER.register("silencer",
            () -> new BarrelItem(Barrel.create(
                    0.0F,
                    GunModifiers.SILENCED),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(500)
            ));
                            //GunModifiers.REDUCED_DAMAGE),

    /* Under Barrel Attachments */
    public static final RegistryObject<Item> LIGHT_GRIP = REGISTER.register("light_grip",
            () -> new UnderBarrelItem(UnderBarrel.create(
                    GunModifiers.LIGHT_RECOIL), new
                    Item.Properties()
                    .stacksTo(1)
                    .durability(600)
            ));
    public static final RegistryObject<Item> VERTICAL_GRIP = REGISTER.register("vertical_grip",
            () -> new UnderBarrelItem(UnderBarrel.create(
                    GunModifiers.REDUCED_RECOIL), new
                    Item.Properties()
                    .stacksTo(1)
                    .durability(800)
            ));

    public static final RegistryObject<Item> ANGLED_GRIP = REGISTER.register("angled_grip",
            () -> new UnderBarrelItem(UnderBarrel.create(
                    GunModifiers.REDUCED_RECOIL), new
                    Item.Properties()
                    .stacksTo(1)
                    .durability(800)
            ));

    /* Magazine */
    public static final RegistryObject<Item> EXTENDED_MAG = REGISTER.register("extended_mag",
            () -> new MagazineItem(Magazine.create(),
                    new Item.Properties()
                            .stacksTo(1)
            ));

    /* Items */
    public static final RegistryObject<Item> SCRAP = REGISTER.register("scrap",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REPAIR_KIT = REGISTER.register("repair_kit",
            () -> new ToolTipItem(new Item.Properties()));
    public static final RegistryObject<Item> TECH_TRASH = REGISTER.register("tech_trash",
            () -> new ToolTipItem(new Item.Properties()));
    public static final RegistryObject<Item> CIRCUIT_BOARD = REGISTER.register("circuit_board",
            () -> new Item(new Item.Properties()));
    //public static final RegistryObject<Item> SPRING = REGISTER.register("spring",
    //        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GUNMETAL_GRIT = REGISTER.register("gunmetal_grit",
            () -> new ToolTipItem(new Item.Properties()));
    public static final RegistryObject<Item> GUNMETAL_INGOT = REGISTER.register("gunmetal_ingot",
            () -> new ToolTipItem(new Item.Properties()));
    public static final RegistryObject<Item> GUNMETAL_NUGGET = REGISTER.register("gunmetal_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GUNNITE_INGOT = REGISTER.register("gunnite_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_BRIMSTONE = REGISTER.register("raw_brimstone",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRIMSTONE_CRYSTAL = REGISTER.register("brimstone_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ECTOPLASM = REGISTER.register("ectoplasm",
            () -> new ToolTipItem(new Item.Properties()));

    // Mobs
    public static final RegistryObject<Item> GHOUL_SPAWN_TALISMAN = REGISTER.register("ghoul_spawn_talisman",
            () -> new ForgeSpawnEggItem(ModEntities.GHOUL, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> BOO_SPAWN_HONEYCOMB = REGISTER.register("boo_spawn_honeycomb",
            () -> new ForgeSpawnEggItem(ModEntities.BOO, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    // Fallbacks
    /*public static final RegistryObject<Item> SCAR_L_FALLBACK = REGISTER.register("scar_l",
            () -> new FallbackItem(new Item.Properties()));

    public static final RegistryObject<Item> HK_G36_FALLBACK = REGISTER.register("hk_g36",
            () -> new FallbackItem(new Item.Properties()));*/

}