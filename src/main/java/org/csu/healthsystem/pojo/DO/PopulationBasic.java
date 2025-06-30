package org.csu.healthsystem.pojo.DO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationBasic {
    private Long id;
    private Integer year;
    private Double totalHouseholds;
    private Double urbanHouseholds;
    private Double countyHouseholds;
    private Double totalPopulation;
    private Double urbanPopulation;
    private Double countyPopulation;
}
