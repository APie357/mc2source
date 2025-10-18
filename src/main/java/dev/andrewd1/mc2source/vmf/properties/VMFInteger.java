package dev.andrewd1.mc2source.vmf.properties;

public class VMFInteger extends VMFProperty<Integer> {
    public VMFInteger(String name) {
        this(name, 0);
    }

    public VMFInteger(String name, Integer initialValue) {
        super(name, initialValue);
    }

    @Override
    public String toString() {
        return "\"%s\" \"%d\"\n".formatted(name, value);
    }
}
