package dev.andrewd1.mc2source.util;

import dev.andrewd1.mc2source.Config;
import dev.andrewd1.mc2source.Plugin;
import dev.andrewd1.mc2source.vmf.VMFWriter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class MapBuilder {
    private final String mapName;
    private final String vmfName;

    public MapBuilder(String mapName) {
        this.mapName = mapName;
        this.vmfName = mapName + ".vmf";
    }

    public void buildVMF() {
        World world = Objects.requireNonNull(Plugin.instance.getServer().getWorld(new NamespacedKey("minecraft", "overworld")));
        VMFWriter writer = new VMFWriter(new File(Plugin.instance.getDataFolder(), vmfName));
        writer.addEntitiesFromWorld(world);
        writer.addBlocksFromWorld(world);
        writer.write();
    }

    private int run(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        if (Config.debug) {
            processBuilder.redirectOutput(new File(Plugin.instance.getDataFolder(), "debug.log"));
        }

        if (Config.shouldUseWine) {
            List<String> command = processBuilder.command();
            command.addFirst("wine");
            processBuilder.command(command);
        }

        processBuilder.environment().put("VPROJECT", Config.gameDirectory.getAbsolutePath());

        processBuilder.directory(Plugin.instance.getDataFolder());
        Process process = processBuilder.start();
        return process.waitFor();
    }

    public boolean runVBSP(@Nullable CommandSender sender) {
        try {
            int exitCode = run(new ProcessBuilder(
                    Config.vbsp.getAbsolutePath(),
                    mapName
            ));

            if (exitCode != 0 && sender != null) {
                sender.sendMessage(Component.text("VBSP failed with code %d".formatted(exitCode)));
                return false;
            }

            return true;
        } catch (IOException | InterruptedException e) {
            Plugin.log.severe(e.getMessage());
            return false;
        }
    }

    public boolean runVVIS(@Nullable CommandSender sender) {
        try {
            int exitCode = run(new ProcessBuilder(
                    Config.vvis.getAbsolutePath(),
                    mapName
            ));

            if (exitCode != 0 && sender != null) {
                sender.sendMessage(Component.text("VVIS failed with code %d".formatted(exitCode)));
                return false;
            }

            return true;
        } catch (IOException | InterruptedException e) {
            Plugin.log.severe(e.getMessage());
            return false;
        }
    }

    public boolean runVRAD(@Nullable CommandSender sender) {
        try {
            int exitCode = run(new ProcessBuilder(
                    Config.vrad.getAbsolutePath(),
                    mapName
            ));

            if (exitCode != 0 && sender != null) {
                sender.sendMessage(Component.text("VRAD failed with code %d".formatted(exitCode)));
                return false;
            }

            return true;
        } catch (IOException | InterruptedException e) {
            Plugin.log.severe(e.getMessage());
            return false;
        }
    }

    public void runBSPZip() {
        try {
            File list = new File(Plugin.instance.getDataFolder(), "resources.txt");
            File resourcesDir = new File(Plugin.instance.getDataFolder(), "resources");
            if (list.exists()) list.delete();
            list.createNewFile();

            try (FileWriter writer = new FileWriter(list)) {
                for (File resource : Objects.requireNonNull(FileUtils.getFilesRecursive(resourcesDir))) {
                    String relative = resourcesDir.toPath().relativize(resource.toPath()).toString();
                    String absolute = resource.getAbsolutePath();
                    Plugin.log.info(relative);
                    writer.write(relative + "\n");
                    writer.write(absolute + "\n");
                }
            }

            run(new ProcessBuilder(
                    Config.bspZip.getAbsolutePath(),
                    "-addlist",
                    new File(Plugin.instance.getDataFolder(), mapName + ".bsp").getAbsolutePath(),
                    list.getAbsolutePath(),
                    new File(Plugin.instance.getDataFolder(), mapName + "-final.bsp").getAbsolutePath()
            ));
        } catch (IOException | InterruptedException e) {
            Plugin.log.severe(e.getMessage());
        }
    }

    public void copyMap() {
        File mapFolder = new File(Config.gameDirectory, "maps");
        try {
            Files.copy(
                    new File(Plugin.instance.getDataFolder(), mapName + "-final.bsp").toPath(),
                    new File(mapFolder, mapName + ".bsp").toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            Plugin.log.severe(e.getMessage());
        }
    }
}
