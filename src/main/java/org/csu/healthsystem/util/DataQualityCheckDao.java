package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface DataQualityCheckDao {
    Integer countTotal(@Param("table") String table, @Param("startYear") Integer startYear, @Param("endYear") Integer endYear);
    Integer countNull(@Param("table") String table, @Param("field") String field, @Param("startYear") Integer startYear, @Param("endYear") Integer endYear);
    Integer countOutOfRange(@Param("table") String table, @Param("field") String field, @Param("min") Integer min, @Param("max") Integer max, @Param("startYear") Integer startYear, @Param("endYear") Integer endYear);
    List<Map<String, Object>> findYearDuplicates(@Param("table") String table, @Param("startYear") Integer startYear, @Param("endYear") Integer endYear);
}
