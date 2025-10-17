package dev.andrewd1.mc2source.vmf.properties;

import java.awt.*;

public class VMFColor extends VMFProperty<Color> {
    public VMFColor(String name) {
        this(name, Color.BLACK);
    }

    public VMFColor(String name, Color initialValue) {
        super(name, initialValue);
    }

    @Override
    public String toString() {
        return "\"%s\" \"%d %d %d\"".formatted(name, value.getRed(), value.getGreen(), value.getBlue());
    }
}
