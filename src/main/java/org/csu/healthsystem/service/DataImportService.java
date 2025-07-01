package org.csu.healthsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.csu.healthsystem.util.DataImportDao;
import org.csu.healthsystem.pojo.VO.DataImportResultVO;
import org.csu.healthsystem.pojo.VO.ImportErrorDetail;
import org.csu.healthsystem.util.CsvUtil;
import org.csu.healthsystem.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("DataImportService")
public class DataImportService {

    @Autowired
    private DataImportDao dataImportDao;


    public DataImportResultVO importData(MultipartFile file, String dataType, String importMode) {
        String importId = "import_" + LocalDate.now() + "_" + System.currentTimeMillis();
        List<ImportErrorDetail> errors = new ArrayList<>();
        int total = 0, success = 0, failed = 0;

        // 统一支持 Excel/CSV/JSON
        List<Map<String, Object>> dataList = parseFile(file, errors);
        total = dataList.size();

        if ("replace".equalsIgnoreCase(importMode)) {
            String table = getTableName(dataType);
            dataImportDao.truncateTable(table);
        }

        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> row = dataList.get(i);
            if ("population".equals(dataType)) {
                row = mapPopulationFields(row);
            }
            try {
                if ("insert".equalsIgnoreCase(importMode)) {
                    insertRow(dataType, row);
                } else if ("update".equalsIgnoreCase(importMode)) {
                    updateRow(dataType, row);
                } else if ("replace".equalsIgnoreCase(importMode)) {
                    insertRow(dataType, row);
                }
                success++;
            } catch (Exception e) {
                failed++;
                errors.add(new ImportErrorDetail(i + 2, e.getMessage()));
            }
        }

        DataImportResultVO result = new DataImportResultVO();
        result.setImportId(importId);
        result.setTotalRecords(total);
        result.setSuccessRecords(success);
        result.setFailedRecords(failed);
        result.setImportErrorDetail(errors);
        return result;
    }

    // 合并后的 parseFile 方法
    private List<Map<String, Object>> parseFile(MultipartFile file, List<ImportErrorDetail> errors) {
        String filename = file.getOriginalFilename();
        try {
            if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
                return ExcelUtil.readExcel(file.getInputStream());
            } else if (filename.endsWith(".csv")) {
                return CsvUtil.readCsv(file.getInputStream());
            } else if (filename.endsWith(".json")) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(file.getInputStream(), new TypeReference<List<Map<String, Object>>>(){});
            } else {
                throw new RuntimeException("仅支持Excel/CSV/JSON文件");
            }
        } catch (Exception e) {
            errors.add(new ImportErrorDetail(0, "文件解析失败: " + e.getMessage()));
            return Collections.emptyList();
        }
    }

    // 动态表名
    private String getTableName(String dataType) {
        switch (dataType) {
            case "population": return "population_basic";
            case "institution": return "institution_category_stats";
            case "personnel": return "health_person_category";
            case "bed": return "health_bed_category";
            case "service": return "hospital_service_statistics";
            case "cost": return "outpatient_costs"; // 或 inpatient_costs，需前端指定
            default: throw new RuntimeException("不支持的数据类型: " + dataType);
        }
    }

    // 动态插入(包含核心表)
    private void insertRow(String dataType, Map<String, Object> row) {
        switch (dataType) {
            case "population":
                dataImportDao.insertPopulation(row);
                break;
            case "institution":
                dataImportDao.insertInstitution(row);
                break;
            case "personnel":
                dataImportDao.insertPersonnel(row);
                break;
            case "bed":
                dataImportDao.insertBed(row);
                break;
            case "service":
                dataImportDao.insertService(row);
                break;
            case "cost":
                dataImportDao.insertCost(row);
                break;
            default:
                throw new RuntimeException("不支持的数据类型: " + dataType);
        }
    }
    //包含核心表
    private void updateRow(String dataType, Map<String, Object> row) {
        switch (dataType) {
            case "population":
                dataImportDao.updatePopulation(row);
                break;
            case "institution":
                dataImportDao.updateInstitution(row);
                break;
            case "personnel":
                dataImportDao.updatePersonnel(row);
                break;
            case "bed":
                dataImportDao.updateBed(row);
                break;
            case "service":
                dataImportDao.updateService(row);
                break;
            case "cost":
                dataImportDao.updateCost(row);
                break;
            default:
                throw new RuntimeException("不支持的数据类型: " + dataType);
        }
    }

    // population 字段名映射表
    private static final Map<String, String> POPULATION_FIELD_MAP = Map.of(
        "total_hourban_hc", "total_households",
        "urban_hc", "urban_households",
        "county_hc", "county_households",
        "total_pop", "total_population",
        "urban_pop", "urban_population"
    );

    private Map<String, Object> mapPopulationFields(Map<String, Object> row) {
        Map<String, Object> mapped = new HashMap<>();
        for (Map.Entry<String, Object> entry : row.entrySet()) {
            String key = POPULATION_FIELD_MAP.getOrDefault(entry.getKey(), entry.getKey());
            mapped.put(key, entry.getValue());
        }
        return mapped;
    }
}