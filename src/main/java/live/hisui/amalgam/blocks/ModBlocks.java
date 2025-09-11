package live.hisui.amalgam.blocks;

import live.hisui.amalgam.Amalgam;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Amalgam.MODID);

    public static final DeferredBlock<Block> GLORP_BLOCK = BLOCKS.registerBlock("glorp_block", properties ->
            new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).mapColor(MapColor.COLOR_PURPLE)));

    public static void register(IEventBus modBus){
        BLOCKS.register(modBus);
    }
}
