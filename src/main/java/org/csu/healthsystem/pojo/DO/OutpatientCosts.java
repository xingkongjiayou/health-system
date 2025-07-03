package org.csu.healthsystem.pojo.DO;

import lombok.Data;

// OutpatientCosts.java
@Data
public class OutpatientCosts {
    private Integer id;
    private Integer hospitalId;
    private Integer totalFee;
    private Integer medicineFee;
    private Integer examFee;
    private Integer treatmentFee;
}
