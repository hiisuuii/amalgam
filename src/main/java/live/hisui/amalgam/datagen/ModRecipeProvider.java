package live.hisui.amalgam.datagen;

import live.hisui.amalgam.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(ModItems.ENCHANTING_POWDER.get(), 3))
                .requires(Tags.Items.GEMS_LAPIS)
                .requires(Tags.Items.GEMS_AMETHYST)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Items.GLOW_BERRIES)
                .unlockedBy("has_lapis",has(Tags.Items.GEMS_LAPIS))
                .unlockedBy("has_amethyst",has(Tags.Items.GEMS_AMETHYST))
                .unlockedBy("has_redstone",has(Tags.Items.DUSTS_REDSTONE))
                .unlockedBy("has_berry",has(Items.GLOW_BERRIES))
                .save(recipeOutput);

    }
}
