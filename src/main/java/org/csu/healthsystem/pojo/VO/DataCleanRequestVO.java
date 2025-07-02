package org.csu.healthsystem.pojo.VO;

import lombok.Data;
import java.util.List;

@Data
public class DataCleanRequestVO {
    private String dataType;
    private List<CleanRuleVO> cleanRules;
}