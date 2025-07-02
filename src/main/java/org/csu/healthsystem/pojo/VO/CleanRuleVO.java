package org.csu.healthsystem.pojo.VO;

import lombok.Data;
import java.util.Map;

@Data
public class CleanRuleVO {
    private String rule;
    private Map<String, Object> parameters;
}