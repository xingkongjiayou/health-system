package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.PopulationBasic;
import org.csu.healthsystem.pojo.DO.PopulationChange;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.PopulationBasicDao;
import org.csu.healthsystem.util.PopulationChangeDao;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("PopulationChangeService")
@RequiredArgsConstructor
public class PopulationChangeService extends BaseQueryService<PopulationChange> {
    private final PopulationChangeDao populationChangeDao;
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "year",
                "births", "birthrate", "deaths",
                "deathRate", "natural_increase", "natural_increase_rate"
        );
    }

    @Override
    public BaseQueryDao<PopulationChange> getDao() {
        return populationChangeDao;
    }
}
