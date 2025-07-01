package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DataCleanDao {
    int removeDuplicates(@Param("table") String table, @Param("keyFields") List<String> keyFields);
    int fillMissingValues(@Param("table") String table, @Param("field") String field, @Param("defaultValue") Object defaultValue);
}