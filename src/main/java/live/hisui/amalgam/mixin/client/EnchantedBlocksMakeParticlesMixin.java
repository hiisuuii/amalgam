package live.hisui.amalgam.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import live.hisui.amalgam.attachments.ModDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantingTableBlock.class)
public abstract class EnchantedBlocksMakeParticlesMixin {

    @WrapOperation(method = "animateTick",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/EnchantingTableBlock;isValidBookShelf(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean inj(Level level, BlockPos enchantingTablePos, BlockPos bookshelfPos, Operation<Boolean> original){

        var contains = level.getChunkAt(enchantingTablePos.offset(bookshelfPos)).getData(ModDataAttachments.ENCHANTED_BLOCKS).contains(enchantingTablePos.offset(bookshelfPos));
        return original.call(level, enchantingTablePos, bookshelfPos) || contains;
    }

}
