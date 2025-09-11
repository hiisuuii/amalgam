package live.hisui.amalgam;

import live.hisui.amalgam.attachments.ModDataAttachments;
import live.hisui.amalgam.blocks.ModBlocks;
import live.hisui.amalgam.items.ModItems;
import live.hisui.amalgam.tabs.ModCreativeTabs;
import net.neoforged.fml.common.EventBusSubscriber;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Amalgam.MODID)
public class Amalgam {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "amalgam";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Amalgam(IEventBus modEventBus, ModContainer modContainer) {

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        ModCreativeTabs.register(modEventBus);

        ModDataAttachments.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

}
