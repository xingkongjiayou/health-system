package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.DO.HospitalServiceStatistics;

import java.util.List;

@Mapper
public interface HospitalServiceStatisticsDao {
    List<HospitalServiceStatistics> getAll();
}