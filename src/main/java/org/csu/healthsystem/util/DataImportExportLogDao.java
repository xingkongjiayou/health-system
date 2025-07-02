package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.csu.healthsystem.pojo.VO.DataImportExportLogVO;

import java.util.List;

@Mapper
public interface DataImportExportLogDao {
    List<DataImportExportLogVO> selectLogs(
            @Param("operation") String operation,
            @Param("status") String status,
            @Param("offset") int offset,
            @Param("size") int size
    );

    long countLogs(
            @Param("operation") String operation,
            @Param("status") String status
    );
}
