package org.csu.healthsystem.service;

import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.pojo.DO.HospitalStatistics;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.HospitalStatisticsDao;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 医院分级统计查询服务
 */
@Service("HospitalStatisticsService")
@RequiredArgsConstructor
public class HospitalStatisticsService extends BaseQueryService2<HospitalStatistics> {

    private final HospitalStatisticsDao hospitalStatisticsDao;

    /** 前端允许查询 / 排序 / 聚合的字段（驼峰） */
    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "hospitalLevel",
                "institutionCount",
                "visitsPerDoctor",
                "bedDaysPerDoctor"
        );
    }

    /** 返回对应 DAO */
    @Override
    public BaseQueryDao<HospitalStatistics> getDao() {
        return hospitalStatisticsDao;
    }
}
