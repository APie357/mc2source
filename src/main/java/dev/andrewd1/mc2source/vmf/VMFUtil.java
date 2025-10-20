package dev.andrewd1.mc2source.vmf;

import dev.andrewd1.mc2source.Config;
import org.bukkit.util.Vector;

public class VMFUtil {
    // Set this to your desired origin point in Minecraft coordinates
    private static Vector originOffset = new Vector(0, 0, 0);
    
    public static void setOrigin(Vector origin) {
        originOffset = origin.clone();
    }
    
    public static Vector minecraftToSourceLocation(Vector minecraftLocation) {
        // Subtract the origin to keep coordinates reasonable
        Vector relative = minecraftLocation.clone().subtract(originOffset);
        
        return new Vector(
                relative.getX() * Config.hammerUnitsPerBlock,
                relative.getZ() * Config.hammerUnitsPerBlock,
                relative.getY() * Config.hammerUnitsPerBlock
        );
    }
}
