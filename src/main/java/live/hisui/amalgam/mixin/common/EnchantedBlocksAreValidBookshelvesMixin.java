package live.hisui.amalgam.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import live.hisui.amalgam.attachments.ModDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentMenu.class)
public class EnchantedBlocksAreValidBookshelvesMixin {


    @WrapOperation(method = "lambda$slotsChanged$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/EnchantingTableBlock;isValidBookShelf(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean enchantedBlocksAreValid(Level level, BlockPos enchantingTablePos, BlockPos bookshelfPos, Operation<Boolean> original){
        return original.call(level, enchantingTablePos, bookshelfPos) || level.getChunkAt(enchantingTablePos.offset(bookshelfPos)).getData(ModDataAttachments.ENCHANTED_BLOCKS).contains(enchantingTablePos.offset(bookshelfPos));
    }

    @WrapOperation(method = "lambda$slotsChanged$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getEnchantPowerBonus(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)F"))
    private float wtv(BlockState instance, LevelReader levelReader, BlockPos blockPos,  Operation<Float> original){
        float originalValue = original.call(instance,levelReader,blockPos);

        var contains = ((Level)levelReader).getChunkAt(blockPos).getData(ModDataAttachments.ENCHANTED_BLOCKS).contains(blockPos);

        return originalValue > 0.0 ? originalValue : contains ? 1.0f : 0.0f;
    }
}
