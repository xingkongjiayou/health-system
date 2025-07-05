package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.PopulationRegion;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.PopulationRegionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("PopulationRegionService")
@RequiredArgsConstructor
public class PopulationRegionService extends BaseQueryService2<PopulationRegion> {
    private final PopulationRegionDao populationRegionDao;
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of("name", "level",
        "year","population",
                "birthPopulation","deathPopulation","birthRate","deathRate","growthRate");
    }

    @Override
    public BaseQueryDao<PopulationRegion> getDao() {
        return populationRegionDao;
    }
}
