package ttv.migami.jeg.init;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.blockentity.GunmetalWorkbenchBlockEntity;
import ttv.migami.jeg.blockentity.GunniteWorkbenchBlockEntity;
import ttv.migami.jeg.blockentity.ScrapWorkbenchBlockEntity;
import ttv.migami.jeg.common.container.*;

/**
 * Author: MrCrayfish
 */
public class ModContainers {
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);

    public static final RegistryObject<MenuType<ScrapWorkbenchContainer>> SCRAP_WORKBENCH = register("scrap_workbench", (IContainerFactory<ScrapWorkbenchContainer>) (windowId, playerInventory, data) -> {
        ScrapWorkbenchBlockEntity scrap_workbench = (ScrapWorkbenchBlockEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new ScrapWorkbenchContainer(windowId, playerInventory, scrap_workbench);
    });
    public static final RegistryObject<MenuType<GunmetalWorkbenchContainer>> GUNMETAL_WORKBENCH = register("gunmetal_workbench", (IContainerFactory<GunmetalWorkbenchContainer>) (windowId, playerInventory, data) -> {
        GunmetalWorkbenchBlockEntity gunmetal_workbench = (GunmetalWorkbenchBlockEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new GunmetalWorkbenchContainer(windowId, playerInventory, gunmetal_workbench);
    });
    public static final RegistryObject<MenuType<GunniteWorkbenchContainer>> GUNNITE_WORKBENCH = register("gunnite_workbench", (IContainerFactory<GunniteWorkbenchContainer>) (windowId, playerInventory, data) -> {
        GunniteWorkbenchBlockEntity gunnite_workbench = (GunniteWorkbenchBlockEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new GunniteWorkbenchContainer(windowId, playerInventory, gunnite_workbench);
    });

    public static final RegistryObject<MenuType<AttachmentContainer>> ATTACHMENTS = register("attachments", AttachmentContainer::new);

    public static final RegistryObject<MenuType<RecyclerMenu>> RECYCLER = register("recycler", RecyclerMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, MenuType.MenuSupplier<T> factory) {
        return REGISTER.register(id, () -> new MenuType<>(factory));
    }
}
