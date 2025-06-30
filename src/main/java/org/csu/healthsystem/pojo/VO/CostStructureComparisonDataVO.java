package org.csu.healthsystem.pojo.VO;

import lombok.Data;

@Data
public class CostStructureComparisonDataVO {
    private String hospitalLevel;
    private Double totalFee;
    private Double medicineRatio;
    private Double examRatio;
    private Double treatmentRatio;
    private Integer ranking;
}