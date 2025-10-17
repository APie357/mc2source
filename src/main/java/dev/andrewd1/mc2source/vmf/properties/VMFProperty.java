package dev.andrewd1.mc2source.vmf.properties;

public abstract class VMFProperty<T> {
    public final String name;
    public T value;

    public VMFProperty(String name, T initialValue) {
        this.name = name;
        this.value = initialValue;
    }

    public abstract String toString();
}
