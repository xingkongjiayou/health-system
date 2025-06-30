package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.VO.OutpatientCostStatisticsVO;

import java.util.List;

@Mapper
public interface OutpatientCostsDao {
    List<OutpatientCostStatisticsVO> getAllOutpatientCostStatistics();
}