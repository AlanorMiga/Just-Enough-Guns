package ttv.alanorMiga.jeg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.alanorMiga.jeg.Reference;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum SpecialModels {

    SCAR_L("gun/scar_l"),
    SCAR_L_MAIN("scar_l/scar_l_main"),
    SCAR_L_IRON_SIGHTS("scar_l/scar_l_iron_sights"),
    SCAR_L_FOLDED_IRON_SIGHTS("scar_l/scar_l_folded_iron_sights"),
    SCAR_L_EJECTOR("scar_l/scar_l_ejector"),
    SCAR_L_STOCK_TACTICAL("scar_l/scar_l_stock_tactical"),
    SCAR_L_STOCK_LIGHT("scar_l/scar_l_stock_light"),
    SCAR_L_STOCK_WEIGHTED("scar_l/scar_l_stock_weighted"),
    SCAR_L_SILENCER("scar_l/scar_l_silencer"),
    SCAR_L_VERTICAL_GRIP("scar_l/scar_l_vertical_grip"),
    SCAR_L_LIGHT_GRIP("scar_l/scar_l_light_grip"),
    SCAR_L_ANGLED_GRIP("scar_l/scar_l_angled_grip"),

    ASSAULT_RIFLE("gun/assault_rifle"),
    ASSAULT_RIFLE_MAIN("assault_rifle_main"),
    ASSAULT_RIFLE_EJECTOR("assault_rifle_ejector"),
    ASSAULT_RIFLE_STOCK_MAKESHIFT("assault_rifle_stock_makeshift"),

    REVOLVER("gun/revolver"),
    REVOLVER_MAIN("revolver_main"),
    REVOLVER_CHAMBER("revolver_chamber"),
    REVOLVER_BOLT("revolver_bolt"),

    WATERPIPE_SHOTGUN("gun/waterpipe_shotgun"),

    SEMI_AUTO_RIFLE("gun/semi_auto_rifle"),
    SEMI_AUTO_RIFLE_MAIN("semi_auto_rifle_main"),
    SEMI_AUTO_RIFLE_EJECTOR("semi_auto_rifle_ejector"),
    SEMI_AUTO_RIFLE_STOCK_MAKESHIFT("semi_auto_rifle_stock_makeshift"),

    HK_G36("gun/hk_g36"),

    BAZOOKA("gun/bazooka"),
    FLAME("flame"),
    GRENADE_LAUNCHER_BASE("grenade_launcher_base"),
    GRENADE_LAUNCHER_CYLINDER("grenade_launcher_cylinder");

    /**
     * The location of an item model in the [MOD_ID]/models/special/[NAME] folder
     */
    private final ResourceLocation modelLocation;

    /**
     * Cached model
     */
    private BakedModel cachedModel;

    /**
     * Sets the model's location
     *
     * @param modelName name of the model file
     */
    SpecialModels(String modelName) {
        this.modelLocation = new ResourceLocation(Reference.MOD_ID, "special/" + modelName);
    }

    /**
     * Gets the model
     *
     * @return isolated model
     */
    public BakedModel getModel() {
        if (this.cachedModel == null) {
            this.cachedModel = Minecraft.getInstance().getModelManager().getModel(this.modelLocation);
        }
        return this.cachedModel;
    }

    /**
     * Registers the special models into the Forge Model Bakery. This is only called once on the
     * load of the game.
     */
    @SubscribeEvent
    public static void register(ModelRegistryEvent event) {
        for (SpecialModels model : values()) {
            ForgeModelBakery.addSpecialModel(model.modelLocation);
        }
    }

    /**
     * Clears the cached BakedModel since it's been rebuilt. This is needed since the models may
     * have changed when a resource pack was applied, or if resources are reloaded.
     */
    @SubscribeEvent
    public static void onBake(ModelBakeEvent event) {
        for (SpecialModels model : values()) {
            model.cachedModel = null;
        }
    }
}
