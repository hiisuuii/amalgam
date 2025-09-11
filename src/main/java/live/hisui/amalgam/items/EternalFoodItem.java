package live.hisui.amalgam.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EternalFoodItem extends Item {
    public EternalFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if(stack.has(DataComponents.FOOD)){
            livingEntity.eat(level, stack.copy());
        }
        return stack;
    }
}
