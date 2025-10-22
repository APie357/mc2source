package dev.andrewd1.mc2source.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.andrewd1.mc2source.Plugin;
import dev.andrewd1.mc2source.vmf.VMFWriter;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Objects;

public record BuildVMFOnlyCommand(Plugin plugin) implements Command<CommandSourceStack> {
    @Override
    public int run(CommandContext<CommandSourceStack> ctx) {
        String mapName = "test.vmf";
        CommandSender sender = ctx.getSource().getSender();
        World world = Objects.requireNonNull(plugin.getServer().getWorld(new NamespacedKey("minecraft", "overworld")));

        sender.sendMessage(Component.text("Building %s".formatted(mapName)));

        VMFWriter writer = new VMFWriter(new File(plugin.getDataFolder(), mapName));
        writer.addEntitiesFromWorld(world);
        writer.addBlocksFromWorld(world);
        writer.write();

        sender.sendMessage(Component.text("Successfully built %s".formatted(mapName)));

        return 1;
    }
}
