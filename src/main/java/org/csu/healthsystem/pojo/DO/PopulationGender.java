package org.csu.healthsystem.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationGender {
    private Long id;
    private Integer year;
    private Double malePopulation;
    private Double femalePopulation;
    private Double genderRatio;
}
