package dev.andrewd1.mc2source.vmf.properties;

import java.util.ArrayList;

public class VMFSection extends VMFProperty<ArrayList<VMFProperty<?>>> {
    public VMFSection(String name) {
        super(name, new ArrayList<>());
    }

    public VMFSection addProperty(VMFProperty<?> property) {
        value.add(property);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name);
        sb.append("\n{\n");

        for (VMFProperty<?> property : value) {
            String[] lines = property.toString().split("\n");
            for (String line : lines) {
                sb.append("    ");
                sb.append(line);
                sb.append("\n");
            }
        }

        sb.append("}\n");

        return sb.toString();
    }
}
