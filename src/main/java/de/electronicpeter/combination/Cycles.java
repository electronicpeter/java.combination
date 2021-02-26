package de.electronicpeter.combination;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Cycles extends ArrayList<Cycle> {
    public CyclesStatistic getStatistics() {
        final CyclesStatistic cyclesStatistic = new CyclesStatistic();
        cyclesStatistic.setNumberOfCycles(this.size());
        this.stream().forEach(cycle -> {
            cyclesStatistic.setMaxNumberOfGroups(Math.max(cyclesStatistic.getMaxNumberOfGroups(), cycle.size()));
            cyclesStatistic.setMinNumberOfGroups(Math.min(cyclesStatistic.getMinNumberOfGroups(), cycle.size()));
            cycle.stream().forEach(group -> {
                cyclesStatistic.setMaxElementsInLargestGroup(Math.max(cyclesStatistic.getMaxElementsInLargestGroup(), group.size()));
                cyclesStatistic.setMinElementInSmallestGroup(Math.min(cyclesStatistic.getMinElementInSmallestGroup(), group.size()));
            });
        });
        return cyclesStatistic;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("cylces: " + this.size() + "\n");
        for (Cycle c : this) {
            sb.append(c);
        }
        return sb.toString();
    }
}
