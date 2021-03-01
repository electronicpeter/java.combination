package de.electronicpeter.combination;


import java.util.ArrayList;

public class Cycle extends ArrayList<Group> {
    @Override
    public boolean add(Group group) {
        if (!group.isEmpty()) {
            return super.add(group);
        }
        return false;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\\begin{bmatrix}\n");
        for (Group g : this) {
            sb.append(g);
        }
        sb.append("\\end{bmatrix}\n");
        return sb.toString();
    }
}
