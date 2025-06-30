package org.csu.healthsystem.pojo.VO;

import lombok.Data;

import java.util.List;

@Data
public class CostStructureComparisonAnalysisVO {
    private Double averageTotalFee;
    private String medicineRatioRange;
    private String highestCostLevel;
    private String lowestCostLevel;
    private String costTrend;
    private List<String> recommendations;
}