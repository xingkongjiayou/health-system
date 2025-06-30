package org.csu.healthsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.csu.healthsystem.pojo.VO.DataImportResultVO;
import org.csu.healthsystem.pojo.VO.ImportErrorDetail;
import org.csu.healthsystem.util.CsvUtil;
import org.csu.healthsystem.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate jdbcTemplate;


    public DataImportResultVO importData(MultipartFile file, String dataType, String importMode) {
        String importId = "import_" + LocalDate.now() + "_" + System.currentTimeMillis();
        List<ImportErrorDetail> errors = new ArrayList<>();
        int total = 0, success = 0, failed = 0;

        // 统一支持 Excel/CSV/JSON
        List<Map<String, Object>> dataList = parseFile(file, errors);
        total = dataList.size();

        if ("replace".equalsIgnoreCase(importMode)) {
            String table = getTableName(dataType);
            jdbcTemplate.execute("TRUNCATE TABLE " + table);
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
                jdbcTemplate.update(
                        "INSERT INTO population_basic (year, total_households, urban_households, county_households, total_population, urban_population, county_population) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        row.get("year"), row.get("total_households"), row.get("urban_households"),
                        row.get("county_households"), row.get("total_population"),
                        row.get("urban_population"), row.get("county_population")
                );
                break;
            case "institution":
                jdbcTemplate.update(
                        "INSERT INTO institution_category_stats (year, hospital, community_health, health_center, cdc, mch, total) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        row.get("year"), row.get("hospital"), row.get("community_health"),
                        row.get("health_center"), row.get("cdc"), row.get("mch"), row.get("total")
                );
                break;
            case "personnel":
                jdbcTemplate.update(
                        "INSERT INTO health_person_category (year, health_personnel, licensed_physician, nurse, pharmacist, total) VALUES (?, ?, ?, ?, ?, ?)",
                        row.get("year"), row.get("health_personnel"), row.get("licensed_physician"),
                        row.get("nurse"), row.get("pharmacist"), row.get("total")
                );
                break;
            case "bed":
                jdbcTemplate.update(
                        "INSERT INTO health_bed_category (year, hospital, community_health, health_center, total) VALUES (?, ?, ?, ?, ?)",
                        row.get("year"), row.get("hospital"), row.get("community_health"),
                        row.get("health_center"), row.get("total")
                );
                break;
            case "service":
                jdbcTemplate.update(
                        "INSERT INTO hospital_service_statistics (type_name, outpatient_visits, emergency_visits, referrals, transfer_out, bed_utilization_rate, avg_length_of_stay) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        row.get("type_name"), row.get("outpatient_visits"), row.get("emergency_visits"),
                        row.get("referrals"), row.get("transfer_out"),
                        row.get("bed_utilization_rate"), row.get("avg_length_of_stay")
                );
                break;
            case "cost":
                // 这里只以 outpatient_costs 为例，如需 inpatient_costs 可自行扩展
                jdbcTemplate.update(
                        "INSERT INTO outpatient_costs (hospital_id, total_fee, medicine_fee, exam_fee, treatment_fee) VALUES (?, ?, ?, ?, ?)",
                        row.get("hospital_id"), row.get("total_fee"), row.get("medicine_fee"),
                        row.get("exam_fee"), row.get("treatment_fee")
                );
                break;
            default:
                throw new RuntimeException("不支持的数据类型: " + dataType);
        }
    }
    //包含核心表
    private void updateRow(String dataType, Map<String, Object> row) {
        switch (dataType) {
            case "population":
                jdbcTemplate.update(
                        "UPDATE population_basic SET total_households=?, urban_households=?, county_households=?, total_population=?, urban_population=?, county_population=? WHERE year=?",
                        row.get("total_households"), row.get("urban_households"),
                        row.get("county_households"), row.get("total_population"),
                        row.get("urban_population"), row.get("county_population"),
                        row.get("year")
                );
                break;
            case "institution":
                jdbcTemplate.update(
                        "UPDATE institution_category_stats SET hospital=?, community_health=?, health_center=?, cdc=?, mch=?, total=? WHERE year=?",
                        row.get("hospital"), row.get("community_health"),
                        row.get("health_center"), row.get("cdc"), row.get("mch"), row.get("total"),
                        row.get("year")
                );
                break;
            case "personnel":
                jdbcTemplate.update(
                        "UPDATE health_person_category SET health_personnel=?, licensed_physician=?, nurse=?, pharmacist=?, total=? WHERE year=?",
                        row.get("health_personnel"), row.get("licensed_physician"),
                        row.get("nurse"), row.get("pharmacist"), row.get("total"),
                        row.get("year")
                );
                break;
            case "bed":
                jdbcTemplate.update(
                        "UPDATE health_bed_category SET hospital=?, community_health=?, health_center=?, total=? WHERE year=?",
                        row.get("hospital"), row.get("community_health"),
                        row.get("health_center"), row.get("total"),
                        row.get("year")
                );
                break;
            case "service":
                jdbcTemplate.update(
                        "UPDATE hospital_service_statistics SET outpatient_visits=?, emergency_visits=?, referrals=?, transfer_out=?, bed_utilization_rate=?, avg_length_of_stay=? WHERE type_name=?",
                        row.get("outpatient_visits"), row.get("emergency_visits"),
                        row.get("referrals"), row.get("transfer_out"),
                        row.get("bed_utilization_rate"), row.get("avg_length_of_stay"),
                        row.get("type_name")
                );
                break;
            case "cost":
                jdbcTemplate.update(
                        "UPDATE outpatient_costs SET total_fee=?, medicine_fee=?, exam_fee=?, treatment_fee=? WHERE hospital_id=?",
                        row.get("total_fee"), row.get("medicine_fee"),
                        row.get("exam_fee"), row.get("treatment_fee"),
                        row.get("hospital_id")
                );
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