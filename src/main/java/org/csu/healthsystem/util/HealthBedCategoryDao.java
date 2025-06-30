package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.DO.HealthBedCategory;

import java.util.List;

@Mapper
public interface HealthBedCategoryDao {
    List<HealthBedCategory> getAll();
}