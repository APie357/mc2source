package dev.andrewd1.mc2source.vmf.properties;

import dev.andrewd1.mc2source.util.Triple;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import static dev.andrewd1.mc2source.vmf.VMFUtil.*;

public class VMFPlane extends VMFProperty<Triple<Vector, Vector, Vector>> {
    public VMFPlane(String name, Block block, BlockFace face) {
        super(name, new Triple<>(
                minecraftToSourceLocation(block.getLocation().toVector().clone().add(getFirstPoint(face))),
                minecraftToSourceLocation(block.getLocation().toVector().clone().add(getSecondPoint(face))),
                minecraftToSourceLocation(block.getLocation().toVector().clone().add(getThirdPoint(face)))
        ));
    }
    
    public VMFPlane(String name, Vector p1, Vector p2, Vector p3) {
        super(name, new Triple<>(p1, p2, p3));
    }

    private static Vector getFirstPoint(BlockFace face) {
        return switch (face) {
            case UP -> new Vector(1, 1, 0);
            case DOWN -> new Vector(1, 0, 1);
            case NORTH -> new Vector(1, 0, 0);
            case SOUTH -> new Vector(0, 0, 1);
            case EAST -> new Vector(1, 0, 1);
            case WEST -> new Vector(0, 1, 1);
            default -> new Vector(1, 0, 1);
        };
    }

    private static Vector getSecondPoint(BlockFace face) {
        return switch (face) {
            case UP -> new Vector(1, 1, 1);
            case DOWN -> new Vector(1, 0, 0);
            case NORTH -> new Vector(1, 1, 0);
            case SOUTH -> new Vector(0, 1, 1);
            case EAST -> new Vector(1, 1, 1);
            case WEST -> new Vector(0, 0, 1);
            default -> new Vector(1, 0, 0);
        };
    }

    private static Vector getThirdPoint(BlockFace face) {
        return switch (face) {
            case UP -> new Vector(0, 1, 1);
            case DOWN -> new Vector(0, 0, 0);
            case NORTH -> new Vector(0, 1, 0);
            case SOUTH -> new Vector(1, 1, 1);
            case EAST -> new Vector(1, 1, 0);
            case WEST -> new Vector(0, 0, 0);
            default -> new Vector(0, 0, 0);
        };
    }

    public VMFPlane(String name, Triple<Vector, Vector, Vector> initialValue) {
        super(name, initialValue);
    }

    @Override
    public String toString() {
        return "\"%s\" \"(%d %d %d) (%d %d %d) (%d %d %d)\"\n".formatted(
                name,
                (int) value.left().getX(),
                (int) value.left().getY(),
                (int) value.left().getZ(),
                (int) value.middle().getX(),
                (int) value.middle().getY(),
                (int) value.middle().getZ(),
                (int) value.right().getX(),
                (int) value.right().getY(),
                (int) value.right().getZ()
        );
    }
}
