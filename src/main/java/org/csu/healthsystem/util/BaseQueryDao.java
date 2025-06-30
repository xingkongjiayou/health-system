package org.csu.healthsystem.util;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseQueryDao<T> {
    List<T> selectByCondition(@Param("p") Map<String,Object> filters, String orderBy, int offset, int limit);
    Integer countByCondition(@Param("p") Map<String,Object> filters);

}
