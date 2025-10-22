package dev.andrewd1.mc2source;

import dev.andrewd1.mc2source.command.BuildVMFOnlyCommand;
import dev.andrewd1.mc2source.event.OnEntitySpawn;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {
    public static Plugin instance;
    public static Logger log;

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();

        if (getDataFolder().mkdirs()) log.info("Created data folder");

        getServer().getPluginManager().registerEvents(new OnEntitySpawn(), this);

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(
                Commands.literal("mc2source")
                        .then(Commands.literal("build")
                                .then(Commands.literal("vmf").executes(new BuildVMFOnlyCommand(this)))
                        )
                        .then(Commands.literal("debug")
                                .then(Commands.literal("printExecutables").executes(ctx -> {
                                    CommandSender sender = ctx.getSource().getSender();

                                    sender.sendMessage(Component.text("VBSP executable path: %s".formatted(Config.vbsp.getAbsolutePath())));
                                    sender.sendMessage(Component.text("VVIS executable path: %s".formatted(Config.vvis.getAbsolutePath())));
                                    sender.sendMessage(Component.text("VRAD executable path: %s".formatted(Config.vrad.getAbsolutePath())));

                                    return 1;
                                }))
                        )
                        .build()
            );
        });

        log.info("Enabled");
    }

    @Override
    public void onDisable() {
        log.info("Disabled");
    }
}
