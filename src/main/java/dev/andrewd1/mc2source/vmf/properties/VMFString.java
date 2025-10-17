package dev.andrewd1.mc2source.vmf.properties;

public class VMFString extends VMFProperty<String> {
    public VMFString(String name, String initialValue) {
        super(name, initialValue);
    }

    @Override
    public String toString() {
        return "\"%s\" \"%s\"".formatted(name, value);
    }
}
