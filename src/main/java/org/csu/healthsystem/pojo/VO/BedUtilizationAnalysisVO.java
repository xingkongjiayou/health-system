package org.csu.healthsystem.pojo.VO;

import lombok.Data;

import java.util.List;

@Data
public class BedUtilizationAnalysisVO {
    private List<UtilizationData> utilizationData;
    private Analysis analysis;

    @Data
    public static class UtilizationData {
        private String hospitalType;
        private Double bedUtilizationRate;
        private Double avgLengthOfStay;
        private Double totalBeds;
        private Double occupiedBeds;
    }

    @Data
    public static class Analysis {
        private Double averageUtilization;
        private String highestUtilization;
        private String lowestUtilization;
        private List<String> recommendations;
    }
}