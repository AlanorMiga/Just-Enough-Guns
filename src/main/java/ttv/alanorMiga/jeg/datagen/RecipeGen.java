package ttv.alanorMiga.jeg.datagen;

/*
public class RecipeGen extends RecipeProvider
{
    public RecipeGen(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {
        // Dye Item
        consumer.accept(new FinishedRecipe()
        {
            @Override
            public void serializeRecipeData(JsonObject json) {}

            @Override
            public RecipeSerializer<?> getType()
            {
                return ModRecipeSerializers.DYE_ITEM.get();
            }

            @Override
            public ResourceLocation getId()
            {
                return new ResourceLocation(Reference.MOD_ID, "dye_item");
            }

            @Override
            @Nullable
            public JsonObject serializeAdvancement()
            {
                return null;
            }

            @Override
            public ResourceLocation getAdvancementId()
            {
                return null;
            }
        });

        ShapedRecipeBuilder.shaped(ModBlocks.WORKBENCH.get())
                .pattern("CCC")
                .pattern("III")
                .pattern("I I")
                .define('C', Blocks.LIGHT_GRAY_CONCRETE)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_concrete", has(Blocks.LIGHT_GRAY_CONCRETE))
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(consumer);

        // Guns
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.ASSAULT_RIFLE.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 14))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.GRENADE_LAUNCHER.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 32))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.BAZOOKA.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 44))
                .addIngredient(Items.REDSTONE, 4)
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.DYES_RED, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_redstone", has(Items.REDSTONE))
                .build(consumer);

        // Ammo
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.MEDIUM_CALIBER_BULLET.get(), 64)
                .addIngredient(GunniteWorkbenchIngredient.of(Items.COPPER_INGOT, 4))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.GUNPOWDER, 1))
                .addCriterion("has_copper_ingot", has(Items.COPPER_INGOT))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.MISSILE.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 2))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.GUNPOWDER, 4))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.GRENADE.get(), 2)
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 1))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.GUNPOWDER, 4))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.STUN_GRENADE.get(), 2)
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 1))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.GUNPOWDER, 2))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.DUSTS_GLOWSTONE, 4))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER))
                .addCriterion("has_glowstone", has(Tags.Items.DUSTS_GLOWSTONE))
                .build(consumer);

        // Scope Attachments
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.SHORT_SCOPE.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 2))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.GEMS_AMETHYST, 1))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.DUSTS_REDSTONE, 2))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_amethyst", has(Tags.Items.GEMS_AMETHYST))
                .addCriterion("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.MEDIUM_SCOPE.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 4))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.GEMS_AMETHYST, 1))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.DUSTS_REDSTONE, 4))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_amethyst", has(Tags.Items.GEMS_AMETHYST))
                .addCriterion("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.LONG_SCOPE.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 6))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.GEMS_AMETHYST, 2))
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.DYES_BLACK, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_amethyst", has(Tags.Items.GEMS_AMETHYST))
                .addCriterion("has_black_dye", has(Tags.Items.DYES_BLACK))
                .build(consumer);

        // Barrel Attachments
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.SILENCER.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 4))
                .addIngredient(GunniteWorkbenchIngredient.of(Items.SPONGE, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .build(consumer);

        // Stock Attachments
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.LIGHT_STOCK.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 6))
                .addIngredient(GunniteWorkbenchIngredient.of(Items.GRAY_WOOL, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_gray_wool", has(Items.GRAY_WOOL))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.TACTICAL_STOCK.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 8))
                .addIngredient(GunniteWorkbenchIngredient.of(Items.GRAY_WOOL, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_gray_wool", has(Items.GRAY_WOOL))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.WEIGHTED_STOCK.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 12))
                .addIngredient(GunniteWorkbenchIngredient.of(Items.GRAY_WOOL, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_gray_wool", has(Items.GRAY_WOOL))
                .build(consumer);

        // Under Barrel Attachments
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.LIGHT_GRIP.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 4))
                .addIngredient(GunniteWorkbenchIngredient.of(Items.GRAY_WOOL, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_gray_wool", has(Items.GRAY_WOOL))
                .build(consumer);
        GunniteWorkbenchRecipeBuilder.crafting(ModItems.SPECIALISED_GRIP.get())
                .addIngredient(GunniteWorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 8))
                .addIngredient(GunniteWorkbenchIngredient.of(Items.GRAY_WOOL, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_gray_wool", has(Items.GRAY_WOOL))
                .build(consumer);
    }
}*/