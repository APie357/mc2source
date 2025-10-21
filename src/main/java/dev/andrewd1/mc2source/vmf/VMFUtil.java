package dev.andrewd1.mc2source.vmf;

import dev.andrewd1.mc2source.Config;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class VMFUtil {
    public static Vector minecraftToSourceLocation(Vector minecraftLocation) {
        return new Vector(
                minecraftLocation.getX() * Config.hammerUnitsPerBlock,
                minecraftLocation.getZ() * Config.hammerUnitsPerBlock,
                minecraftLocation.getY() * Config.hammerUnitsPerBlock
        );
    }

    public static boolean isFaceHorizontal(BlockFace face) {
        return face == BlockFace.UP || face == BlockFace.DOWN;
    }
}
