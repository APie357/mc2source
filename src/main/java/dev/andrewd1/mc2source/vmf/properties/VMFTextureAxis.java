package dev.andrewd1.mc2source.vmf.properties;

import dev.andrewd1.mc2source.util.TextureAxis;

public class VMFTextureAxis extends VMFProperty<TextureAxis> {
    public VMFTextureAxis(String name, double x, double y, double z, double translation, double scale) {
        this(name, new TextureAxis(x, y, z, translation, scale));
    }

    public VMFTextureAxis(String name, TextureAxis initialValue) {
        super(name, initialValue);
    }

    @Override
    public String toString() {
        return "\"%s\" \"[%f %f %f %f] %f\"\n".formatted(name, value.x(), value.y(), value.z(), value.translation(), value.scale());
    }
}
