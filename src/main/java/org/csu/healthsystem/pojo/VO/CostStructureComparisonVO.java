package org.csu.healthsystem.pojo.VO;

import lombok.Data;

import java.util.List;

@Data
public class CostStructureComparisonVO {
    private List<CostStructureComparisonDataVO> comparisonData;
    private CostStructureComparisonAnalysisVO analysis;
}
