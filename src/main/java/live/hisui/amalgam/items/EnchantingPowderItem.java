package live.hisui.amalgam.items;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.attachments.ModDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnchantingPowderItem extends Item {
    public EnchantingPowderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var blockpos = context.getClickedPos();
        var level = context.getLevel();
        var chunk = level.getChunkAt(blockpos);
        var player = context.getPlayer();
        var data = chunk.getData(ModDataAttachments.ENCHANTED_BLOCKS);
        var contains = data.contains(blockpos);
        if(player instanceof ServerPlayer && !contains) {
            data.add(blockpos);
            chunk.syncData(ModDataAttachments.ENCHANTED_BLOCKS);
            chunk.setUnsaved(true);
            var stack = context.getItemInHand();
            stack.consume(1, player);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    public static void makeParticles(Level level, BlockPos pos, RandomSource random){
        double d0 = 0.5625;
        var state = level.getBlockState(pos);

        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!state.isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double px = direction$axis == Direction.Axis.X ? 0.5 + d0 * (double)direction.getStepX() : (double)random.nextFloat();
                double py = direction$axis == Direction.Axis.Y ? 0.5 + d0 * (double)direction.getStepY() : (double)random.nextFloat();
                double pz = direction$axis == Direction.Axis.Z ? 0.5 + d0 * (double)direction.getStepZ() : (double)random.nextFloat();

                level.addParticle(
                        ParticleTypes.ENCHANT, (double)pos.getX() + px, (double)pos.getY() + py, (double)pos.getZ() + pz, 0.0, 0.0, 0.0
                );
            }
        }
    }
}
