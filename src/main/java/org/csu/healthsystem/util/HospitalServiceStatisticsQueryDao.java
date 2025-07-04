package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Param;
import org.csu.healthsystem.pojo.DO.HospitalServiceStatistics;

import java.util.Map;

public interface HospitalServiceStatisticsQueryDao extends BaseQueryDao<HospitalServiceStatistics> {
    Map<String, Object> bedUtilizationRateStats(@Param("p") Map<String, Object> filters);
    java.util.List<java.util.Map<String, Object>> bedUtilizationRateBuckets(@Param("p") Map<String, Object> filters);
}