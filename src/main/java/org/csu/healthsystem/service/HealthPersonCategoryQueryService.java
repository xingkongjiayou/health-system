package org.csu.healthsystem.service;

import org.csu.healthsystem.pojo.DO.HealthPersonCategory;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.HealthPersonCategoryQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HealthPersonCategoryQueryService extends BaseQueryService<HealthPersonCategory> {
    @Autowired
    private HealthPersonCategoryQueryDao healthPersonCategoryQueryDao;

    @Override
    public Set<String> getAllowedColumns() {
        // 用驼峰风格，BaseQueryService会自动转下划线
        return Set.of("id", "year", "healthPersonnel", "licensedPhysician", "nurse", "pharmacist", "total");
    }

    @Override
    public BaseQueryDao<HealthPersonCategory> getDao() {
        return healthPersonCategoryQueryDao;
    }
}
