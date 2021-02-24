package de.electronicpeter.combination;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SquareTest {
    @Test
    public void a() {
        for (int i = 2; i<10; i++) {
            log.info("{} -> {}", i, new Square(i).toString());
        }
    }
}
