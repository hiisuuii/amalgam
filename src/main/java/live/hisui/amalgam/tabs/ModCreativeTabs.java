package live.hisui.amalgam.tabs;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Amalgam.MODID);

    public static final Supplier<CreativeModeTab> MOD_TAB = TABS.register("amalgam",() -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.GLORP.get()))
            .title(Component.translatable("amalgam.itemGroup.amalgam"))
            .displayItems((params, output) -> {
                output.accept(ModItems.GLORP);
                output.accept(ModItems.AMBROSIA);
                output.accept(ModItems.ENCHANTING_POWDER);
                output.accept(ModItems.GLORP_BLOCK);
                output.accept(ModItems.MAGIC_FEATHER);
                output.accept(ModItems.MAGIC_STEAK);
            }
            ).build());

    public static void register(IEventBus modBus){
        TABS.register(modBus);
    }

}
