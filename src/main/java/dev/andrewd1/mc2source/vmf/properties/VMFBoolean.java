package dev.andrewd1.mc2source.vmf.properties;

public class VMFBoolean extends VMFProperty<Boolean> {
    public VMFBoolean(String name) {
        this(name, false);
    }

    public VMFBoolean(String name, Boolean initialValue) {
        super(name, initialValue);
    }

    @Override
    public String toString() {
        return "\"%s\" \"%d\"\n".formatted(name, value ? 1 : 0);
    }
}
