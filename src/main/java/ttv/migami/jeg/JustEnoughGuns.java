package ttv.migami.jeg;

import com.mrcrayfish.framework.api.client.FrameworkClientAPI;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ttv.migami.jeg.client.ClientHandler;
import ttv.migami.jeg.client.CustomGunManager;
import ttv.migami.jeg.client.MetaLoader;
import ttv.migami.jeg.client.handler.CrosshairHandler;
import ttv.migami.jeg.client.render.entity.BooRenderer;
import ttv.migami.jeg.client.render.entity.GhoulRenderer;
import ttv.migami.jeg.common.BoundingBoxManager;
import ttv.migami.jeg.common.ProjectileManager;
import ttv.migami.jeg.crafting.ScrapWorkbenchIngredient;
import ttv.migami.jeg.datagen.BlockTagGen;
import ttv.migami.jeg.datagen.EntityTagGen;
import ttv.migami.jeg.datagen.GunGen;
import ttv.migami.jeg.datagen.ItemTagGen;
import ttv.migami.jeg.enchantment.EnchantmentTypes;
import ttv.migami.jeg.entity.client.BubbleRenderer;
import ttv.migami.jeg.entity.client.SplashRenderer;
import ttv.migami.jeg.entity.projectile.PocketBubbleEntity;
import ttv.migami.jeg.entity.projectile.ProjectileEntity;
import ttv.migami.jeg.entity.projectile.SpectreProjectileEntity;
import ttv.migami.jeg.entity.projectile.WhirpoolEntity;
import ttv.migami.jeg.entity.throwable.GrenadeEntity;
import ttv.migami.jeg.init.*;
import ttv.migami.jeg.network.PacketHandler;
import ttv.migami.jeg.world.ModEntityGeneration;
import ttv.migami.jeg.world.feature.ModOreFeatures;

@Mod(Reference.MOD_ID)
public class JustEnoughGuns {
    public static boolean debugging = false;
    public static boolean controllableLoaded = false;
    public static boolean backpackedLoaded = false;
    public static boolean playerReviveLoaded = false;
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static final CreativeModeTab GROUP = new CreativeModeTab(Reference.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            ItemStack stack = new ItemStack(ModItems.ASSAULT_RIFLE.get());
            stack.getOrCreateTag().putInt("AmmoCount", ModItems.ASSAULT_RIFLE.get().getGun().getReloads().getMaxAmmo());
            return stack;
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            super.fillItemList(items);
            CustomGunManager.fill(items);
        }
    }.setEnchantmentCategories(EnchantmentTypes.GUN, EnchantmentTypes.SEMI_AUTO_GUN);

    public JustEnoughGuns() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModBlocks.REGISTER.register(bus);
        ModContainers.REGISTER.register(bus);
        ModEffects.REGISTER.register(bus);
        ModEnchantments.REGISTER.register(bus);
        ModEntities.REGISTER.register(bus);
        ModItems.REGISTER.register(bus);
        ModParticleTypes.REGISTER.register(bus);
        ModRecipeSerializers.REGISTER.register(bus);
        ModSounds.REGISTER.register(bus);
        ModTileEntities.REGISTER.register(bus);
        ModPointOfInterestTypes.REGISTER.register(bus);
        MinecraftForge.EVENT_BUS.addListener(ModOreFeatures::onBiomeLoadingEvent);
        MinecraftForge.EVENT_BUS.addListener(ModEntityGeneration::onEntitySpawn);
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onGatherData);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(CrosshairHandler::onConfigReload);
            bus.addListener(ClientHandler::onRegisterReloadListener);
            FrameworkClientAPI.registerDataLoader(MetaLoader.getInstance());
        });
        controllableLoaded = ModList.get().isLoaded("controllable");
        backpackedLoaded = ModList.get().isLoaded("backpacked");
        playerReviveLoaded = ModList.get().isLoaded("playerrevive");

    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() ->
        {
            ModRecipeTypes.init();
            ModSyncedDataKeys.register();
            ModOreFeatures.registerOreFeatures();
            CraftingHelper.register(new ResourceLocation(Reference.MOD_ID, "workbench_ingredient"), ScrapWorkbenchIngredient.Serializer.INSTANCE);
            ProjectileManager.getInstance().registerFactory(ModItems.RIFLE_AMMO.get(), (worldIn, entity, weapon, item, modifiedGun) -> new ProjectileEntity(ModEntities.PROJECTILE.get(), worldIn, entity, weapon, item, modifiedGun));
            ProjectileManager.getInstance().registerFactory(ModItems.PISTOL_AMMO.get(), (worldIn, entity, weapon, item, modifiedGun) -> new ProjectileEntity(ModEntities.PROJECTILE.get(), worldIn, entity, weapon, item, modifiedGun));
            ProjectileManager.getInstance().registerFactory(ModItems.HANDMADE_SHELL.get(), (worldIn, entity, weapon, item, modifiedGun) -> new ProjectileEntity(ModEntities.PROJECTILE.get(), worldIn, entity, weapon, item, modifiedGun));
            ProjectileManager.getInstance().registerFactory(ModItems.SHOTGUN_SHELL.get(), (worldIn, entity, weapon, item, modifiedGun) -> new ProjectileEntity(ModEntities.PROJECTILE.get(), worldIn, entity, weapon, item, modifiedGun));
            ProjectileManager.getInstance().registerFactory(ModItems.SPECTRE_AMMO.get(), (worldIn, entity, weapon, item, modifiedGun) -> new SpectreProjectileEntity(ModEntities.SPECTRE_PROJECTILE.get(), worldIn, entity, weapon, item, modifiedGun));
            ProjectileManager.getInstance().registerFactory(ModItems.WATER_BOMB.get(), (worldIn, entity, weapon, item, modifiedGun) -> new WhirpoolEntity(ModEntities.WATER_BOMB.get(), worldIn, entity, weapon, item, modifiedGun));
            ProjectileManager.getInstance().registerFactory(ModItems.POCKET_BUBBLE.get(), (worldIn, entity, weapon, item, modifiedGun) -> new PocketBubbleEntity(ModEntities.POCKET_BUBBLE.get(), worldIn, entity, weapon, item, modifiedGun));
            ProjectileManager.getInstance().registerFactory(ModItems.GRENADE.get(), (worldIn, entity, weapon, item, modifiedGun) -> new GrenadeEntity(ModEntities.GRENADE.get(), worldIn, entity, weapon, item, modifiedGun));
            //ProjectileManager.getInstance().registerFactory(ModItems.MISSILE.get(), (worldIn, entity, weapon, item, modifiedGun) -> new MissileEntity(ModEntities.MISSILE.get(), worldIn, entity, weapon, item, modifiedGun));
            PacketHandler.init();
            if (Config.COMMON.gameplay.improvedHitboxes.get()) {
                MinecraftForge.EVENT_BUS.register(new BoundingBoxManager());
            }
        });
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.HEALING_TALISMAN.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.GHOUL.get(), GhoulRenderer::new);
        EntityRenderers.register(ModEntities.BOO.get(), BooRenderer::new);
        EntityRenderers.register(ModEntities.SPLASH.get(), SplashRenderer::new);
        EntityRenderers.register(ModEntities.BUBBLE.get(), BubbleRenderer::new);
        event.enqueueWork(ClientHandler::setup);
    }

    private void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        BlockTagGen blockTagGen = new BlockTagGen(generator, existingFileHelper);
        EntityTagGen entityTagGen = new EntityTagGen(generator, existingFileHelper);
        generator.addProvider(blockTagGen);
        generator.addProvider(entityTagGen);
        generator.addProvider(new ItemTagGen(generator, blockTagGen, existingFileHelper));
        generator.addProvider(new GunGen(generator));
    }

    public static boolean isDebugging() {
        return false; //!FMLEnvironment.production;
    }
}
