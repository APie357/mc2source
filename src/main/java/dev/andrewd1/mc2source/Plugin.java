package dev.andrewd1.mc2source;

import com.google.common.io.ByteStreams;
import dev.andrewd1.mc2source.command.BuildDefaultCommand;
import dev.andrewd1.mc2source.command.BuildVMFOnlyCommand;
import dev.andrewd1.mc2source.event.OnEntitySpawn;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {
    public static Plugin instance;
    public static Logger log;

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();

        if (getDataFolder().mkdirs()) log.info("Created data folder");
        log.info("Unpacking resources...");
        Reader resourceReader = getTextResource("resources.txt");
        if (resourceReader != null) {
            BufferedReader bufferedReader = new BufferedReader(resourceReader);
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.isEmpty()) continue;
                    String path = line.split(" ")[0];
                    String name = line.split(" ")[1];

                    InputStream resource = getResource(path + "/" + name);

                    if (resource == null) {
                        log.warning("Failed to find resource: " + line);
                        continue;
                    }

                    File containingFolder = new File(getDataFolder(), path);
                    if (!containingFolder.exists()) {
                        containingFolder.mkdirs();
                    }

                    ByteStreams.copy(resource, new FileOutputStream(new File(containingFolder, name)));
                    log.info("Unpacked resource: " + line);
                }
            } catch (IOException e) {
                log.warning("Failed to unpack resources: " + e.getMessage());
            }
        } else {
            log.warning("Failed to unpack resources, resources.txt not found.");
        }


        getServer().getPluginManager().registerEvents(new OnEntitySpawn(), this);

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(
                Commands.literal("mc2source")
                        .then(Commands.literal("build")
                                .then(Commands.literal("vmf").executes(new BuildVMFOnlyCommand()))
                                .then(Commands.literal("default").executes(new BuildDefaultCommand()))
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
