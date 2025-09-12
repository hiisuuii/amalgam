package live.hisui.amalgam.events;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.attachments.EnchantedBlocks;
import live.hisui.amalgam.attachments.ModDataAttachments;
import live.hisui.amalgam.entities.ModEntities;
import live.hisui.amalgam.entities.renderers.TeleportGatewayRenderer;
import live.hisui.amalgam.items.EnchantingPowderItem;
import live.hisui.amalgam.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Amalgam.MODID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.TELEPORT_GATEWAY.get(), TeleportGatewayRenderer::new);
    }

    @SubscribeEvent
    public static void renderNameTag(RenderNameTagEvent event) {
        var player = event.getEntity();
        if(player instanceof Player) {
            var disguiseInfo = player.getData(ModDataAttachments.DISGUISE_NAME);
            var name = disguiseInfo.name().orElse(null);
            if (name != null) {
                var style = event.getContent().getStyle();
                event.setContent(Component.literal(name).setStyle(style));
            }
        }
    }

    @SubscribeEvent
    public static void renderEnchantedBlocks(LevelTickEvent.Post event){
        Level clientLevel = event.getLevel();
        if(!(clientLevel instanceof ClientLevel)) return;
        if(Minecraft.getInstance().player == null) return;
        if(Minecraft.getInstance().level == null) return;
        if(!Minecraft.getInstance().player.isHolding(stack -> stack.is(ModItems.MAGIC_FEATHER))) return;;
        ChunkPos chunkPos = Minecraft.getInstance().getCameraEntity().chunkPosition();
        int radius = 2;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                LevelChunk chunk = clientLevel.getChunk(chunkPos.x + dx, chunkPos.z + dz);
                if (chunk == null) continue;

                EnchantedBlocks attachment = chunk.getData(ModDataAttachments.ENCHANTED_BLOCKS);

                for (BlockPos pos : attachment.blocks()) {
                    if (pos.closerToCenterThan(Minecraft.getInstance().getCameraEntity().position(), 32.0)) {
                        if (clientLevel.random.nextFloat() < 0.04f) {
                            EnchantingPowderItem.makeParticles(clientLevel, pos, clientLevel.random);
                        }
                    }
                }
            }
        }
    }
}
