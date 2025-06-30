package org.csu.healthsystem.pojo.DO;

import lombok.Data;

@Data
public class HealthPersonCategory {
    private Integer id;
    private Integer year;
    private Double healthPersonnel;
    private Double licensedPhysician;
    private Double nurse;
    private Double pharmacist;
    private Double total;
}