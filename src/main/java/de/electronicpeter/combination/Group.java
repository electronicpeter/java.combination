package de.electronicpeter.combination;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Group extends ArrayList<Integer> {
    public void add(Group other) {
        this.addAll(other);
    }

    public void add(Optional<Integer> value) {
        if (value.isPresent()) {
            this.add(value.get());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.stream().map(Object::toString).collect(Collectors.joining(",& ")));
        sb.append(")\\\\ \n");
        return sb.toString();
    }
}
