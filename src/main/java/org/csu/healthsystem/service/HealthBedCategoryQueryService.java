package org.csu.healthsystem.service;


import org.csu.healthsystem.pojo.DO.HealthBedCategory;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.HealthBedCategoryQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HealthBedCategoryQueryService extends BaseQueryService<HealthBedCategory> {
    @Autowired
    private HealthBedCategoryQueryDao healthBedCategoryQueryDao;

    @Override
    public Set<String> getAllowedColumns() {
        // 用驼峰风格，BaseQueryService会自动转下划线
        return Set.of("id", "year", "hospital", "communityHealth", "healthCenter", "total");
    }

    @Override
    public BaseQueryDao<HealthBedCategory> getDao() {
        return healthBedCategoryQueryDao;
    }
}
