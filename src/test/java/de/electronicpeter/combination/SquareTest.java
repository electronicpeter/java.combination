package de.electronicpeter.combination;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SquareTest {
    @Test
    public void a() {
        log.info(new Square(53 * 53, Square.FillAlgorithm.CIRCLE).toPrimeString());
        // log.info(new Square(53*53, Square.FillAlgorithm.SQUARE2).toPrimeString());
        // log.info(new Square(121, Square.FillAlgorithm.ROW).toString());
        // log.info(new Square(17, Square.FillAlgorithm.SPACED).toString());
    }
}
