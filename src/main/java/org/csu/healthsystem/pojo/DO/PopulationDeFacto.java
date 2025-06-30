package org.csu.healthsystem.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationDeFacto {
    private Integer year;
    private Double ruralPopulation;
    private Double urbanPopulation;
}
