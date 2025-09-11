package live.hisui.amalgam.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;

public record DisguisedSkin(Optional<String> skinName) {
    public static final Codec<DisguisedSkin> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.STRING.optionalFieldOf("skinName").forGetter(DisguisedSkin::skinName)
            ).apply(inst, DisguisedSkin::new));
    public static final StreamCodec<ByteBuf, DisguisedSkin> STREAM_CODEC =
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).map(DisguisedSkin::new, DisguisedSkin::skinName);

}
