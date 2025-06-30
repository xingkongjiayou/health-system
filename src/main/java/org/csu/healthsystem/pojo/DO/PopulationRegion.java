package org.csu.healthsystem.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationRegion {
    private String name;
    private String level;
    private Integer year;
    private Double population;
    private Double birthPopulation;
    private Double deathPopulation;
    private Double birthRate;
    private Double deathRate;
    private Double growthRate;
}
