package org.csu.healthsystem.pojo.DO;

import lombok.Data;

@Data
public class HealthBedCategory {
    private Integer id;
    private Integer year;
    private Double hospital;
    private Double communityHealth;
    private Double healthCenter;
    private Double total;
}