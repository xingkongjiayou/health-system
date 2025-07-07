package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Param;
import org.csu.healthsystem.pojo.DO.HospitalServiceStatistics;

import java.util.Map;

public interface HospitalServiceStatisticsQueryDao extends BaseQueryDao<HospitalServiceStatistics> {
    Map<String, Object> bedUtilizationRateStats(@Param("p") Map<String, Object> filters);
    
    java.util.List<java.util.Map<String, Object>> bedUtilizationRateBuckets(@Param("p") Map<String, Object> filters);
    Map<String, Object> bed_utilization_rate(@Param("p") Map<String, Object> filters);
    Map<String, Object> outpatient_visits(@Param("p") Map<String, Object> filters);
    Map<String, Object> emergency_visits(@Param("p") Map<String, Object> filters);
    Map<String, Object> referrals(@Param("p") Map<String, Object> filters);
    Map<String, Object>transfer_out(@Param("p") Map<String, Object> filters);
    Map<String, Object> avg_length_of_stay(@Param("p") Map<String, Object> filters);


}