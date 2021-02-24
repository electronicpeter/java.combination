package de.electronicpeter.combination;

import lombok.extern.slf4j.Slf4j;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Square {
    private Integer[][] array;
    private Integer dimension;

    public Square(Integer numberOfElements) {
        List<Integer> elements = new ArrayList<>();
        for (int i = 0; i<numberOfElements; i++) {
            elements.add(i);
        }


        dimension = Double.valueOf(Math.ceil(Math.sqrt(numberOfElements))).intValue();
        if (dimension % 2 == 0) {
            dimension++;
        }
        log.info("dimension is {}", dimension);


        double space = (dimension * dimension - numberOfElements) / numberOfElements.doubleValue();
        log.info("space is {} {}", space);
        double spaceCounter = 0;

        array = new Integer[dimension][dimension];
        for (int y = 0; y < dimension; y ++) {
            for (int x = 0; x < dimension; x++) {
                spaceCounter++;
                log.info("x {} y {} spaceCounter {} space {}", x,y,spaceCounter, space);
                if (spaceCounter > space) {
                    spaceCounter = 0;
                    if (!elements.isEmpty()) {
                        array[x][y] = elements.get(0);
                        elements.remove(0);
                    }
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int y = 0; y < dimension; y ++) {
            for (int x = 0; x < dimension; x++) {
                sb.append(array[x][y] != null ? String.format("%3d", array[x][y]) : "  .");
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
