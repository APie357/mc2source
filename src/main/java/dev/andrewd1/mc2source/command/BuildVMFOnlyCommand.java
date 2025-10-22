package dev.andrewd1.mc2source.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import dev.andrewd1.mc2source.Plugin;
import dev.andrewd1.mc2source.util.MapBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class BuildVMFOnlyCommand implements Command<CommandSourceStack> {
    @Override
    public int run(CommandContext<CommandSourceStack> ctx) {
        String mapName = "test";
        CommandSender sender = ctx.getSource().getSender();

        sender.sendMessage(Component.text("Building \"%s\" (VMF Only)".formatted(mapName)));

        MapBuilder builder = new MapBuilder(mapName);
        builder.buildVMF();

        sender.sendMessage(Component.text("Successfully built \"%s\"".formatted(mapName)));

        return 1;
    }
}
