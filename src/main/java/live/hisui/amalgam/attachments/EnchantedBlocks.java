package live.hisui.amalgam.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;

public record EnchantedBlocks(ArrayList<BlockPos> blocks) {
    public static final Codec<EnchantedBlocks> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    BlockPos.CODEC.listOf().xmap(ArrayList::new, list -> list)
                            .fieldOf("blocks")
                            .forGetter(EnchantedBlocks::blocks)
            ).apply(inst, EnchantedBlocks::new)
    );
    public static final StreamCodec<ByteBuf, EnchantedBlocks> STREAM_CODEC =
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list())
            .map(ArrayList::new, (ArrayList<BlockPos> list) -> list)
                    .map(EnchantedBlocks::new, EnchantedBlocks::blocks);

    public boolean add(BlockPos pos) {
        if(blocks.contains(pos)) return false;
        return blocks.add(pos);
    }

    public boolean remove(BlockPos pos) {
        if(!blocks.contains(pos)) return false;
        return blocks.remove(pos);
    }

    public boolean contains(BlockPos pos) {
        return blocks.contains(pos);
    }
}
