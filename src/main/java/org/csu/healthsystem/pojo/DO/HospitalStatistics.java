package org.csu.healthsystem.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalStatistics {
    private String hospitalLevel;
    private Integer institutionCount;
    private Integer visitsPerDoctor;
    private Integer bedDaysPerDoctor;
}
