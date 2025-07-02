package org.csu.healthsystem.pojo.VO;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DataQualityCheckRequestVO {
    private String dataType;
    private List<String> checkRules;
    private Map<String, Object> dateRange;
}
