package live.hisui.amalgam.datagen;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.blocks.ModBlocks;
import live.hisui.amalgam.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Amalgam.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.GLORP.get());
//        basicItem(ModItems.AMBROSIA.get());
        basicItem(ModItems.ENCHANTING_POWDER.get());
        basicItem(ModItems.MAGIC_FEATHER.get());
//        basicItem(ModItems.MAGIC_STEAK.get());

        simpleBlockItem(ModBlocks.GLORP_BLOCK.get());
    }
}
