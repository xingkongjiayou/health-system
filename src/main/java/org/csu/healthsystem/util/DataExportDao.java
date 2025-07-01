package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface DataExportDao {
    List<Map<String, Object>> exportData(@Param("table") String table,
                                         @Param("fields") List<String> fields,
                                         @Param("filters") Map<String, Object> filters);
}
