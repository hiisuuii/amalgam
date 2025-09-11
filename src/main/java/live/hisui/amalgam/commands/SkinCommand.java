package live.hisui.amalgam.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import live.hisui.amalgam.attachments.DisguisedSkin;
import live.hisui.amalgam.attachments.ModDataAttachments;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public class SkinCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("skin").requires(commandSourceStack -> commandSourceStack.isPlayer() && commandSourceStack.hasPermission(2))
                    .then(Commands.argument("target", EntityArgument.player())
                    .then(Commands.literal("set")
                            .then(Commands.argument("username", StringArgumentType.word())
                                    .executes(context -> {
                                        var player = EntityArgument.getPlayer(context, "target");
                                        var username = StringArgumentType.getString(context, "username");
                                        player.setData(
                                                ModDataAttachments.DISGUISE_SKIN, new DisguisedSkin(Optional.ofNullable(username))
                                        );
                                        context.getSource().sendSuccess(() -> Component.translatable("command.amalgam.skin.set", player.getName().getString(), username), true);
                                        return 1;
                                    })))
                    .then(Commands.literal("clear")
                            .executes(context -> {
                                var player = EntityArgument.getPlayer(context, "target");
                                player.removeData(ModDataAttachments.DISGUISE_SKIN);
                                context.getSource().sendSuccess(() -> Component.translatable("command.amalgam.skin.clear", player.getName()), true);

                                return 1;
                            }))
                    )

        );
    }
}
