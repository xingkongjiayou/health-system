package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.DO.HealthPersonCategory;

import java.util.List;

@Mapper
public interface HealthPersonCategoryDao {
    List<HealthPersonCategory> getAll();
}