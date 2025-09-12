package live.hisui.amalgam.items;

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
        BlockState state = level.getBlockState(pos);
        VoxelShape shape = state.getShape(level, pos);

        if (shape.isEmpty()) {
            shape = Shapes.block(); // fallback: full cube
        }

        AABB box = shape.bounds();
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;

        double epsilon = 0.025;

        for (Direction dir : Direction.values()) {
            BlockPos blockpos = pos.relative(dir);
            if (level.isEmptyBlock(blockpos)) {
                double px, py, pz;

                switch (dir.getAxis()) {
                    case X -> {
                        px = dir == Direction.EAST ? maxX + epsilon : minX - epsilon;
                        py = random.nextDouble() * (maxY - minY) + minY;
                        pz = random.nextDouble() * (maxZ - minZ) + minZ;
                    }
                    case Y -> {
                        px = random.nextDouble() * (maxX - minX) + minX;
                        py = dir == Direction.UP ? maxY + epsilon : minY - epsilon;
                        pz = random.nextDouble() * (maxZ - minZ) + minZ;
                    }
                    case Z -> {
                        px = random.nextDouble() * (maxX - minX) + minX;
                        py = random.nextDouble() * (maxY - minY) + minY;
                        pz = dir == Direction.SOUTH ? maxZ + epsilon : minZ - epsilon;
                    }
                    default -> throw new IllegalStateException("Unexpected axis: " + dir.getAxis());
                }
                level.addParticle(
                        ParticleTypes.ENCHANT, (double)pos.getX() + px, (double)pos.getY() + py, (double)pos.getZ() + pz, 0.0, 0.0, 0.0
                );
            }
        }
    }
}
