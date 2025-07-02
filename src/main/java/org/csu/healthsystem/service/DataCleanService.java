package org.csu.healthsystem.service;

import org.csu.healthsystem.util.DataCleanDao;
import org.csu.healthsystem.pojo.VO.DataCleanRequestVO;
import org.csu.healthsystem.pojo.VO.DataCleanResultVO;
import org.csu.healthsystem.pojo.VO.CleanRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataCleanService {

    @Autowired
    private DataCleanDao dataCleanDao;

    // 驼峰转下划线
    private String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
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

    // 表字段白名单（可根据实际表结构补充）
    private static final Map<String, Set<String>> TABLE_FIELDS = new HashMap<>();
    static {
        TABLE_FIELDS.put("population_basic", Set.of("id", "year"));
        TABLE_FIELDS.put("institution_category_stats", Set.of("id", "year", "hospital", "community_health", "health_center", "cdc", "mch", "total"));
        TABLE_FIELDS.put("health_person_category", Set.of("id", "year", "health_personnel", "licensed_physician", "nurse", "pharmacist", "total"));
        TABLE_FIELDS.put("health_bed_category", Set.of("id", "year", "hospital", "community_health", "health_center", "total"));
        TABLE_FIELDS.put("hospital_service_statistics", Set.of("id", "type_name", "outpatient_visits", "emergency_visits", "referrals", "transfer_out", "bed_utilization_rate", "avg_length_of_stay"));
        TABLE_FIELDS.put("outpatient_costs", Set.of("id", "hospital_id", "total_fee", "medicine_fee", "exam_fee"));
    }

    public DataCleanResultVO cleanData(DataCleanRequestVO request) {
        String table = getTableName(request.getDataType());
        Set<String> allowedFields = TABLE_FIELDS.getOrDefault(table, Collections.emptySet());
        int totalAffected = 0;
        List<String> messages = new ArrayList<>();

        for (CleanRuleVO rule : request.getCleanRules()) {
            switch (rule.getRule()) {
                case "remove_duplicates":
                    List<String> keyFields = ((List<?>) rule.getParameters().get("keyFields"))
                            .stream().map(Object::toString).map(this::camelToSnake)
                            .filter(allowedFields::contains).toList();
                    if (keyFields.isEmpty()) {
                        messages.add("去重跳过：无有效字段");
                        break;
                    }
                    int removed = dataCleanDao.removeDuplicates(table, keyFields);
                    totalAffected += removed;
                    messages.add("去重完成，删除重复行数：" + removed);
                    break;
                case "fill_missing_values":
                    String field = camelToSnake(rule.getParameters().get("field").toString());
                    Object defaultValue = rule.getParameters().get("defaultValue");
                    if (!allowedFields.contains(field)) {
                        messages.add("填充跳过：字段 " + field + " 不存在");
                        break;
                    }
                    int filled = dataCleanDao.fillMissingValues(table, field, defaultValue);
                    totalAffected += filled;
                    messages.add("填充缺失值完成，影响行数：" + filled);
                    break;
                // 可扩展更多规则
                default:
                    messages.add("不支持的清洗规则: " + rule.getRule());
            }
        }

        DataCleanResultVO result = new DataCleanResultVO();
        result.setAffectedRows(totalAffected);
        result.setMessages(messages);
        return result;
    }
}