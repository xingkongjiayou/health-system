package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.PopulationChange;
import org.csu.healthsystem.pojo.DO.PopulationResident;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.PopulationChangeDao;
import org.csu.healthsystem.util.PopulationResidentDao;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("PopulationResidentService")
@RequiredArgsConstructor
public class PopulationResidentService extends BaseQueryService2<PopulationResident> {
    private final PopulationResidentDao populationResidentDao;
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "year",
                "residentPopulation", "urbanResident", "ruralResident",
                "urbanizationRate"
        );
    }

    @Override
    public BaseQueryDao<PopulationResident> getDao() {
        return populationResidentDao;
    }
}
