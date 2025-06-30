package org.csu.healthsystem.pojo.DO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationChange {
    private Long id;
    private Integer year;
    private Integer births;
    private Double birthRate;
    private Integer deaths;
    private Double deathRate;
    private Integer naturalIncrease;
    private Double naturalIncreaseRate;
}

