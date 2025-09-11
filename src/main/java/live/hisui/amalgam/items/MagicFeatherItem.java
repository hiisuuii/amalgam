package live.hisui.amalgam.items;

import live.hisui.amalgam.attachments.ModDataAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MagicFeatherItem extends Item {
    public MagicFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var blockpos = context.getClickedPos();
        var level = context.getLevel();
        var chunk = level.getChunkAt(blockpos);
        var data = chunk.getData(ModDataAttachments.ENCHANTED_BLOCKS);
        var player = context.getPlayer();
        var contains = data.contains(blockpos);
        if(player instanceof ServerPlayer && contains) {
            data.remove(blockpos);
            chunk.syncData(ModDataAttachments.ENCHANTED_BLOCKS);
            chunk.setUnsaved(true);
            var stack = context.getItemInHand();
            stack.hurtAndBreak(1, context.getPlayer(), LivingEntity.getSlotForHand(context.getHand()));
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        int i = stack.getDamageValue() + amount;
        if (i >= stack.getMaxDamage()) {
            stack.shrink(1);
            if(entity instanceof ServerPlayer serverplayer){
                ItemStack itemstack = new ItemStack(Items.FEATHER,1);
                boolean flag = serverplayer.getInventory().add(itemstack);
                if (flag && itemstack.isEmpty()) {
                    ItemEntity itementity1 = serverplayer.drop(itemstack, false);
                    if (itementity1 != null) {
                        itementity1.makeFakeItem();
                    }

                    serverplayer.level()
                            .playSound(
                                    null,
                                    serverplayer.getX(),
                                    serverplayer.getY(),
                                    serverplayer.getZ(),
                                    SoundEvents.ITEM_PICKUP,
                                    SoundSource.PLAYERS,
                                    0.2F,
                                    ((serverplayer.getRandom().nextFloat() - serverplayer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                            );
                    serverplayer.containerMenu.broadcastChanges();
                } else {
                    ItemEntity itementity = serverplayer.drop(itemstack, false);
                    if (itementity != null) {
                        itementity.setNoPickUpDelay();
                        itementity.setTarget(serverplayer.getUUID());
                    }
                }
            }
            return 0;
        }
        return super.damageItem(stack, amount, entity, onBroken);
    }
}
