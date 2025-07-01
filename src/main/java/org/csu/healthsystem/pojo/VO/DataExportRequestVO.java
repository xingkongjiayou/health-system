package org.csu.healthsystem.pojo.VO;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class DataExportRequestVO {
    private String dataType;
    private String exportFormat; // csv, excel, json
    private Map<String, Object> filters; // startYear, endYear, regions
    private List<String> fields;
}
