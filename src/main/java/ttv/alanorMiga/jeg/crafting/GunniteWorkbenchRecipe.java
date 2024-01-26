package ttv.alanorMiga.jeg.crafting;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import ttv.alanorMiga.jeg.blockentity.GunniteWorkbenchBlockEntity;
import ttv.alanorMiga.jeg.util.InventoryUtil;

/**
 * Author: MrCrayfish
 */
public class GunniteWorkbenchRecipe implements Recipe<GunniteWorkbenchBlockEntity>
{
    private final ResourceLocation id;
    private final ItemStack item;
    private final ImmutableList<GunniteWorkbenchIngredient> materials;

    public GunniteWorkbenchRecipe(ResourceLocation id, ItemStack item, ImmutableList<GunniteWorkbenchIngredient> materials)
    {
        this.id = id;
        this.item = item;
        this.materials = materials;
    }

    public ItemStack getItem()
    {
        return this.item.copy();
    }

    public ImmutableList<GunniteWorkbenchIngredient> getMaterials()
    {
        return this.materials;
    }

    @Override
    public boolean matches(GunniteWorkbenchBlockEntity inv, Level worldIn)
    {
        return false;
    }

    @Override
    public ItemStack assemble(GunniteWorkbenchBlockEntity inv)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem()
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
    public net.minecraft.world.item.crafting.RecipeType<?> getType()  { return ModRecipeType.GUNNITE_WORKBENCH; }

    public boolean hasMaterials(Player player)
    {
        for(GunniteWorkbenchIngredient ingredient : this.getMaterials())
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
        for(GunniteWorkbenchIngredient ingredient : this.getMaterials())
        {
            InventoryUtil.removeWorkstationIngredient(player, ingredient);
        }
    }
}
