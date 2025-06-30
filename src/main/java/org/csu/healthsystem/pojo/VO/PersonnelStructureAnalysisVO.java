package org.csu.healthsystem.pojo.VO;

import lombok.Data;

import java.util.List;

@Data
public class PersonnelStructureAnalysisVO {
    private Integer currentYear;
    private Structure structure;
    private String doctorNurseRatio;
    private List<YearlyGrowth> yearlyGrowth;
    private Analysis analysis;

    @Data
    public static class Structure {
        private Double physicianRatio;
        private Double nurseRatio;
        private Double pharmacistRatio;
        private Double otherRatio;
    }

    @Data
    public static class YearlyGrowth {
        private Integer year;
        private Double totalPersonnel;
        private Double growthRate;
    }

    @Data
    public static class Analysis {
        private Boolean nurseShortage;
        private String recommendedRatio;
        private List<String> improvementSuggestions;
    }
}