package live.hisui.amalgam.events;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.attachments.ModDataAttachments;
import live.hisui.amalgam.commands.NameCommand;
import live.hisui.amalgam.commands.SkinCommand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Amalgam.MODID)
public class ModEvents {


    @SubscribeEvent
    public static void nameFormat(PlayerEvent.NameFormat event) {
        var player = event.getEntity();
        var disguiseInfo = player.getData(ModDataAttachments.DISGUISE_NAME);
        var name = disguiseInfo.name().orElse(null);
        if(name != null) {
            var style = event.getDisplayname().getStyle();
            event.setDisplayname(Component.literal(name).setStyle(style));
        }
    }


    @SubscribeEvent
    public static void tabListNameFormat(PlayerEvent.TabListNameFormat event) {
        var player = event.getEntity();
        var disguiseInfo = player.getData(ModDataAttachments.DISGUISE_NAME);
        var name = disguiseInfo.name().orElse(null);
        if(name != null) {
            var style = event.getDisplayName() != null ? event.getDisplayName().getStyle() : Style.EMPTY;
            event.setDisplayName(Component.literal(name).setStyle(style));
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event){
        SkinCommand.register(event.getDispatcher());
        NameCommand.register(event.getDispatcher());
    }
}
