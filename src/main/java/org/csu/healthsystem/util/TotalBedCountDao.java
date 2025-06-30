package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.DO.TotalBedCount;

import java.util.List;

@Mapper
public interface TotalBedCountDao {
    List<TotalBedCount> getAll();
}