package org.csu.healthsystem.util;

import org.csu.healthsystem.pojo.DO.DataTag;
import org.csu.healthsystem.pojo.VO.DataTagVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DataTagDao {
    List<DataTagVO> getAllTags();
    void insertTag(DataTag tag);
    int batchAssignTags(@Param("table") String table,
                        @Param("dataIds") List<Long> dataIds,
                        @Param("tagIds") List<Long> tagIds);
}
