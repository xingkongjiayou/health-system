package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

@Mapper
public interface DataImportDao {
    int insertPopulation(@Param("row") Map<String, Object> row);
    int updatePopulation(@Param("row") Map<String, Object> row);
    int insertInstitution(@Param("row") Map<String, Object> row);
    int updateInstitution(@Param("row") Map<String, Object> row);
    int insertPersonnel(@Param("row") Map<String, Object> row);
    int updatePersonnel(@Param("row") Map<String, Object> row);
    int insertBed(@Param("row") Map<String, Object> row);
    int updateBed(@Param("row") Map<String, Object> row);
    int insertService(@Param("row") Map<String, Object> row);
    int updateService(@Param("row") Map<String, Object> row);
    int insertCost(@Param("row") Map<String, Object> row);
    int updateCost(@Param("row") Map<String, Object> row);
    void truncateTable(@Param("table") String table);
}
