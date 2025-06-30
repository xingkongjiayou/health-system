package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.PopulationBasic;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.PopulationBasicDao;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("PopulationBasicService")
@RequiredArgsConstructor
public class PopulationBasicService extends BaseQueryService<PopulationBasic>{
    private final PopulationBasicDao populationBasicDao;
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "year",
                "totalHouseholds", "urbanHouseholds", "countyHouseholds",
                "totalPopulation", "urbanPopulation", "countyPopulation"
        );
    }

    @Override
    public BaseQueryDao<PopulationBasic> getDao() {
        return populationBasicDao;
    }
}
