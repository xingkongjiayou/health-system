package org.csu.healthsystem.pojo.VO;

import lombok.Data;

@Data
public class DataExportResultVO {
    private String exportId;
    private String downloadUrl;
    private String status; // processing
    private int estimatedTime;
    // getter/setter
}
