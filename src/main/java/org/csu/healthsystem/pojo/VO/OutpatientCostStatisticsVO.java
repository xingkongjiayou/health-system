package org.csu.healthsystem.pojo.VO;

import lombok.Data;

@Data
public class OutpatientCostStatisticsVO {
    private Integer id;
    private Integer hospitalId;
    private String hospitalLevel;
    private Integer totalFee;
    private Integer medicineFee;
    private Integer examFee;
    private Integer treatmentFee;
    private Double medicineRatio;
    private Double examRatio;
    private Double treatmentRatio;
}