package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.PopulationChange;
import org.csu.healthsystem.pojo.DO.PopulationDeFacto;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.PopulationChangeDao;
import org.csu.healthsystem.util.PopulationDeFactoDao;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("PopulationDeFactoService")
@RequiredArgsConstructor
public class PopulationDeFactoService extends BaseQueryService<PopulationDeFacto> {
    private final PopulationDeFactoDao populationDeFactoDao;
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "year",
                "ruralPopulation", "urbanPopulation"
        );
    }

    @Override
    public BaseQueryDao<PopulationDeFacto> getDao() {
        return populationDeFactoDao;
    }
}
