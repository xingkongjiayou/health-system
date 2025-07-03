package org.csu.healthsystem.service;

import org.csu.healthsystem.pojo.DO.TotalBedCount;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.TotalBedCountQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TotalBedCountQueryService extends BaseQueryService<TotalBedCount> {
    @Autowired
    private TotalBedCountQueryDao totalBedCountQueryDao;

    @Override
    public Set<String> getAllowedColumns() {
        // 用驼峰风格，BaseQueryService会自动转下划线
        return Set.of("id", "year", "totalCount");
    }

    @Override
    public BaseQueryDao<TotalBedCount> getDao() {
        return totalBedCountQueryDao;
    }
}
