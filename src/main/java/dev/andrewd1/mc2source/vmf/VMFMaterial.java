package dev.andrewd1.mc2source.vmf;

import org.bukkit.Material;

public enum VMFMaterial {
    GRASS(Material.GRASS_BLOCK, "NATURE/GRASSFLOOR002A"),
    DIRT(Material.DIRT, "NATURE/DIRTFLOOR011A"),
    SKYBOX(Material.CYAN_CONCRETE, "TOOLS/TOOLSSKYBOX"),
    NODRAW(null, "TOOLS/TOOLSNODRAW"),

    ;

    public final Material blockType;
    public final String texture;

    VMFMaterial(Material blockType, String texture) {
        this.blockType = blockType;
        this.texture = texture;
    }

    public static VMFMaterial fromMaterial(Material material) {
        for (VMFMaterial vmfMaterial : VMFMaterial.values()) {
            if (vmfMaterial.blockType == material) {
                return vmfMaterial;
            }
        }
        return null;
    }
}
