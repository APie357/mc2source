package dev.andrewd1.mc2source.vmf;

import dev.andrewd1.mc2source.vmf.properties.VMFSection;
import io.papermc.paper.math.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.ArrayList;

public class VMFWriter {
    private final File vmfFile;
    private final ArrayList<VMFSection> sections = new ArrayList<>();

    public VMFWriter(File vmfFile) {
        this.vmfFile = vmfFile;
    }

    public void addBlock(BlockPosition pos, Block block) {

    }

    public void addEntity(Entity entity) {
        if (entity.getType() == EntityType.PLAYER) return;
        Location pos = entity.getLocation();

        if (entity.getType() == EntityType.VILLAGER) {

        }
    }
}
