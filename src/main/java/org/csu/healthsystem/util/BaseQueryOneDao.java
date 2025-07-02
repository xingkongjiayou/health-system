package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface BaseQueryOneDao{
    String selectTableByColumn(String toPredict);
    List<Double> selectOneByCondition(@Param("p") Map<String,Object> filters,String toPredict,String table);
}
