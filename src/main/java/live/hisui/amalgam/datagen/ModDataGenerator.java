package live.hisui.amalgam.datagen;

import live.hisui.amalgam.Amalgam;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.BlockTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Amalgam.MODID)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        BlockTagsProvider blockTagsProvider;
        BlockStateProvider blockStateProvider = new ModBlockStateProvider(packOutput,existingFileHelper);
        LanguageProvider languageProvider = new ModLangProvider(packOutput);
        ItemModelProvider itemModelProvider = new ModItemModelProvider(packOutput, existingFileHelper);
        RecipeProvider recipeProvider = new ModRecipeProvider(packOutput, lookupProvider);
        GatherDataEvent.ItemTagsProvider itemTagsProvider;
        LootTableProvider lootTableProvider;

        dataGenerator.addProvider(event.includeServer(), blockStateProvider);
        dataGenerator.addProvider(event.includeServer(), languageProvider);
        dataGenerator.addProvider(event.includeClient(), itemModelProvider);
        dataGenerator.addProvider(event.includeServer(), recipeProvider);

    }
}
