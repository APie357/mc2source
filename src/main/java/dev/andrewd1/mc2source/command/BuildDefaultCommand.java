package dev.andrewd1.mc2source.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.andrewd1.mc2source.util.MapBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class BuildDefaultCommand implements Command<CommandSourceStack> {
    @Override
    public int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        String mapName = "test";
        CommandSender sender = ctx.getSource().getSender();

        sender.sendMessage(Component.text("Building \"%s\" (Default)".formatted(mapName)));

        MapBuilder builder = new MapBuilder(mapName);
        builder.buildVMF();

        sender.sendMessage("Running VBSP...");
        if (!builder.runVBSP(sender)) return 0;

        sender.sendMessage("Running VVIS...");
        if (!builder.runVVIS(sender)) return 0;

        sender.sendMessage("Running VRAD...");
        if (!builder.runVRAD(sender)) return 0;

        builder.copyMap();

        sender.sendMessage(Component.text("Successfully built \"%s\"".formatted(mapName)));

        return 1;
    }
}
