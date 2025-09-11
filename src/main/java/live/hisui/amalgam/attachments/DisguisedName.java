package live.hisui.amalgam.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;

public record DisguisedName(Optional<String> name) {
    public static final Codec<DisguisedName> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.STRING.optionalFieldOf("name").forGetter(DisguisedName::name)
            ).apply(inst, DisguisedName::new));
    public static final StreamCodec<ByteBuf, DisguisedName> STREAM_CODEC =
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).map(DisguisedName::new, DisguisedName::name);
}
