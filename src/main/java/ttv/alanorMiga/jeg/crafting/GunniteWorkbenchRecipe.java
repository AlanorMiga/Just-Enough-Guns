package ttv.alanorMiga.jeg.crafting;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import ttv.alanorMiga.jeg.blockentity.GunniteWorkbenchBlockEntity;
import ttv.alanorMiga.jeg.init.ModRecipeSerializers;
import ttv.alanorMiga.jeg.init.ModRecipeTypes;
import ttv.alanorMiga.jeg.util.InventoryUtil;

/**
 * Author: MrCrayfish
 */
public class GunniteWorkbenchRecipe implements Recipe<GunniteWorkbenchBlockEntity>
{
    private final ResourceLocation id;
    private final ItemStack item;
    private final ImmutableList<ScrapWorkbenchIngredient> materials;

    public GunniteWorkbenchRecipe(ResourceLocation id, ItemStack item, ImmutableList<ScrapWorkbenchIngredient> materials)
    {
        this.id = id;
        this.item = item;
        this.materials = materials;
    }

    public ItemStack getItem()
    {
        return this.item.copy();
    }

    public ImmutableList<ScrapWorkbenchIngredient> getMaterials()
    {
        return this.materials;
    }

    @Override
    public boolean matches(GunniteWorkbenchBlockEntity inv, Level worldIn)
    {
        return false;
    }

    @Override
    public ItemStack assemble(GunniteWorkbenchBlockEntity entity, RegistryAccess access)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access)
    {
        return this.item.copy();
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.GUNNITE_WORKBENCH.get();
    }

    @Override
    public net.minecraft.world.item.crafting.RecipeType<?> getType()  { return ModRecipeTypes.GUNNITE_WORKBENCH.get(); }

    public boolean hasMaterials(Player player)
    {
        for(ScrapWorkbenchIngredient ingredient : this.getMaterials())
        {
            if(!InventoryUtil.hasWorkstationIngredient(player, ingredient))
            {
                return false;
            }
        }
        return true;
    }

    public void consumeMaterials(Player player)
    {
        for(ScrapWorkbenchIngredient ingredient : this.getMaterials())
        {
            InventoryUtil.removeWorkstationIngredient(player, ingredient);
        }
    }
}
