package dev.andrewd1.mc2source.vmf;

import dev.andrewd1.mc2source.Config;
import dev.andrewd1.mc2source.Plugin;
import dev.andrewd1.mc2source.vmf.properties.*;
import io.papermc.paper.math.BlockPosition;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.ArrayList;

public class VMFWriter {
    private final File vmfFile;
    private final ArrayList<VMFProperty<?>> properties;
    private final VMFSection brushes;
    private int idCounter = 1;

    public VMFWriter(File vmfFile) {
        this.vmfFile = vmfFile;
        this.properties = new ArrayList<>();
        this.brushes = new VMFSection("brushes")
                .addProperty(new VMFInteger("id", idCounter++))
                .addProperty(new VMFInteger("mapversion", 3))
                .addProperty(new VMFString("classname", "worldspawn"))
                .addProperty(new VMFString("detailmaterial", "detail/detailsprites"))
                .addProperty(new VMFInteger("maxpropscreenwitdth", -1))
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
        VMFSection vmfBlock = new VMFSection("solid")
                .addProperty(new VMFInteger("id", idCounter++));

        block.getRelative(BlockFace.DOWN);
        for (BlockFace face : new BlockFace[]{
                BlockFace.NORTH,
                BlockFace.EAST,
                BlockFace.SOUTH,
                BlockFace.WEST,
                BlockFace.UP,
                BlockFace.DOWN
        }) {
            vmfBlock.addProperty(new VMFSection("side")
                    .addProperty(new VMFInteger("id", idCounter++))
                    .addProperty(new VMFString("material", "TOOLS/NODRAW"))
                    .addProperty(new VMFPlane("plane", block, face))
                    .addProperty(new VMFString("uaxis", "[1 0 0 0] 0.25"))  // TODO: make real (texture offsets?)
                    .addProperty(new VMFString("vaxis", "[0 1 0 0] 0.25"))  // TODO: make real (texture offsets?)
                    .addProperty(new VMFInteger("rotation", 0))
                    .addProperty(new VMFInteger("lightmapscale", 16))
                    .addProperty(new VMFInteger("smoothing_groups", 0))
            );
        }

        brushes.addProperty(vmfBlock);
    }

    public void addEntity(Entity entity) {
        if (entity.getType() == EntityType.PLAYER) return;
        Location pos = entity.getLocation();

        if (entity.getType() == EntityType.VILLAGER) {

        }
    }
}
