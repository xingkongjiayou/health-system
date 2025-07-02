package org.csu.healthsystem.service;

import org.csu.healthsystem.util.DataExportDao;
import org.csu.healthsystem.pojo.VO.DataExportRequestVO;
import org.csu.healthsystem.pojo.VO.DataExportResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service("DataExportService")
@EnableAsync
public class DataExportService {

    @Autowired
    private DataExportDao dataExportDao;

    public DataExportResultVO exportData(DataExportRequestVO request) {
        String exportId = "export_" + LocalDate.now() + "_" + System.currentTimeMillis();
        String downloadUrl = "/api/data/download/" + exportId;
        DataExportResultVO result = new DataExportResultVO();
        result.setExportId(exportId);
        result.setDownloadUrl(downloadUrl);
        result.setStatus("processing");
        result.setEstimatedTime(30);
        // 异步导出
        doExportAsync(exportId, request);
        return result;
    }

    @Async
    public void doExportAsync(String exportId, DataExportRequestVO request) {
        String filePath = null;
        try {
            // 参数校验
            if (request == null || request.getDataType() == null || request.getExportFormat() == null) {
                throw new IllegalArgumentException("导出参数不完整");
            }
            String table = getTableName(request.getDataType());
            List<String> fields = request.getFields();
            // 字段名驼峰转下划线，保证传给 Mapper 的都是数据库字段
            List<String> dbFields = null;
            if (fields != null && !fields.isEmpty()) {
                dbFields = new java.util.ArrayList<>();
                for (String f : fields) {
                    dbFields.add(camelToSnake(f));
                }
            }
            Map<String, Object> filters = request.getFilters();
            // 只对有 region_id 的表传 regions
            java.util.Set<String> regionTables = java.util.Set.of(
                "institution_category_stats",
                "health_bed_category",
                "hospital_service_statistics",
                "outpatient_costs"
            );
            if (!regionTables.contains(table) && filters != null) {
                filters.remove("regions");
            }
            List<Map<String, Object>> data = dataExportDao.exportData(table, dbFields, filters);

            // 文件路径和目录
            filePath = "export/" + exportId + getFileSuffix(request.getExportFormat());
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // 导出文件
            if ("csv".equalsIgnoreCase(request.getExportFormat())) {
                writeCsv(data, fields, file);
            } else if ("excel".equalsIgnoreCase(request.getExportFormat())) {
                writeExcel(data, fields, file);
            } else if ("json".equalsIgnoreCase(request.getExportFormat())) {
                writeJson(data, fields, file);
            } else {
                throw new IllegalArgumentException("不支持的导出格式: " + request.getExportFormat());
            }

            // 检查文件是否生成
            if (!file.exists() || file.length() == 0) {
                throw new RuntimeException("导出文件生成失败");
            }
            System.out.println("导出任务 " + exportId + " 已完成，文件路径：" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("导出任务 " + exportId + " 失败：" + e.getMessage());
        }
    }
    private String getTableName(String dataType) {
        switch (dataType) {
            case "population": return "population_basic";
            case "institution": return "institution_category_stats";
            case "personnel": return "health_person_category";
            case "bed": return "health_bed_category";
            case "service": return "hospital_service_statistics";
            case "cost": return "outpatient_costs";
            default: throw new RuntimeException("不支持的数据类型: " + dataType);
        }
    }

    // 驼峰转下划线
    private String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    private String getFileSuffix(String format) {
        if ("csv".equalsIgnoreCase(format)) return ".csv";
        if ("excel".equalsIgnoreCase(format)) return ".xlsx";
        if ("json".equalsIgnoreCase(format)) return ".json";
        return ".dat";
    }

    private void writeCsv(List<Map<String, Object>> data, List<String> fields, File file) throws Exception {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            // 写表头
            for (int i = 0; i < fields.size(); i++) {
                if (i > 0) writer.write(",");
                writer.write(fields.get(i));
            }
            writer.write("\n");
            // 写数据
            for (Map<String, Object> row : data) {
                for (int i = 0; i < fields.size(); i++) {
                    if (i > 0) writer.write(",");
                    String dbField = camelToSnake(fields.get(i));
                    Object val = row.get(dbField);
                    writer.write(val == null ? "" : val.toString());
                }
                writer.write("\n");
            }
        }
    }

    private void writeExcel(List<Map<String, Object>> data, List<String> fields, File file) throws Exception {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Sheet1");
            // 写表头
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fields.size(); i++) {
                headerRow.createCell(i).setCellValue(fields.get(i));
            }
            // 写数据
            for (int i = 0; i < data.size(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(i + 1);
                Map<String, Object> rowData = data.get(i);
                for (int j = 0; j < fields.size(); j++) {
                    String dbField = camelToSnake(fields.get(j));
                    Object val = rowData.get(dbField);
                    row.createCell(j).setCellValue(val == null ? "" : val.toString());
                }
            }
            // 自动列宽
            for (int i = 0; i < fields.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            // 写入文件
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                workbook.write(fos);
            }
        }
    }
    private void writeJson(List<Map<String, Object>> data, List<String> fields, File file) throws Exception {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            List<Map<String, Object>> resultList = new java.util.ArrayList<>();
            for (Map<String, Object> row : data) {
                Map<String, Object> resultRow = new java.util.LinkedHashMap<>();
                for (String field : fields) {
                    String dbField = camelToSnake(field);
                    resultRow.put(field, row.get(dbField));
                }
                resultList.add(resultRow);
            }
            writer.write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(resultList));
        }
    }
}

