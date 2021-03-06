package de.electronicpeter.server.rest.layer.mapper;

import de.electronicpeter.perfect.permutation.Cycle;
import de.electronicpeter.perfect.permutation.Cycles;
import de.electronicpeter.perfect.permutation.Group;
import de.electronicpeter.perfect.permutation.MemoryStatistic;
import de.electronicpeter.perfect.permutation.Square;
import de.electronicpeter.server.rest.layer.generated.PerfectPermutationCycle;
import de.electronicpeter.server.rest.layer.generated.PerfectPermutationGroup;
import de.electronicpeter.server.rest.layer.generated.PerfectPermutationMetaInfo;
import de.electronicpeter.server.rest.layer.generated.PerfectPermutationSquare;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(implementationPackage = "de.electronicpeter.server.rest.layer.mapper.generated")
public interface Service2RestMapper {
    PerfectPermutationMetaInfo mapMetaInfo(MemoryStatistic statistic);
    List<PerfectPermutationCycle> mapCycles(Cycles cycles);
    PerfectPermutationCycle mapCycle(Cycle cycles);
    PerfectPermutationGroup mapGroup(Group group);
    PerfectPermutationSquare mapSquare(Square square);
    List<List<Integer>> map(Integer[][] value);
    List<Integer> map(Integer[] value);
}
