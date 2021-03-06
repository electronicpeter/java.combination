package de.electronicpeter.perfect.permutation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PermutationTest {
    private static Set<Integer> unsolvableSet = new HashSet<>(Arrays.asList(5,7,11));
    @Test
    public void checkAllAlgorithms() {
        for (Square.FillAlgorithm algorithm : Square.FillAlgorithm.values()) {
            Map<Integer, MemoryStatistic> justOk = new HashMap<>();
            for (int i = 4; i <= 1000; i++) {
                check(i, justOk, algorithm);
            }
            log.info("in a 1000 groups made with {} found {} which are just ok", algorithm, justOk.keySet().size());
            log.info("{}", justOk.keySet().stream().sorted().map(Object::toString).collect(Collectors.joining(", ")));
            int keyMax = -1;
            MemoryStatistic valueMax = null;
            for (Integer key : justOk.keySet()) {
                MemoryStatistic value = justOk.get(key);
                if (keyMax == -1 || valueMax.getNumberOfElementsWithMoreThanOneMatch() < value.getNumberOfElementsWithMoreThanOneMatch()) {
                    valueMax = value;
                    keyMax = key;
                }
            }
            log.info("{} -> worst key {} with {} ", algorithm, keyMax, valueMax.toString());
            Assertions.assertEquals(MemoryStatistic.Status.OK, valueMax.getStatus());

            if (! unsolvableSet.contains(keyMax)) {
                Cycles combinations = new Permutation().findPerfectPermutation(keyMax, Square.FillAlgorithm.BEST);
                MemoryStatistic memoryStatistic = new Memory(combinations).getMemoryStatistic();
                log.info("perfect -> key {} with {}", keyMax, memoryStatistic);
                Assertions.assertEquals(MemoryStatistic.Status.PERFECT, memoryStatistic.getStatus());
            }
        }
    }

    @Test
    public void checkCritical() {
        int critical = 200;
        for (Square.FillAlgorithm algorithm : Square.FillAlgorithm.values()) {
            Map<Integer, MemoryStatistic> justOk = new HashMap<>();
            for (int i = 4; i <= critical; i++) {
                check(i, justOk, algorithm);
            }
            log.info("in a {} groups made with {} found {} which are just ok {}", critical, algorithm, justOk.keySet().size(),
                    justOk.keySet().stream().sorted().map(Object::toString).collect(Collectors.joining(", ")));
        }
        Map<Integer, MemoryStatistic> justOk = new HashMap<>();
        for (int i = 4; i <= critical; i++) {
            check(i, justOk, Square.FillAlgorithm.BEST);
        }
        log.info("in a {} groups made with best algorithm found {} which are just ok {}", critical, justOk.keySet().size(),
                justOk.keySet().stream().sorted().map(Object::toString).collect(Collectors.joining(", ")));
        Assertions.assertEquals(unsolvableSet, justOk.keySet());
    }

    @Test
    public void checkPerfect() {
        Map<Integer, MemoryStatistic> justOk = new HashMap<>();
        log.info("check from 4 till 1000");
        for (int i = 4; i <= 1000; i++) {
            check(i, justOk, Square.FillAlgorithm.BEST);
        }
        for (int largeRandoms = 0; largeRandoms < 10; largeRandoms++) {
            Random random = new Random();
            int largeNumber = random.nextInt(10000);
            log.info("check for {}", largeNumber);
            check(largeNumber, justOk, Square.FillAlgorithm.BEST);
        }
        log.info("{}", justOk.keySet().stream().sorted().map(Object::toString).collect(Collectors.joining(", ")));
        Assertions.assertEquals(unsolvableSet, justOk.keySet());
        log.info("did find perfect groups for all tested numbers");
    }

    @Test
    public void checkAny() {
        check(16);
    }

    @Test
    public void checkBestDeterministic() {
        int limit = 100;
        Map<Square.FillAlgorithm, List<Integer>> exceptions = new HashMap<>();
        for (Square.FillAlgorithm algorithm : Square.FillAlgorithm.values()) {
            Map<Integer, MemoryStatistic> justOk = new HashMap<>();
            for (int i = 4; i <= limit; i++) {
                check(i, justOk, algorithm);
            }
            exceptions.put(algorithm, justOk.keySet().stream().sorted().collect(Collectors.toList()));
        }
        // preferred algorithm is square, so find other where square is not good enough
        List<Integer> squareExceptions = exceptions.get(Square.FillAlgorithm.SQUARE);
        final List<Integer> doneBySquare2 = new ArrayList<>();
        final List<Integer> doneByRow = new ArrayList<>();

        squareExceptions.forEach(el -> {
            if (!exceptions.get(Square.FillAlgorithm.SQUARE2).contains(el)) {
                doneBySquare2.add(el);
            } else if (!exceptions.get(Square.FillAlgorithm.ROW).contains(el)) {
                doneByRow.add(el);
            }
        });
        log.info("done by row        {}", doneByRow.stream().map(Object::toString).collect(Collectors.joining(", ")));
        log.info("done by square2    {}", doneBySquare2.stream().map(Object::toString).collect(Collectors.joining(", ")));
        log.info("not done by square {}", squareExceptions.stream().map(Object::toString).collect(Collectors.joining(", ")));
        squareExceptions.removeAll(doneByRow);
        squareExceptions.removeAll(doneBySquare2);
        log.info("not best algorithm {}", squareExceptions.stream().map(Object::toString).collect(Collectors.joining(", ")));
        Assertions.assertEquals(unsolvableSet, new HashSet<>(squareExceptions));
    }

    private void check(int size) {
        check(size, Square.FillAlgorithm.BEST);
    }

    private void check(int size, Square.FillAlgorithm fillAlgorithm) {
        check(size, null, fillAlgorithm);
    }

    private void check(int size, Map<Integer, MemoryStatistic> map, Square.FillAlgorithm fillAlgorithm) {
        Cycles combinations = new Permutation().findPerfectPermutation(size, fillAlgorithm);
        // log.info("square is {}", combinations.getSquare().toString());
        Memory memory = new Memory(combinations);
        MemoryStatistic memoryStatistic = memory.getMemoryStatistic();
        switch (memoryStatistic.getStatus()) {
            case PERFECT:
                if (map == null) {
                    log.info("check {} is PERFECT", size);
                    log.info("{}", combinations.toString());
                    log.info("\n{}", memory.getMemoryStatistic().toString());
                    log.info("{}", memory.toString());
                }
                return;
            case OK:
                if (map == null) {
                    log.info("check {} just OK", size);
                    log.info("{}", combinations.toString());
                    log.info("\n{}", memory.getMemoryStatistic().toString());
                    log.info("{}", memory.toString());
                } else {
                    map.put(size, memoryStatistic);
                }
                return;
            default:
                log.info("check {} NOT OK", size);
                log.info("{}", combinations.toString());
                log.info("{}", memory.toString());
                throw new RuntimeException("no ok");
        }
    }
}
