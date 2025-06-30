package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.PopulationChange;
import org.csu.healthsystem.pojo.DO.PopulationGender;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.PopulationChangeDao;
import org.csu.healthsystem.util.PopulationGenderDao;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("PopulationGenderService")
@RequiredArgsConstructor
public class PopulationGenderService extends BaseQueryService<PopulationGender> {
    private final PopulationGenderDao populationGenderDao;
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "year",
                "malePopulation", "femalePopulation", "genderRatio"
        );
    }

    @Override
    public BaseQueryDao<PopulationGender> getDao() {
        return populationGenderDao;
    }
}
