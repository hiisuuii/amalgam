package live.hisui.amalgam.mixin.common;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.attachments.ModDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class OnDestroyBlockEvent {


    @Inject(method = "onRemove", at = @At(value = "HEAD"))
    private void onDestroy(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston, CallbackInfo ci) {
        var chunk = level.getChunkAt(pos);
        var data = chunk.getData(ModDataAttachments.ENCHANTED_BLOCKS);
        data.remove(pos);
        chunk.syncData(ModDataAttachments.ENCHANTED_BLOCKS);
        chunk.setUnsaved(true);
    }
}
