package org.csu.healthsystem.service;

import org.csu.healthsystem.pojo.DO.HospitalServiceStatistics;
import org.csu.healthsystem.util.BaseQueryDao;
import org.csu.healthsystem.util.HospitalServiceStatisticsQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class HospitalServiceStatisticsQueryService extends BaseQueryService<HospitalServiceStatistics> {
    @Autowired
    private HospitalServiceStatisticsQueryDao hospitalServiceStatisticsQueryDao;

    @Override
    public Set<String> getAllowedColumns() {
        return Set.of(
                "id", "year","typeName", "outpatientVisits", "emergencyVisits", "referrals",
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
    public Map<String, Object> bed_utilization_rate(Map<String, Object> filters) {
        return hospitalServiceStatisticsQueryDao.bed_utilization_rate(filters);
    }
    @Override
    public Object buildAggregations(Map<String, Object> params)  {
        Map<String, Object> aggs = new HashMap<>();
        // 先调用父类聚合
        Object parentAggs = super.buildAggregations(params);
        if (parentAggs instanceof Map) {
            Map<?, ?> parentMap = (Map<?, ?>) parentAggs;
            for (Map.Entry<?, ?> entry : parentMap.entrySet()) {
                String key = String.valueOf(entry.getKey());
                if (!"total_buckets".equals(key) && !"year_histogram".equals(key)) {
                    aggs.put(key, entry.getValue());
                }
            }
        }
        // 新增字段
        Map<String, Object> outpatient_visits = hospitalServiceStatisticsQueryDao.outpatient_visits(params);
        aggs.put("outpatient_visits", outpatient_visits);
        Map<String, Object> bed_utilization_rate = hospitalServiceStatisticsQueryDao.bed_utilization_rate(params);
        aggs.put("bed_utilization_rate", bed_utilization_rate);
        Map<String, Object> emergency_visits = hospitalServiceStatisticsQueryDao.emergency_visits(params);
        aggs.put("emergency_visits", emergency_visits);
        Map<String, Object> referrals = hospitalServiceStatisticsQueryDao.referrals(params);
        aggs.put("referrals", referrals);
        Map<String, Object> avg_length_of_stay = hospitalServiceStatisticsQueryDao.avg_length_of_stay(params);
        aggs.put("avg_length_of_stay", avg_length_of_stay);
        Map<String, Object> transfer_out = hospitalServiceStatisticsQueryDao.transfer_out(params);
        aggs.put("transfer_out", transfer_out);
        List<Map<String, Object>> bedUtilizationRateBuckets= hospitalServiceStatisticsQueryDao.bedUtilizationRateBuckets(params);
        aggs.put("bedUtilizationRateBuckets", bedUtilizationRateBuckets);
        return aggs;
    }
}