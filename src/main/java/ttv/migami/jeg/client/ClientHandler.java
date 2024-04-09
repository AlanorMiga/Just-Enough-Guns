package ttv.migami.jeg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;
import ttv.migami.jeg.JustEnoughGuns;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.client.handler.*;
import ttv.migami.jeg.client.render.gun.ModelOverrides;
import ttv.migami.jeg.client.render.gun.model.*;
import ttv.migami.jeg.client.screen.*;
import ttv.migami.jeg.client.util.PropertyHelper;
import ttv.migami.jeg.debug.IEditorMenu;
import ttv.migami.jeg.debug.client.screen.EditorScreen;
import ttv.migami.jeg.enchantment.EnchantmentTypes;
import ttv.migami.jeg.init.ModBlocks;
import ttv.migami.jeg.init.ModContainers;
import ttv.migami.jeg.init.ModItems;
import ttv.migami.jeg.item.GunItem;
import ttv.migami.jeg.item.IColored;
import ttv.migami.jeg.item.attachment.IAttachment;
import ttv.migami.jeg.network.PacketHandler;
import ttv.migami.jeg.network.message.C2SMessageAttachments;

import java.lang.reflect.Field;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public class ClientHandler {
    private static Field mouseOptionsField;

    public static void setup() {
        MinecraftForge.EVENT_BUS.register(AimingHandler.get());
        MinecraftForge.EVENT_BUS.register(BulletTrailRenderingHandler.get());
        MinecraftForge.EVENT_BUS.register(CrosshairHandler.get());
        MinecraftForge.EVENT_BUS.register(GunRenderingHandler.get());
        MinecraftForge.EVENT_BUS.register(RecoilHandler.get());
        MinecraftForge.EVENT_BUS.register(ReloadHandler.get());
        MinecraftForge.EVENT_BUS.register(ShootingHandler.get());
        MinecraftForge.EVENT_BUS.register(SoundHandler.get());
        MinecraftForge.EVENT_BUS.register(new PlayerModelHandler());

        /* Only register controller events if Controllable is loaded otherwise it will crash */
        if (JustEnoughGuns.controllableLoaded) {
            ControllerHandler.init();
            GunButtonBindings.register();
        }

        setupRenderLayers();
        registerColors();
        registerModelOverrides();
        registerScreenFactories();
    }

    private static void setupRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SCRAP_WORKBENCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GUNMETAL_WORKBENCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GUNNITE_WORKBENCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RECYCLER.get(), RenderType.cutout());
    }

    private static void registerColors() {
        ItemColor color = (stack, index) ->
        {
            if (!IColored.isDyeable(stack)) {
                return -1;
            }
            if (index == 0 && stack.hasTag() && stack.getTag().contains("Color", Tag.TAG_INT)) {
                return stack.getTag().getInt("Color");
            }
            if (index == 0 && stack.getItem() instanceof IAttachment) {
                ItemStack renderingWeapon = GunRenderingHandler.get().getRenderingWeapon();
                if (renderingWeapon != null) {
                    return Minecraft.getInstance().getItemColors().getColor(renderingWeapon, index);
                }
            }
            if (index == 2) // Reticle colour
            {
                return PropertyHelper.getReticleColor(stack);
            }
            return -1;
        };
        ForgeRegistries.ITEMS.forEach(item ->
        {
            if (item instanceof IColored) {
                Minecraft.getInstance().getItemColors().register(color, item);
            }
        });
    }

    private static void registerModelOverrides() {
        /* Weapons */
        //ModelOverrides.register(ModItems.BAZOOKA.get(), new SimpleModel(SpecialModels.BAZOOKA::getModel));
        //ModelOverrides.register(ModItems.GRENADE_LAUNCHER.get(), new GrenadeLauncherModel());

        ModelOverrides.register(ModItems.COMBAT_RIFLE.get(), new CombatRifleModel());
        ModelOverrides.register(ModItems.ASSAULT_RIFLE.get(), new AssaultRifleModel());
        ModelOverrides.register(ModItems.REVOLVER.get(), new RevolverModel());
        ModelOverrides.register(ModItems.WATERPIPE_SHOTGUN.get(), new WaterpipeShotgunModel());
        ModelOverrides.register(ModItems.SEMI_AUTO_RIFLE.get(), new SemiAutoRifleModel());
        ModelOverrides.register(ModItems.BURST_RIFLE.get(), new BurstRifleModel());
        ModelOverrides.register(ModItems.PUMP_SHOTGUN.get(), new PumpShotgunModel());
        ModelOverrides.register(ModItems.BOLT_ACTION_RIFLE.get(), new BoltActionRifleModel());
        ModelOverrides.register(ModItems.CUSTOM_SMG.get(), new CustomSMGModel());
        ModelOverrides.register(ModItems.BLOSSOM_RIFLE.get(), new BlossomRifleModel());
        ModelOverrides.register(ModItems.DOUBLE_BARREL_SHOTGUN.get(), new DoubleBarrelShotgunModel());
        ModelOverrides.register(ModItems.HOLY_SHOTGUN.get(), new HolyShotgunModel());
    }

    private static void registerScreenFactories() {
        MenuScreens.register(ModContainers.GUNNITE_WORKBENCH.get(), GunniteWorkbenchScreen::new);
        MenuScreens.register(ModContainers.SCRAP_WORKBENCH.get(), ScrapWorkbenchScreen::new);
        MenuScreens.register(ModContainers.GUNMETAL_WORKBENCH.get(), GunmetalWorkbenchScreen::new);
        MenuScreens.register(ModContainers.ATTACHMENTS.get(), AttachmentScreen::new);
        MenuScreens.register(ModContainers.RECYCLER.get(), RecyclerScreen::new);
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof MouseSettingsScreen) {
            MouseSettingsScreen screen = (MouseSettingsScreen) event.getScreen();
            if (mouseOptionsField == null) {
                mouseOptionsField = ObfuscationReflectionHelper.findField(MouseSettingsScreen.class, "f_96218_");
                mouseOptionsField.setAccessible(true);
            }
            try {
                OptionsList list = (OptionsList) mouseOptionsField.get(screen);
                //list.addSmall(GunOptions.ADS_SENSITIVITY, GunOptions.CROSSHAIR);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.screen == null && event.getAction() == GLFW.GLFW_PRESS) {
            if (KeyBinds.KEY_ATTACHMENTS.isDown()) {
                PacketHandler.getPlayChannel().sendToServer(new C2SMessageAttachments());
            }
            /*else if(event.getKey() == GLFW.GLFW_KEY_KP_9)
            {
                mc.setScreen(new EditorScreen(null, new Debug.Menu()));
            }*/
        }
    }

    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) manager -> {
            PropertyHelper.resetCache();
        });
    }

    public static void registerAdditional(ModelEvent.RegisterAdditional event)
    {
        event.register(new ResourceLocation(Reference.MOD_ID, "special/test"));
    }

    public static void onRegisterCreativeTab(IEventBus bus)
    {
        DeferredRegister<CreativeModeTab> register = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);
        CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.translatable("itemGroup." + Reference.MOD_ID));
            builder.icon(() -> {
                ItemStack stack = new ItemStack(ModItems.ASSAULT_RIFLE.get());
                stack.getOrCreateTag().putBoolean("IgnoreAmmo", true);
                return stack;
            });
            builder.displayItems((flags, output) ->
            {
                ModItems.REGISTER.getEntries().forEach(registryObject ->
                {
                    if(registryObject.get() instanceof GunItem item)
                    {
                        ItemStack stack = new ItemStack(item);
                        stack.getOrCreateTag().putInt("AmmoCount", item.getGun().getReloads().getMaxAmmo());
                        output.accept(stack);
                        return;
                    }
                    output.accept(registryObject.get());
                });
                CustomGunManager.fill(output);
                for(Enchantment enchantment : ForgeRegistries.ENCHANTMENTS)
                {
                    if(enchantment.category == EnchantmentTypes.GUN || enchantment.category == EnchantmentTypes.SEMI_AUTO_GUN)
                    {
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                    }
                }
            });
        register.register("creative_tab", builder::build);
        register.register(bus);
    }

    public static Screen createEditorScreen(IEditorMenu menu) {
        return new EditorScreen(Minecraft.getInstance().screen, menu);
    }

    /* Uncomment for debugging headshot hit boxes */

    /*@SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void onRenderLiving(RenderLivingEvent.Post event)
    {
        LivingEntity entity = event.getEntity();
        IHeadshotBox<LivingEntity> headshotBox = (IHeadshotBox<LivingEntity>) BoundingBoxManager.getHeadshotBoxes(entity.getType());
        if(headshotBox != null)
        {
            AxisAlignedBB box = headshotBox.getHeadshotBox(entity);
            if(box != null)
            {
                WorldRenderer.drawBoundingBox(event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.getLines()), box, 1.0F, 1.0F, 0.0F, 1.0F);

                AxisAlignedBB boundingBox = entity.getBoundingBox().offset(entity.getPositionVec().inverse());
                boundingBox = boundingBox.grow(Config.COMMON.gameplay.growBoundingBoxAmount.get(), 0, Config.COMMON.gameplay.growBoundingBoxAmount.get());
                WorldRenderer.drawBoundingBox(event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.getLines()), boundingBox, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }*/
}
