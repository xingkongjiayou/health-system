package org.csu.healthsystem.pojo.DO;

import lombok.Data;

@Data
public class HospitalServiceStatistics {
    private Integer id;
    private String typeName;
    private Long outpatientVisits;
    private Long emergencyVisits;
    private Long referrals;
    private Long transferOut;
    private Double bedUtilizationRate;
    private Double avgLengthOfStay;
}