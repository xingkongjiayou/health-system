package org.csu.healthsystem.util;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseQueryDao<T> {
    List<T> selectByCondition(@Param("p") Map<String,Object> filters, String orderBy, int offset, int limit);
    Integer countByCondition(@Param("p") Map<String,Object> filters);
    List<Map<String, Object>> yearHistogram(@Param("p") Map<String, Object> filters);
    Map<String, Object> totalStats(@Param("p") Map<String, Object> filters);
    List<Map<String, Object>> totalBuckets(@Param("p") Map<String, Object> filters);
    List<Map<String,Object>> histogramAllNumerics(@Param("columns") List<String> columns,
                                                  @Param("step")    Integer      step,
                                                  @Param("p")       Map<String,Object> filters);
}
