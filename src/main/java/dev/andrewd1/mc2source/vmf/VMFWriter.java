package dev.andrewd1.mc2source.vmf;

import dev.andrewd1.mc2source.Config;
import dev.andrewd1.mc2source.Plugin;
import dev.andrewd1.mc2source.vmf.properties.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static dev.andrewd1.mc2source.vmf.VMFUtil.*;

public class VMFWriter {
    private final File vmfFile;
    private final ArrayList<VMFProperty<?>> properties;
    private final VMFSection brushes;
    private int idCounter = 1;

    public VMFWriter(File vmfFile) {
        this.vmfFile = vmfFile;
        this.properties = new ArrayList<>();
        this.brushes = new VMFSection("world")
                .addProperty(new VMFInteger("id", idCounter++))
                .addProperty(new VMFInteger("mapversion", 3))
                .addProperty(new VMFString("classname", "worldspawn"))
                .addProperty(new VMFString("detailmaterial", "detail/detailsprites"))
                .addProperty(new VMFString("detailvbsp", "detail.vbsp"))
                .addProperty(new VMFInteger("maxpropscreenwidth", -1))
                .addProperty(new VMFString("skyname", "sky_day01_01"))
        ;

        properties.add(new VMFSection("versioninfo")
                .addProperty(new VMFInteger("editorversion", 400))
                .addProperty(new VMFInteger("editorbuild", 0))
                .addProperty(new VMFInteger("mapversion", 0))
                .addProperty(new VMFInteger("formatversion", 100))
                .addProperty(new VMFBoolean("prefab", false))
        );

        properties.add(new VMFSection("visgroups"));

        properties.add(new VMFSection("viewsettings")
                .addProperty(new VMFBoolean("bSnapToGrid", true))
                .addProperty(new VMFBoolean("bShowGrid", true))
                .addProperty(new VMFBoolean("bShowLogicalGrid", false))
                .addProperty(new VMFInteger("nGridSpacing", Config.hammerUnitsPerBlock))
        );

        properties.add(new VMFSection("cameras")
                .addProperty(new VMFInteger("activecamera", -1))
        );

        properties.add(new VMFSection("cordons")
                .addProperty(new VMFBoolean("active", false))
        );

        properties.add(brushes);
    }

    public void addBlock(Block block) {
        if (block.getType() == Material.AIR) return;

        VMFSection vmfBlock = new VMFSection("solid")
                .addProperty(new VMFInteger("id", idCounter++))
                ;

        block.getRelative(BlockFace.DOWN);
        for (BlockFace face : new BlockFace[]{
                BlockFace.NORTH,
                BlockFace.EAST,
                BlockFace.SOUTH,
                BlockFace.WEST,
                BlockFace.UP,
                BlockFace.DOWN
        }) {
            boolean shouldBeDrawn = block.getRelative(face).isSolid();
            vmfBlock.addProperty(
                    new VMFSection("side")
                            .addProperty(new VMFInteger("id", idCounter++))
                            .addProperty(new VMFPlane("plane", block, face))
                            .addProperty(new VMFString("material", shouldBeDrawn ? "NATURE/GRASSFLOOR002A" : "TOOLS/TOOLSNODRAW"))
                            .addProperty(new VMFTextureAxis("uaxis", 1, 0, 0, 0, 0.25))
                            .addProperty(new VMFTextureAxis("vaxis", 0, 1, 0, 0, 0.25))
                            .addProperty(new VMFInteger("rotation", 0))
                            .addProperty(new VMFInteger("lightmapscale", 16))
                            .addProperty(new VMFInteger("smoothing_groups", 0))
                    );
        }

        vmfBlock.addProperty(new VMFSection("editor")
                .addProperty(new VMFColor("color", new Color(0, 234, 235)))
                .addProperty(new VMFBoolean("visgroupshown", true))
                .addProperty(new VMFBoolean("visgroupautoshown", true))
        );
        brushes.addProperty(vmfBlock);
    }

    public void addBlocksFromChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = -64; y < 318; y++) {
                    addBlock(chunk.getBlock(x, y, z));
                }
            }
        }
    }

    public void addBlocksFromWorld(World world) {
        // Set the origin to world spawn
        VMFUtil.setOrigin(world.getSpawnLocation().toVector());
        
        for (Chunk chunk : world.getLoadedChunks()) {
            addBlocksFromChunk(chunk);
        }
    }

    public void addEntity(Entity entity) {
        if (entity.getType() == EntityType.PLAYER) return;
        Location pos = entity.getLocation();
        VMFEntity entityType = null;

        for (VMFEntity vmfEntity : VMFEntity.values()) {
            if (vmfEntity.type == entity.getType()) {
                entityType = vmfEntity;
                break;
            }
        }

        if (entityType == null) {
            entity.remove();
            return;
        }

        VMFSection entitySection = new VMFSection("entity")
                .addProperty(new VMFInteger("id", idCounter++))
                .addProperty(new VMFString("classname", entityType.name().toLowerCase()))
                .addProperty(new VMFVector("origin", minecraftToSourceLocation(pos.toVector())))
                .addProperty(new VMFVector("rotation", new Vector(0, 0, 0)))
                .addProperty(new VMFSection("editor"))
        ;

        properties.add(entitySection);
    }

    public void addEntitiesFromWorld(World world) {
        for (Entity entity : world.getEntities()) {
            addEntity(entity);
        }
    }

    public void write() {
        try (FileWriter writer = new FileWriter(vmfFile)) {
            for (VMFProperty<?> property : properties) {
                writer.write(property.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Plugin.instance.getServer().sendMessage(Component.text("Failed to write VMF file: " + e.getMessage()));
        }
    }
}
