package org.csu.healthsystem.pojo.DTO;

import lombok.Data;

@Data
public class BedUtilizationAnalysisRequest {
    private Integer year;
    private String hospitalType;
}
