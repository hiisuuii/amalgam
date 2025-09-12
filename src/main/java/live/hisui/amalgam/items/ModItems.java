package live.hisui.amalgam.items;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.blocks.ModBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Amalgam.MODID);

    // region items
    public static final DeferredItem<Item> MAGIC_STEAK = ITEMS.registerItem("magic_steak",
            properties -> new EternalFoodItem(properties.stacksTo(1).food(
                    new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).build()
            )));
    public static final DeferredItem<Item> GLORP = ITEMS.registerItem("glorp", Item::new);
    public static final DeferredItem<Item> AMBROSIA = ITEMS.registerItem("ambrosia",properties ->
            new Item(properties.stacksTo(16).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,true).food(
                    new FoodProperties.Builder().nutrition(6).saturationModifier(2.4F)
                            .effect(
                                () -> new MobEffectInstance(MobEffects.REGENERATION, 20), 100)
                            .effect(
                                () -> new MobEffectInstance(MobEffects.ABSORPTION, 3*60), 100)
                            .build()
                    )
            ));
    public static final DeferredItem<Item> ENCHANTING_POWDER = ITEMS.registerItem("enchanting_powder",
            properties -> new EnchantingPowderItem(properties.rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,true)));
    public static final DeferredItem<Item> MAGIC_FEATHER = ITEMS.registerItem("magic_feather",
            properties -> new MagicFeatherItem(properties.rarity(Rarity.RARE)
                    .durability(32).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,true).stacksTo(1)));
    public static final DeferredItem<Item> TELEPORT_SCROLL = ITEMS.registerItem("teleport_scroll",
            properties -> new TeleportScrollItem(properties.rarity(Rarity.UNCOMMON).stacksTo(1)));

    // region BlockItems
    public static final DeferredItem<BlockItem> GLORP_BLOCK = ITEMS.registerItem("glorp_block",
            properties -> new BlockItem(ModBlocks.GLORP_BLOCK.get(), properties));
    // endregion
    public static void register(IEventBus modBus){
        ITEMS.register(modBus);
    }
}
