package dev.andrewd1.mc2source.vmf.properties;

import dev.andrewd1.mc2source.util.Triple;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class VMFPlane extends VMFProperty<Triple<Location, Location, Location>> {
    public VMFPlane(String name, Block block, BlockFace face) {
        // TODO: implement
        super(name, new Triple<>(null, null, null));
    }

    public VMFPlane(String name, Triple<Location, Location, Location> initialValue) {
        super(name, initialValue);
    }

    @Override
    public String toString() {
        return "\"%s\" \"(%d %d %d) (%d %d %d) (%d %d %d)\"".formatted(
                name,
                (int) value.left().x(),
                (int) value.left().y(),
                (int) value.left().z(),
                (int) value.middle().x(),
                (int) value.middle().y(),
                (int) value.middle().z(),
                (int) value.right().x(),
                (int) value.right().y(),
                (int) value.right().z()
        );
    }
}
