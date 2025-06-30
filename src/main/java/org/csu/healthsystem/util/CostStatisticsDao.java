package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.VO.CostStructureComparisonDataVO;

import java.util.List;

@Mapper
public interface CostStatisticsDao {
    List<CostStructureComparisonDataVO> getOutpatientCostStructureComparison();
    List<CostStructureComparisonDataVO> getInpatientCostStructureComparison();
}