package ttv.alanorMiga.jeg.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.alanorMiga.jeg.JustEnoughGuns;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.common.Attachments;
import ttv.alanorMiga.jeg.common.GunModifiers;
import ttv.alanorMiga.jeg.item.*;
import ttv.alanorMiga.jeg.item.attachment.impl.Barrel;
import ttv.alanorMiga.jeg.item.attachment.impl.Stock;
import ttv.alanorMiga.jeg.item.attachment.impl.UnderBarrel;

public class ModItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    /*
    // Implementation order:
    //
    // Scar-L
    // Assault Rifle
    // Revolver
    // Waterpiper Shotgun
    // Semi Auto Rifle
    // HK G36
    //
    */

    /* Firearms */
    public static final RegistryObject<GunItem> REVOLVER = REGISTER.register("revolver",
            () -> new MakeshiftGunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<GunItem>WATERPIPE_SHOTGUN = REGISTER.register("waterpipe_shotgun",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
                    .rarity(Rarity.UNCOMMON)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<MakeshiftRifleItem>CUSTOM_SMG = REGISTER.register("custom_smg",
            () -> new MakeshiftRifleItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(128)
                    .rarity(Rarity.UNCOMMON)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<GunItem> SEMI_AUTO_RIFLE = REGISTER.register("semi_auto_rifle",
            () -> new MakeshiftRifleItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.UNCOMMON)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<GunItem> ASSAULT_RIFLE = REGISTER.register("assault_rifle",
            () -> new MakeshiftRifleItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.RARE)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<MakeshiftShotgunItem> PUMP_SHOTGUN = REGISTER.register("pump_shotgun",
            () -> new MakeshiftShotgunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.RARE)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<GunItem> HK_G36 = REGISTER.register("hk_g36",
            () -> new RifleItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(640)
                    .rarity(Rarity.EPIC)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<GunItem> SCAR_L = REGISTER.register("scar_l",
            () -> new RifleItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(640)
                    .rarity(Rarity.EPIC)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<RifleItem> BOLT_ACTION_RIFLE = REGISTER.register("bolt_action_rifle",
            () -> new RifleItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(384)
                    .rarity(Rarity.EPIC)
                    .tab(JustEnoughGuns.GROUP)));

    //public static final RegistryObject<Item> GRENADE_LAUNCHER = REGISTER.register("grenade_launcher", () -> new GunItem(new Item.Properties().stacksTo(1)));
    //public static final RegistryObject<Item> BAZOOKA = REGISTER.register("bazooka", () -> new GunItem(new Item.Properties().stacksTo(1)));

    /* Projectiles And Throwables */
    //public static final RegistryObject<Item> MISSILE = REGISTER.register("missile", () -> new AmmoItem(new Item.Properties()));
    public static final RegistryObject<Item> GRENADE = REGISTER.register("grenade",
            () -> new GrenadeItem(new Item.Properties()
                    .stacksTo(16)
                    .tab(JustEnoughGuns.GROUP)
                    , 20 * 4));
    public static final RegistryObject<Item> STUN_GRENADE = REGISTER.register("stun_grenade",
            () -> new StunGrenadeItem(new Item.Properties()
                    .stacksTo(16)
                    .tab(JustEnoughGuns.GROUP)
                    , 72000));
    public static final RegistryObject<Item> MOLOTOV_COCKTAIL = REGISTER.register("molotov_cocktail",
            () -> new MolotovCocktailItem(new Item.Properties()
                    .stacksTo(16)
                    .tab(JustEnoughGuns.GROUP)
                    , 72000));

    /* Ammo */
    public static final RegistryObject<Item> RIFLE_AMMO = REGISTER.register("rifle_ammo",
            () -> new AmmoItem(new Item.Properties()
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> PISTOL_AMMO = REGISTER.register("pistol_ammo",
            () -> new AmmoItem(new Item.Properties()
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> HANDMADE_SHELL = REGISTER.register("handmade_shell",
            () -> new AmmoItem(new Item.Properties()
                    .stacksTo(16)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> SHOTGUN_SHELL = REGISTER.register("shotgun_shell",
            () -> new AmmoItem(new Item.Properties()
                    .stacksTo(16)
                    .tab(JustEnoughGuns.GROUP)));

    /* Scope Attachments */
    public static final RegistryObject<Item> HOLOGRAPHIC_SIGHT = REGISTER.register("holographic_sight",
            () -> new ScopeItem(Attachments.HOLOGRAPHIC_SIGHT, new Item.Properties()
                    .stacksTo(1)
                    .durability(800)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> TELESCOPIC_SIGHT = REGISTER.register("telescopic_sight",
            () -> new ScopeItem(Attachments.TELESCOPIC_SIGHT, new Item.Properties()
                    .stacksTo(1)
                    .durability(800)
                    .tab(JustEnoughGuns.GROUP)));

    /* Stock Attachments */
    public static final RegistryObject<Item> MAKESHIFT_STOCK = REGISTER.register("makeshift_stock",
            () -> new MakeshiftStockItem(Stock.create(
                    GunModifiers.MAKESHIFT_CONTROL),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(300)
                            .tab(JustEnoughGuns.GROUP)
                            
                    , false));
    public static final RegistryObject<Item> LIGHT_STOCK = REGISTER.register("light_stock",
            () -> new StockItem(Stock.create(
                    GunModifiers.BETTER_CONTROL),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(600)
                            .tab(JustEnoughGuns.GROUP)
                            
                    , false));
    public static final RegistryObject<Item> TACTICAL_STOCK = REGISTER.register("tactical_stock",
            () -> new StockItem(Stock.create(
                    GunModifiers.STABILISED),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(800)
                            .tab(JustEnoughGuns.GROUP)
                            
                    , false));
    public static final RegistryObject<Item> WEIGHTED_STOCK = REGISTER.register("weighted_stock",
            () -> new StockItem(Stock.create(
                    GunModifiers.SUPER_STABILISED),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(1000)
                            .tab(JustEnoughGuns.GROUP)));

    /* Barrel Attachments */
    public static final RegistryObject<Item> SILENCER = REGISTER.register("silencer",
            () -> new BarrelItem(Barrel.create(
                    0.0F,
                    GunModifiers.SILENCED),
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(500)
                            .tab(JustEnoughGuns.GROUP)));
                            //GunModifiers.REDUCED_DAMAGE),

    /* Under Barrel Attachments */
    public static final RegistryObject<Item> LIGHT_GRIP = REGISTER.register("light_grip",
            () -> new UnderBarrelItem(UnderBarrel.create(
                    GunModifiers.LIGHT_RECOIL), new
                    Item.Properties()
                    .stacksTo(1)
                    .durability(600)
                    .tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> VERTICAL_GRIP = REGISTER.register("vertical_grip",
            () -> new UnderBarrelItem(UnderBarrel.create(
                    GunModifiers.REDUCED_RECOIL), new
                    Item.Properties()
                    .stacksTo(1)
                    .durability(800)
                    .tab(JustEnoughGuns.GROUP)));

    public static final RegistryObject<Item> ANGLED_GRIP = REGISTER.register("angled_grip",
            () -> new UnderBarrelItem(UnderBarrel.create(
                    GunModifiers.REDUCED_RECOIL), new
                    Item.Properties()
                    .stacksTo(1)
                    .durability(800)
                    .tab(JustEnoughGuns.GROUP)));

    /* Items */
    public static final RegistryObject<Item> SCRAP = REGISTER.register("scrap",
            () -> new Item(new Item.Properties().tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> TECH_TRASH = REGISTER.register("tech_trash",
            () -> new Item(new Item.Properties().tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> CIRCUIT_BOARD = REGISTER.register("circuit_board",
            () -> new Item(new Item.Properties().tab(JustEnoughGuns.GROUP)));
    //public static final RegistryObject<Item> SPRING = REGISTER.register("spring",
    //        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GUNMETAL_GRIT = REGISTER.register("gunmetal_grit",
            () -> new Item(new Item.Properties().tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> GUNMETAL_INGOT = REGISTER.register("gunmetal_ingot",
            () -> new Item(new Item.Properties().tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> GUNNITE_INGOT = REGISTER.register("gunnite_ingot",
            () -> new Item(new Item.Properties().tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> RAW_BRIMSTONE = REGISTER.register("raw_brimstone",
            () -> new Item(new Item.Properties().tab(JustEnoughGuns.GROUP)));
    public static final RegistryObject<Item> BRIMSTONE_CRYSTAL = REGISTER.register("brimstone_crystal",
            () -> new Item(new Item.Properties().tab(JustEnoughGuns.GROUP)));



}