package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.HospitalLevelStats;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.HospitalLevelStatsDao;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("HospitalLevelStatsService")
@RequiredArgsConstructor
public class HospitalLevelStatsService extends BaseQueryService2<HospitalLevelStats> {

    private final HospitalLevelStatsDao hospitalLevelStatsDao;

    /** 允许前端查询 / 排序 / 聚合的字段（驼峰） */
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "id",
                "year",
                "level3Total",
                "level3a",
                "level3b",
                "level3Ungraded",
                "level2Total",
                "level2a",
                "level2b",
                "level2Ungraded",
                "level1Ungraded"
        );
    }

    /** 返回对应 DAO */
    @Override
    public BaseQueryDao<HospitalLevelStats> getDao() {
        return hospitalLevelStatsDao;
    }
}
