package live.hisui.amalgam.attachments;

import live.hisui.amalgam.Amalgam;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

public class ModDataAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Amalgam.MODID);

    public static final Supplier<AttachmentType<DisguisedName>> DISGUISE_NAME = ATTACHMENT_TYPES.register(
            "disguise_name", () ->
                    AttachmentType.builder(
                            () -> new DisguisedName(Optional.empty())
                    ).serialize(DisguisedName.CODEC).copyOnDeath().sync(DisguisedName.STREAM_CODEC).build()
    );
    public static final Supplier<AttachmentType<DisguisedSkin>> DISGUISE_SKIN = ATTACHMENT_TYPES.register(
            "disguise_skin", () ->
                    AttachmentType.builder(
                            () -> new DisguisedSkin(Optional.empty())
                    ).serialize(DisguisedSkin.CODEC).copyOnDeath().sync(DisguisedSkin.STREAM_CODEC).build()
    );
    public static final Supplier<AttachmentType<EnchantedBlocks>> ENCHANTED_BLOCKS = ATTACHMENT_TYPES.register(
            "enchanted_blocks", () ->
                    AttachmentType.builder(
                            () -> new EnchantedBlocks(new ArrayList<>())
                    ).serialize(EnchantedBlocks.CODEC).sync(EnchantedBlocks.STREAM_CODEC).build()
    );

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }


}
