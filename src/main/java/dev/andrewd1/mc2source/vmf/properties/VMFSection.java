package dev.andrewd1.mc2source.vmf.properties;

import java.util.ArrayList;

public class VMFSection extends VMFProperty<ArrayList<VMFProperty<?>>> {
    public VMFSection(String name) {
        super(name, new ArrayList<>());
    }

    public void addProperty(VMFProperty<?> property) {
        value.add(property);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        sb.append(name);
        sb.append("\n{");

        for (VMFProperty<?> property : value) {
            sb.append(property.toString());
            sb.append("\n");
        }

        sb.append("}\n");

        return sb.toString();
    }
}
