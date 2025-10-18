package dev.andrewd1.mc2source;

import dev.andrewd1.mc2source.event.OnEntitySpawn;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {
    public static Plugin instance;
    public static Logger log;

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();

        getServer().getPluginManager().registerEvents(new OnEntitySpawn(), this);

/*
        for (BlockFace face : BlockFace.values()) {
            log.info("%s %d %d %d".formatted(face.name(), face.getModX(), face.getModY(), face.getModZ()));
        }
*/

        log.info("Enabled");
    }

    @Override
    public void onDisable() {
        log.info("Disabled");
    }
}
