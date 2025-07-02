package org.csu.healthsystem.pojo.VO;

import lombok.Data;
import java.util.List;

@Data
public class DataCleanResultVO {
    private int affectedRows;
    private List<String> messages;
}