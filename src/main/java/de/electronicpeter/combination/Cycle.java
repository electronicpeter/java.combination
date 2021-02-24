package de.electronicpeter.combination;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Cycle extends ArrayList<Group> {
    private final Integer dim;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Group g : this) {
            sb.append(g);
        }
        return sb.toString();
    }
}
