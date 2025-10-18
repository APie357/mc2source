package dev.andrewd1.mc2source.vmf.properties;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VMFVector extends VMFProperty<Vector> {
    public VMFVector(String name, Vector initialValue) {
        super(name, initialValue);
    }

    @Override
    public String toString() {
        return "\"%s\" \"%d %d %d\"\n".formatted(name, (int) value.getX(), (int) value.getY(), (int) value.getZ());
    }
}
