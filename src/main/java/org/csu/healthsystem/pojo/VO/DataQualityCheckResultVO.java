package org.csu.healthsystem.pojo.VO;


import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DataQualityCheckResultVO {
    private String checkId;
    private Integer totalRecords;
    private Double qualityScore;
    private List<Map<String, Object>> issues;
    private List<String> suggestions;
}
