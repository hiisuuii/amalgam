package live.hisui.amalgam.datagen;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.checkerframework.checker.units.qual.C;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Amalgam.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModBlocks.GLORP_BLOCK.get()).forAllStates(
                blockState -> ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("glorp_block"))
        ).build());
    }

}
