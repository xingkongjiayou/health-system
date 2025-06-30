package org.csu.healthsystem.pojo.VO;

import lombok.Data;

import java.util.List;

@Data
public class ServiceQualityAnalysisVO {
    private ServiceMetrics serviceMetrics;
    private QualityIndicators qualityIndicators;
    private List<Comparison> comparison;
    private List<String> recommendations;

    @Data
    public static class ServiceMetrics {
        private Long totalOutpatientVisits;
        private Long totalEmergencyVisits;
        private Double averageBedUtilization;
        private Double averageStayLength;
        private Double referralRate;
        private Double transferRate;
    }

    @Data
    public static class QualityIndicators {
        private String serviceEfficiency;
        private String resourceUtilization;
        private String patientFlow;
    }

    @Data
    public static class Comparison {
        private String hospitalType;
        private Double efficiency;
        private Integer ranking;
    }
}