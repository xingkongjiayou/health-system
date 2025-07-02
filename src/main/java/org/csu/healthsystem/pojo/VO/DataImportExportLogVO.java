package org.csu.healthsystem.pojo.VO;


import lombok.Data;

@Data
public class DataImportExportLogVO {
    private String id;
    private String operation; // import/export
    private String dataType;
    private String fileName;
    private String status; // success/failed/processing
    private Integer totalRecords;
    private Integer successRecords;
    private Integer failedRecords;
    private String createTime;
    private String completeTime;
}
