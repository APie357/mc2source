package dev.andrewd1.mc2source;

import dev.andrewd1.mc2source.event.OnEntitySpawn;
import dev.andrewd1.mc2source.vmf.VMFWriter;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
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

//        for (BlockFace face : BlockFace.values()) {
//            log.info("%s %d %d %d".formatted(face.name(), face.getModX(), face.getModY(), face.getModZ()));
//        }

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(
                    Commands.literal("build").executes(ctx -> {
                        VMFWriter writer = new VMFWriter(new java.io.File(getDataFolder(), "test.vmf"));
                        writer.addEntitiesFromWorld(Objects.requireNonNull(getServer().getWorld("world")));
                        writer.addBlocksFromWorld(Objects.requireNonNull(getServer().getWorld("world")));
                        writer.write();
                        return 1;
                    }).build()
            );
        });

        log.info("Enabled");
    }

    @Override
    public void onDisable() {
        log.info("Disabled");
    }
}
