package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.VO.InpatientCostStatisticsVO;

import java.util.List;

@Mapper
public interface InpatientCostsDao {
    List<InpatientCostStatisticsVO> getAllInpatientCostStatistics();
}