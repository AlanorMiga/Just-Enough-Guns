package ttv.alanorMiga.jeg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.alanorMiga.jeg.Reference;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum SpecialModels {

    SCAR_L("gun/scar_l"),
    SCAR_L_MAIN("scar_l/main"),
    SCAR_L_IRON_SIGHTS("scar_l/iron_sights"),
    SCAR_L_FOLDED_IRON_SIGHTS("scar_l/folded_iron_sights"),
    SCAR_L_EJECTOR("scar_l/ejector"),
    SCAR_L_STOCK_TACTICAL("scar_l/stock_tactical"),
    SCAR_L_STOCK_LIGHT("scar_l/stock_light"),
    SCAR_L_STOCK_WEIGHTED("scar_l/stock_weighted"),
    SCAR_L_SILENCER("scar_l/silencer"),
    SCAR_L_VERTICAL_GRIP("scar_l/vertical_grip"),
    SCAR_L_LIGHT_GRIP("scar_l/light_grip"),
    SCAR_L_ANGLED_GRIP("scar_l/angled_grip"),

    ASSAULT_RIFLE("gun/assault_rifle"),
    ASSAULT_RIFLE_MAIN("assault_rifle/main"),
    ASSAULT_RIFLE_EJECTOR("assault_rifle/ejector"),
    ASSAULT_RIFLE_STOCK_MAKESHIFT("assault_rifle/stock_makeshift"),
    ASSAULT_RIFLE_SILENCER("assault_rifle/silencer"),

    REVOLVER("gun/revolver"),
    REVOLVER_MAIN("revolver/main"),
    REVOLVER_CHAMBER("revolver/chamber"),
    REVOLVER_BOLT("revolver/bolt"),
    REVOLVER_STOCK_MAKESHIFT("revolver/stock_makeshift"),
    REVOLVER_SILENCER("revolver/silencer"),

    WATERPIPE_SHOTGUN("gun/waterpipe_shotgun"),

    SEMI_AUTO_RIFLE("gun/semi_auto_rifle"),
    SEMI_AUTO_RIFLE_MAIN("semi_auto_rifle/main"),
    SEMI_AUTO_RIFLE_EJECTOR("semi_auto_rifle/ejector"),
    SEMI_AUTO_RIFLE_STOCK_MAKESHIFT("semi_auto_rifle/stock_makeshift"),
    SEMI_AUTO_RIFLE_SILENCER("semi_auto_rifle/silencer"),

    HK_G36("gun/hk_g36"),
    HK_G36_MAIN("hk_g36/main"),
    HK_G36_IRON_SIGHTS("hk_g36/iron_sights"),
    HK_G36_EJECTOR("hk_g36/ejector"),
    HK_G36_STOCK_TACTICAL("hk_g36/stock_tactical"),
    HK_G36_STOCK_LIGHT("hk_g36/stock_light"),
    HK_G36_STOCK_WEIGHTED("hk_g36/stock_weighted"),

    FLAME("flame");

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
    SpecialModels(String modelName)
    {
        this.modelLocation = new ResourceLocation(Reference.MOD_ID, "special/" + modelName);
    }

    /**
     * Gets the model
     *
     * @return isolated model
     */
    public BakedModel getModel()
    {
        if(this.cachedModel == null)
        {
            this.cachedModel = Minecraft.getInstance().getModelManager().getModel(this.modelLocation);
        }
        return this.cachedModel;
    }

    /**
     * Registers the special models into the Forge Model Bakery. This is only called once on the
     * load of the game.
     */
    @SubscribeEvent
    public static void registerAdditional(ModelEvent.RegisterAdditional event)
    {
        for(SpecialModels model : values())
        {
            event.register(model.modelLocation);
        }
    }

    /**
     * Clears the cached BakedModel since it's been rebuilt. This is needed since the models may
     * have changed when a resource pack was applied, or if resources are reloaded.
     */
    @SubscribeEvent
    public static void onBake(ModelEvent.BakingCompleted event)
    {
        for(SpecialModels model : values())
        {
            model.cachedModel = null;
        }
    }
}