package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.PopulationBasic;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.PopulationBasicDao;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("PopulationBasicService")
@RequiredArgsConstructor
public class PopulationBasicService extends BaseQueryService2<PopulationBasic> {
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