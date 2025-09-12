package live.hisui.amalgam.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TeleportScrollItem extends Item {
    public TeleportScrollItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        Vec3 start = player.position();
        Vec3 look = player.getLookAngle();
        Vec3 offset = look.scale(1000);
        Vec3 end = start.add(offset);
        player.teleportTo(end.x, end.y, end.z);
        player.getCooldowns().addCooldown(this, 30);
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
