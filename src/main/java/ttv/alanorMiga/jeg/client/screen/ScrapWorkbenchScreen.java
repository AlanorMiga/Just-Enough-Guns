package ttv.alanorMiga.jeg.client.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;
import ttv.alanorMiga.jeg.blockentity.ScrapWorkbenchBlockEntity;
import ttv.alanorMiga.jeg.client.util.RenderUtil;
import ttv.alanorMiga.jeg.common.NetworkGunManager;
import ttv.alanorMiga.jeg.common.container.ScrapWorkbenchContainer;
import ttv.alanorMiga.jeg.crafting.ScrapWorkbenchRecipe;
import ttv.alanorMiga.jeg.crafting.ScrapWorkbenchRecipes;
import ttv.alanorMiga.jeg.crafting.ScrapWorkbenchIngredient;
import ttv.alanorMiga.jeg.init.ModBlocks;
import ttv.alanorMiga.jeg.init.ModItems;
import ttv.alanorMiga.jeg.item.GunItem;
import ttv.alanorMiga.jeg.item.IAmmo;
import ttv.alanorMiga.jeg.item.IColored;
import ttv.alanorMiga.jeg.item.attachment.IAttachment;
import ttv.alanorMiga.jeg.network.PacketHandler;
import ttv.alanorMiga.jeg.network.message.C2SMessageCraft;
import ttv.alanorMiga.jeg.util.InventoryUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class ScrapWorkbenchScreen extends AbstractContainerScreen<ScrapWorkbenchContainer>
{
    private static final ResourceLocation GUI_BASE = new ResourceLocation("jeg:textures/gui/workbench.png");
    private static boolean showRemaining = false;

    private Tab currentTab;
    private final List<Tab> tabs = new ArrayList<>();
    private final List<MaterialItem> materials;
    private List<MaterialItem> filteredMaterials;
    private final Inventory playerInventory;
    private final ScrapWorkbenchBlockEntity workbench;
    private Button btnCraft;
    private CheckBox checkBoxMaterials;
    private ItemStack displayStack;

    public ScrapWorkbenchScreen(ScrapWorkbenchContainer container, Inventory playerInventory, Component title)
    {

        super(container, playerInventory, title);
        this.playerInventory = playerInventory;
        this.workbench = container.getWorkbench();
        this.imageWidth = 275;
        this.imageHeight = 184;
        this.materials = new ArrayList<>();
        this.createTabs(ScrapWorkbenchRecipes.getAll(playerInventory.player.level()));
        if(!this.tabs.isEmpty())
        {
            this.imageHeight += 28;
        }
    }

    private void createTabs(NonNullList<ScrapWorkbenchRecipe> recipes)
    {
        List<ScrapWorkbenchRecipe> weapons = new ArrayList<>();
        List<ScrapWorkbenchRecipe> attachments = new ArrayList<>();
        List<ScrapWorkbenchRecipe> ammo = new ArrayList<>();
        List<ScrapWorkbenchRecipe> stations = new ArrayList<>();
        List<ScrapWorkbenchRecipe> misc = new ArrayList<>();

        for(ScrapWorkbenchRecipe recipe : recipes)
        {
            ItemStack output = recipe.getItem();
            if(output.getItem() instanceof GunItem)
            {
                weapons.add(recipe);
            }
            else if(output.getItem() instanceof IAttachment)
            {
                attachments.add(recipe);
            }
            else if(this.isAmmo(output))
            {
                ammo.add(recipe);
            }
            else if(output.getItem() instanceof BlockItem)
            {
                stations.add(recipe);
            }
            else
            {
                misc.add(recipe);
            }
        }

        if(!weapons.isEmpty())
        {
            ItemStack icon = new ItemStack(ModItems.ASSAULT_RIFLE.get());
            icon.getOrCreateTag().putInt("AmmoCount", ModItems.ASSAULT_RIFLE.get().getGun().getReloads().getMaxAmmo());
            this.tabs.add(new Tab(icon, "weapons", weapons));
        }

        if(!attachments.isEmpty())
        {
            this.tabs.add(new Tab(new ItemStack(ModItems.HOLOGRAPHIC_SIGHT.get()), "attachments", attachments));
        }

        if(!ammo.isEmpty())
        {
            this.tabs.add(new Tab(new ItemStack(ModItems.RIFLE_AMMO.get()), "ammo", ammo));
        }

        if(!stations.isEmpty())
        {
            ItemStack icon = new ItemStack(ModBlocks.SCRAP_WORKBENCH.get());
            this.tabs.add(new Tab(icon, "stations", stations));
        }

        if(!misc.isEmpty())
        {
            this.tabs.add(new Tab(new ItemStack(ModItems.SCRAP.get()), "misc", misc));
        }

        if(!this.tabs.isEmpty())
        {
            this.currentTab = this.tabs.get(0);
        }
    }

    private boolean isAmmo(ItemStack stack)
    {
        if(stack.getItem() instanceof IAmmo)
        {
            return true;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        Objects.requireNonNull(id);
        for(GunItem gunItem : NetworkGunManager.getClientRegisteredGuns())
        {
            if(id.equals(gunItem.getModifiedGun(stack).getProjectile().getItem()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init()
    {
        super.init();
        if(!this.tabs.isEmpty())
        {
            this.topPos += 28;
        }
        this.addRenderableWidget(Button.builder(Component.literal("<"), button ->
        {
            int index = this.currentTab.getCurrentIndex();
            if(index - 1 < 0)
            {
                this.loadItem(this.currentTab.getRecipes().size() - 1);
            }
            else
            {
                this.loadItem(index - 1);
            }
        }).pos(this.leftPos + 9, this.topPos + 18).size(15, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal(">"), button ->
        {
            int index = this.currentTab.getCurrentIndex();
            if(index + 1 >= this.currentTab.getRecipes().size())
            {
                this.loadItem(0);
            }
            else
            {
                this.loadItem(index + 1);
            }
        }).pos(this.leftPos + 153, this.topPos + 18).size(15, 20).build());
        this.btnCraft = this.addRenderableWidget(Button.builder(Component.translatable("gui.jeg.workbench.assemble"), button ->
        {
            int index = this.currentTab.getCurrentIndex();
            ScrapWorkbenchRecipe recipe = this.currentTab.getRecipes().get(index);
            ResourceLocation registryName = recipe.getId();
            PacketHandler.getPlayChannel().sendToServer(new C2SMessageCraft(registryName, this.workbench.getBlockPos()));
        }).pos(this.leftPos + 195, this.topPos + 16).size(74, 20).build());
        this.btnCraft.active = false;
        this.checkBoxMaterials = this.addRenderableWidget(new CheckBox(this.leftPos + 172, this.topPos + 51, Component.translatable("gui.jeg.workbench.show_remaining")));
        this.checkBoxMaterials.setToggled(ScrapWorkbenchScreen.showRemaining);
        this.loadItem(this.currentTab.getCurrentIndex());
    }

    @Override
    public void containerTick()
    {
        super.containerTick();

        for(MaterialItem material : this.materials)
        {
            material.tick();
        }

        boolean canCraft = true;
        for(MaterialItem material : this.materials)
        {
            if(!material.isEnabled())
            {
                canCraft = false;
                break;
            }
        }

        this.btnCraft.active = canCraft;
        this.updateColor();
    }

    private void updateColor()
    {
        if(this.currentTab != null)
        {
            ItemStack item = this.displayStack;
            if(IColored.isDyeable(item))
            {
                IColored colored = (IColored) item.getItem();
                if(!this.workbench.getItem(0).isEmpty())
                {
                    ItemStack dyeStack = this.workbench.getItem(0);
                    if(dyeStack.getItem() instanceof DyeItem)
                    {
                        DyeColor color = ((DyeItem) dyeStack.getItem()).getDyeColor();
                        float[] components = color.getTextureDiffuseColors();
                        int red = (int) (components[0] * 255F);
                        int green = (int) (components[1] * 255F);
                        int blue = (int) (components[2] * 255F);
                        colored.setColor(item, ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF)));
                    }
                    else
                    {
                        colored.removeColor(item);
                    }
                }
                else
                {
                    colored.removeColor(item);
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
    {
        boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);
        ScrapWorkbenchScreen.showRemaining = this.checkBoxMaterials.isToggled();

        for(int i = 0; i < this.tabs.size(); i++)
        {
            if(RenderUtil.isMouseWithin((int) mouseX, (int) mouseY, this.leftPos + 28 * i, this.topPos - 28, 28, 28))
            {
                this.currentTab = this.tabs.get(i);
                this.loadItem(this.currentTab.getCurrentIndex());
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }

        return result;
    }

    private void loadItem(int index)
    {
        ScrapWorkbenchRecipe recipe = this.currentTab.getRecipes().get(index);
        this.displayStack = recipe.getItem().copy();
        this.updateColor();

        this.materials.clear();

        List<ScrapWorkbenchIngredient> ingredients = recipe.getMaterials();
        if(ingredients != null)
        {
            for(ScrapWorkbenchIngredient ingredient : ingredients)
            {
                MaterialItem item = new MaterialItem(ingredient);
                item.updateEnabledState();
                this.materials.add(item);
            }

            this.currentTab.setCurrentIndex(index);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(pGuiGraphics, mouseX, mouseY);

        int startX = this.leftPos;
        int startY = this.topPos;

        for(int i = 0; i < this.tabs.size(); i++)
        {
            if(RenderUtil.isMouseWithin(mouseX, mouseY, startX + 28 * i, startY - 28, 28, 28))
            {
                this.setTooltipForNextRenderPass(Component.translatable(this.tabs.get(i).getTabKey()));
                this.renderTooltip(pGuiGraphics, mouseX, mouseY);
                return;
            }
        }

        for(int i = 0; i < this.filteredMaterials.size(); i++)
        {
            int itemX = startX + 172;
            int itemY = startY + i * 19 + 63;
            if(RenderUtil.isMouseWithin(mouseX, mouseY, itemX, itemY, 80, 19))
            {
                MaterialItem materialItem = this.filteredMaterials.get(i);
                if(materialItem != MaterialItem.EMPTY)
                {
                    pGuiGraphics.renderTooltip(this.font, materialItem.getDisplayStack(), mouseX, mouseY);
                    return;
                }
            }
        }

        if (RenderUtil.isMouseWithin(mouseX, mouseY, startX + 8, startY + 38, 160, 48))
        {
            pGuiGraphics.renderTooltip(this.font, this.displayStack, mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int mouseX, int mouseY) {
        int offset = this.tabs.isEmpty() ? 0 : 28;
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY - 28 + offset, Color.WHITE.getRGB());
        pGuiGraphics.drawString(this.font, this.playerInventory.getDisplayName(), this.inventoryLabelX, this.inventoryLabelY - 9 + offset, Color.WHITE.getRGB());
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float partialTicks, int mouseX, int mouseY) {
        /* Fixes partial ticks to use percentage from 0 to 1 */
        partialTicks = Minecraft.getInstance().getFrameTime();

        int startX = this.leftPos;
        int startY = this.topPos;

        RenderSystem.enableBlend();

        /* Draw unselected tabs */
        for (int i = 0; i < this.tabs.size(); i++) {
            Tab tab = this.tabs.get(i);
            if (tab != this.currentTab) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, GUI_BASE);
                pGuiGraphics.blit(GUI_BASE, startX + 28 * i, startY - 28, 80, 184, 28, 32);
                pGuiGraphics.renderItem(tab.getIcon(), startX + 28 * i + 6, startY - 28 + 8);
            }
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_BASE);
        pGuiGraphics.blit(GUI_BASE, startX, startY, 0, 0, 173, 184);
        pGuiGraphics.blit(GUI_BASE, startX + 173, startY, 78, 184, 173, 0, 1, 184, 256, 256);
        pGuiGraphics.blit(GUI_BASE, startX + 251, startY, 174, 0, 24, 184);
        pGuiGraphics.blit(GUI_BASE, startX + 172, startY + 16, 198, 0, 20, 20);

        /* Draw selected tab */
        if (this.currentTab != null) {
            int i = this.tabs.indexOf(this.currentTab);
            int u = i == 0 ? 80 : 108;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GUI_BASE);
            pGuiGraphics.blit(GUI_BASE, startX + 28 * i, startY - 28, u, 214, 28, 32);
            pGuiGraphics.renderItem(this.currentTab.getIcon(), startX + 28 * i + 6, startY - 28 + 8);
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_BASE);

        if (this.workbench.getItem(0).isEmpty()) {
            pGuiGraphics.blit(GUI_BASE, startX + 174, startY + 18, 165, 199, 16, 16);
        }

        ItemStack currentItem = this.displayStack;
        StringBuilder builder = new StringBuilder(currentItem.getHoverName().getString());
        if (currentItem.getCount() > 1) {
            builder.append(ChatFormatting.GOLD);
            builder.append(ChatFormatting.BOLD);
            builder.append(" x ");
            builder.append(currentItem.getCount());
        }
        pGuiGraphics.drawCenteredString(this.font, builder.toString(), startX + 88, startY + 22, Color.WHITE.getRGB());

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.scissor(startX + 8, startY + 17, 160, 70);

        PoseStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushPose();
        {
            modelViewStack.translate(startX + 88, startY + 60, 100);
            modelViewStack.scale(50F, -50F, 50F);
            modelViewStack.mulPose(Axis.XP.rotationDegrees(5F));
            modelViewStack.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTicks));
            RenderSystem.applyModelViewMatrix();
            MultiBufferSource.BufferSource buffer = this.minecraft.renderBuffers().bufferSource();
            Minecraft.getInstance().getItemRenderer().render(currentItem, ItemDisplayContext.FIXED, false, pGuiGraphics.pose(), buffer, 15728880, OverlayTexture.NO_OVERLAY, RenderUtil.getModel(currentItem));
            buffer.endBatch();
        }
        modelViewStack.popPose();
        RenderSystem.applyModelViewMatrix();

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        this.filteredMaterials = this.getMaterials();
        for (int i = 0; i < this.filteredMaterials.size(); i++) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GUI_BASE);

            MaterialItem materialItem = this.filteredMaterials.get(i);
            ItemStack stack = materialItem.getDisplayStack();
            if (!stack.isEmpty()) {
                Lighting.setupForFlatItems();
                if (materialItem.isEnabled()) {
                    pGuiGraphics.blit(GUI_BASE,startX + 172, startY + i * 19 + 63, 0, 184, 80, 19);
                } else {
                    pGuiGraphics.blit(GUI_BASE,startX + 172, startY + i * 19 + 63, 0, 222, 80, 19);
                }

                String name = stack.getHoverName().getString();
                if (this.font.width(name) > 55) {
                    name = this.font.plainSubstrByWidth(name, 50).trim() + "...";
                }
                pGuiGraphics.drawString(this.font, name, startX + 172 + 22, startY + i * 19 + 6 + 63, Color.WHITE.getRGB());

                pGuiGraphics.renderItem(stack, startX + 172 + 2, startY + i * 19 + 1 + 63);

                if (this.checkBoxMaterials.isToggled()) {
                    int count = InventoryUtil.getItemStackAmount(Minecraft.getInstance().player, stack);
                    stack = stack.copy();
                    stack.setCount(stack.getCount() - count);
                }

                pGuiGraphics.renderItemDecorations(this.font, stack, startX + 172 + 2, startY + i * 19 + 1 + 63);
            }
        }
    }


    private List<MaterialItem> getMaterials()
    {
        List<MaterialItem> materials = NonNullList.withSize(6, MaterialItem.EMPTY);
        List<MaterialItem> filteredMaterials = this.materials.stream().filter(materialItem -> this.checkBoxMaterials.isToggled() ? !materialItem.isEnabled() : materialItem != MaterialItem.EMPTY).collect(Collectors.toList());
        for(int i = 0; i < filteredMaterials.size() && i < materials.size(); i++)
        {
            materials.set(i, filteredMaterials.get(i));
        }
        return materials;
    }

    public List<Tab> getTabs()
    {
        return ImmutableList.copyOf(this.tabs);
    }

    public static class MaterialItem
    {
        public static final MaterialItem EMPTY = new MaterialItem();

        private long lastTime = System.currentTimeMillis();
        private int displayIndex;
        private boolean enabled = false;
        private ScrapWorkbenchIngredient ingredient;
        private final List<ItemStack> displayStacks = new ArrayList<>();

        private MaterialItem() {}

        private MaterialItem(ScrapWorkbenchIngredient ingredient)
        {
            this.ingredient = ingredient;
            Stream.of(ingredient.getItems()).forEach(stack -> {
                ItemStack displayStack = stack.copy();
                displayStack.setCount(ingredient.getCount());
                this.displayStacks.add(displayStack);
            });
        }

        public ScrapWorkbenchIngredient getIngredient()
        {
            return this.ingredient;
        }

        public void tick()
        {
            if(this.ingredient == null)
                return;

            this.updateEnabledState();
            long currentTime = System.currentTimeMillis();
            if(currentTime - this.lastTime >= 1000)
            {
                this.displayIndex = (this.displayIndex + 1) % this.displayStacks.size();
                this.lastTime = currentTime;
            }
        }

        public ItemStack getDisplayStack()
        {
            return this.ingredient != null ? this.displayStacks.get(this.displayIndex) : ItemStack.EMPTY;
        }

        public void updateEnabledState()
        {
            this.enabled = InventoryUtil.hasWorkstationIngredient(Minecraft.getInstance().player, this.ingredient);
        }

        public boolean isEnabled()
        {
            return this.ingredient == null || this.enabled;
        }
    }

    private static class Tab
    {
        private final ItemStack icon;
        private final String id;
        private final List<ScrapWorkbenchRecipe> items;
        private int currentIndex;

        public Tab(ItemStack icon, String id, List<ScrapWorkbenchRecipe> items)
        {
            this.icon = icon;
            this.id = id;
            this.items = items;
        }

        public ItemStack getIcon()
        {
            return this.icon;
        }

        public String getTabKey()
        {
            return "gui.jeg.workbench.tab." + this.id;
        }

        public void setCurrentIndex(int currentIndex)
        {
            this.currentIndex = currentIndex;
        }

        public int getCurrentIndex()
        {
            return this.currentIndex;
        }

        public List<ScrapWorkbenchRecipe> getRecipes()
        {
            return this.items;
        }
    }
}
