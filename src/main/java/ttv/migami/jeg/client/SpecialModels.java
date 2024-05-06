package ttv.migami.jeg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.jeg.Reference;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum SpecialModels {

    COMBAT_RIFLE("gun/combat_rifle"),
    COMBAT_RIFLE_MAIN("combat_rifle/main"),
    COMBAT_RIFLE_IRON_SIGHTS("combat_rifle/iron_sights"),
    COMBAT_RIFLE_FOLDED_IRON_SIGHTS("combat_rifle/folded_iron_sights"),
    COMBAT_RIFLE_EJECTOR("combat_rifle/ejector"),
    COMBAT_RIFLE_STOCK_TACTICAL("combat_rifle/stock_tactical"),
    COMBAT_RIFLE_STOCK_LIGHT("combat_rifle/stock_light"),
    COMBAT_RIFLE_STOCK_WEIGHTED("combat_rifle/stock_weighted"),
    COMBAT_RIFLE_SILENCER("combat_rifle/silencer"),
    COMBAT_RIFLE_VERTICAL_GRIP("combat_rifle/vertical_grip"),
    COMBAT_RIFLE_LIGHT_GRIP("combat_rifle/light_grip"),
    COMBAT_RIFLE_ANGLED_GRIP("combat_rifle/angled_grip"),

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

    BURST_RIFLE("gun/burst_rifle"),
    BURST_RIFLE_MAIN("burst_rifle/main"),
    BURST_RIFLE_IRON_SIGHTS("burst_rifle/iron_sights"),
    BURST_RIFLE_EJECTOR("burst_rifle/ejector"),
    BURST_RIFLE_STOCK_TACTICAL("burst_rifle/stock_tactical"),
    BURST_RIFLE_STOCK_LIGHT("burst_rifle/stock_light"),
    BURST_RIFLE_STOCK_WEIGHTED("burst_rifle/stock_weighted"),

    PUMP_SHOTGUN("gun/pump_shotgun"),
    PUMP_SHOTGUN_MAIN("pump_shotgun/main"),
    PUMP_SHOTGUN_PUMPY("pump_shotgun/pumpy"),
    PUMP_SHOTGUN_STOCK_MAKESHIFT("pump_shotgun/stock_makeshift"),
    PUMP_SHOTGUN_SILENCER("pump_shotgun/silencer"),
    PUMP_SHOTGUN_GRIP_LIGHT("pump_shotgun/grip_light"),
    PUMP_SHOTGUN_GRIP_VERTICAL("pump_shotgun/grip_vertical"),
    PUMP_SHOTGUN_GRIP_ANGLED("pump_shotgun/grip_angled"),

    BOLT_ACTION_RIFLE("gun/bolt_action_rifle"),
    BOLT_ACTION_RIFLE_MAIN("bolt_action_rifle/main"),
    BOLT_ACTION_RIFLE_BOLT("bolt_action_rifle/bolt"),

    CUSTOM_SMG("gun/custom_smg"),
    CUSTOM_SMG_MAIN("custom_smg/main"),
    CUSTOM_SMG_EJECTOR("custom_smg/ejector"),
    CUSTOM_SMG_STOCK_MAKESHIFT("custom_smg/stock_makeshift"),
    CUSTOM_SMG_SILENCER("custom_smg/silencer"),

    BLOSSOM_RIFLE("gun/blossom_rifle"),
    BLOSSOM_RIFLE_MAIN("blossom_rifle/main"),
    BLOSSOM_RIFLE_IRON_SIGHT("blossom_rifle/iron_sight"),
    BLOSSOM_RIFLE_CHAMBER("blossom_rifle/chamber"),
    BLOSSOM_RIFLE_STOCK_LIGHT("blossom_rifle/stock_light"),
    BLOSSOM_RIFLE_STOCK_TACTICAL("blossom_rifle/stock_tactical"),
    BLOSSOM_RIFLE_STOCK_WEIGHTED("blossom_rifle/stock_weighted"),
    BLOSSOM_RIFLE_SILENCER("blossom_rifle/silencer"),

    DOUBLE_BARREL_SHOTGUN("gun/double_barrel_shotgun"),
    DOUBLE_BARREL_SHOTGUN_MAIN("double_barrel_shotgun/main"),
    DOUBLE_BARREL_SHOTGUN_CHAMBER("double_barrel_shotgun/chamber"),
    DOUBLE_BARREL_SHOTGUN_STOCK_MAKESHIFT("double_barrel_shotgun/stock_makeshift"),

    HOLY_SHOTGUN("gun/holy_shotgun"),
    HOLY_SHOTGUN_MAIN("holy_shotgun/main"),
    HOLY_SHOTGUN_PUMPY("holy_shotgun/pumpy"),
    HOLY_SHOTGUN_SILENCER("holy_shotgun/silencer"),
    HOLY_SHOTGUN_GRIP_LIGHT("holy_shotgun/grip_light"),
    HOLY_SHOTGUN_GRIP_VERTICAL("holy_shotgun/grip_vertical"),
    HOLY_SHOTGUN_GRIP_ANGLED("holy_shotgun/grip_angled"),

    TYPHOONEE("gun/typhoonee"),
    TYPHOONEE_MAIN("typhoonee/main"),

    ATLANTEAN_SPEAR("gun/atlantean_spear"),
    ATLANTEAN_SPEAR_MAIN("atlantean_spear/main"),

    BUBBLE_CANNON("gun/bubble_cannon"),
    BUBBLE_CANNON_MAIN("bubble_cannon/main"),

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
