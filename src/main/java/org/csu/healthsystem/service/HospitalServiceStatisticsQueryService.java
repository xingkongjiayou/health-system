package org.csu.healthsystem.service;

import org.csu.healthsystem.pojo.DO.HospitalServiceStatistics;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.HospitalServiceStatisticsQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class HospitalServiceStatisticsQueryService extends BaseQueryService<HospitalServiceStatistics> {
    @Autowired
    private HospitalServiceStatisticsQueryDao hospitalServiceStatisticsQueryDao;

    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "id", "typeName", "outpatientVisits", "emergencyVisits", "referrals",
                "transferOut", "bedUtilizationRate", "avgLengthOfStay"
        );
    }

    @Override
    public BaseQueryDao<HospitalServiceStatistics> getDao() {
        return hospitalServiceStatisticsQueryDao;
    }
    // 支持 Map<String, Object> 和 Map<String, Condition> 两种参数
    public Map<String, Object> getBedUtilizationRateStats(Map<String, Object> filters) {
        return hospitalServiceStatisticsQueryDao.bedUtilizationRateStats(filters);
    }
    public Map<String, Object> getBedUtilizationRateStatsByCondition(Map<String, org.csu.healthsystem.pojo.DO.Condition> filters) {
        return hospitalServiceStatisticsQueryDao.bedUtilizationRateStats((Map)filters);
    }

    public java.util.List<java.util.Map<String, Object>> getBedUtilizationRateBuckets(Map<String, Object> filters) {
        return hospitalServiceStatisticsQueryDao.bedUtilizationRateBuckets(filters);
    }
    public java.util.List<java.util.Map<String, Object>> getBedUtilizationRateBucketsByCondition(Map<String, org.csu.healthsystem.pojo.DO.Condition> filters) {
        return hospitalServiceStatisticsQueryDao.bedUtilizationRateBuckets((Map)filters);
    }
}