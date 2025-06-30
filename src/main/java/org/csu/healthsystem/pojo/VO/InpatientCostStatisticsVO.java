package org.csu.healthsystem.pojo.VO;

import lombok.Data;

@Data
public class InpatientCostStatisticsVO {
    private Integer id;
    private Integer hospitalId;
    private String hospitalLevel;
    private Integer totalFee;
    private Integer bedFee;
    private Integer medicineFee;
    private Integer treatmentFee;
    private Double bedRatio;
    private Double medicineRatio;
    private Double treatmentRatio;
}