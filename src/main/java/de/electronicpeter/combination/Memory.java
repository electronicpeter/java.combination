package de.electronicpeter.combination;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Memory {
    private int dimension;
    private Integer[][] mem;

    public Memory(int dimension) {
        this.dimension = dimension;

        mem = new Integer[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            mem[i] = new Integer[dimension];
            for (int i2 = 0; i2 < dimension; i2++) {
                mem[i][i2] = 0;
            }
        }
    }

    public Memory set(Cycle cycle) {
        for (Group group : cycle) {
            set(group);
        }
        return this;
    }

    public Memory set(Cycles cycles) {
        for (Cycle cycle : cycles) {
            set(cycle);
        }
        return this;
    }

    public Memory set(Group group) {
        for (int groupIndex = 0; groupIndex < group.size(); groupIndex++) {
            int i1 = group.get(groupIndex);
            for (int remainderIndex = groupIndex + 1; remainderIndex < group.size(); remainderIndex++) {
                int i2 = group.get(remainderIndex);
                set(i1, i2);
            }
        }
        return this;
    }

    public Memory set(Integer element1, Integer element2) {
        int i1 = Math.min(element1, element2);
        int i2 = Math.max(element1, element2);
        if (i1 != i2) {
            mem[i1][i2] += 1;
        }
        return this;
    }

    public String toString() {
        int l = ("" + (dimension - 1)).length() + 1;

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\\begin{bmatrix}");
        sb.append(String.format("%" + l +"s", " "));
        for (int col = 0; col < dimension; col++) {
            sb.append(String.format("%" + l + "s&", col));
        }
        sb.append("\\\\ \n");
        for (int row = 0; row < dimension; row++) {
            sb.append(String.format("%" + l + "s", row));
            for (int col = 0; col < dimension; col++) {
                sb.append(String.format("%" + l + "s &", mem[row][col] > 0 ? mem[row][col] == 1 ? "." : mem[row][col] : " "));
            }
            sb.append("\\\\ \n");
        }
        sb.append("\\end{bmatrix}");
        return sb.toString();
    }

    public Boolean everyThingOne() {
        for (int row = 0; row < dimension; row++) {
            for (int col = row + 1; col < dimension; col++) {
                if (mem[row][col] != 1) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

    public Boolean everyThingOneOrMore() {
        for (int row = 0; row < dimension; row++) {
            for (int col = row + 1; col < dimension; col++) {
                if (mem[row][col] < 1) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

    public MemoryStatistic getMemoryStatistic() {
        MemoryStatistic memoryStatistic = new MemoryStatistic();
        if (everyThingOne()) {
            memoryStatistic.setStatus(MemoryStatistic.Status.PERFECT);
        } else if (everyThingOneOrMore()) {
            memoryStatistic.setStatus(MemoryStatistic.Status.OK);
        } else {
            memoryStatistic.setStatus(MemoryStatistic.Status.FAULTY);
        }

        int count = 0;
        for (int row = 0; row < dimension; row++) {
            for (int col = row + 1; col < dimension; col++) {
                if (mem[row][col] > 1) {
                    memoryStatistic.setNumberOfElementsWithMoreThanOneMatch(memoryStatistic.getNumberOfElementsWithMoreThanOneMatch()+1);
                    memoryStatistic.setMaxNumberOfMatchings(Math.max(memoryStatistic.getMaxNumberOfMatchings(), mem[row][col]));
                }
            }
        }
        return memoryStatistic;
    }
}
