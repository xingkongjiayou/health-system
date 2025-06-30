package org.csu.healthsystem.pojo.VO;

import lombok.Data;

import java.util.List;

@Data
public class DataImportResultVO {
    private String importId;
    private Integer totalRecords;
    private Integer successRecords;
    private Integer failedRecords;
    private List<ImportErrorDetail> importErrorDetail;
}