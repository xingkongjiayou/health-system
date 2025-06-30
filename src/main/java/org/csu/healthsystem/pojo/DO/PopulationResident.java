package org.csu.healthsystem.pojo.DO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationResident {
    private Long id;
    private Integer year;
    private Double residentPopulation;
    private Double urbanResident;
    private Double ruralResident;
    private Double urbanizationRate;
}
