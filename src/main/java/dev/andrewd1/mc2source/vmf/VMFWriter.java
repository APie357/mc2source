package dev.andrewd1.mc2source.vmf;

import io.papermc.paper.math.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.io.File;

public class VMFWriter {
    private final File vmfFile;

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
