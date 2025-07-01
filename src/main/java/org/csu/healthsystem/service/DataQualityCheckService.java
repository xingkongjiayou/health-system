package org.csu.healthsystem.service;

import org.csu.healthsystem.util.DataQualityCheckDao;
import org.csu.healthsystem.pojo.VO.DataQualityCheckRequestVO;
import org.csu.healthsystem.pojo.VO.DataQualityCheckResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataQualityCheckService {

    @Autowired
    private DataQualityCheckDao dataQualityCheckDao;

    public DataQualityCheckResultVO qualityCheck(DataQualityCheckRequestVO request) {
        String table = getTableName(request.getDataType());
        Map<String, Object> dateRange = request.getDateRange();
        Integer startYear = dateRange != null ? (Integer) dateRange.get("startYear") : null;
        Integer endYear = dateRange != null ? (Integer) dateRange.get("endYear") : null;

        // 统计总记录数
        int totalRecords = dataQualityCheckDao.countTotal(table, startYear, endYear);

        List<Map<String, Object>> issues = new ArrayList<>();
        double qualityScore = 100.0;
        List<String> suggestions = new ArrayList<>();

        // 1. 空值检查
        if (request.getCheckRules().contains("null_check")) {
            List<String> nullFields = getNullCheckFields(request.getDataType());
            for (String field : nullFields) {
                int nullCount = dataQualityCheckDao.countNull(table, field, startYear, endYear);
                if (nullCount > 0) {
                    Map<String, Object> nullIssue = new HashMap<>();
                    nullIssue.put("type", "null_value");
                    nullIssue.put("count", nullCount);
                    nullIssue.put("severity", nullCount > 10 ? "high" : "medium");
                    nullIssue.put("fields", Collections.singletonList(field));
                    issues.add(nullIssue);
                    suggestions.add("补充缺失的 " + field + " 字段数据");
                    qualityScore -= Math.min(10, nullCount * 0.5);
                }
            }
        }

        // 2. 范围检查（以 population_basic 的 year 字段为例）
        if (request.getCheckRules().contains("range_check")) {
            if ("population".equals(request.getDataType())) {
                int outOfRange = dataQualityCheckDao.countOutOfRange(table, "year", 1900, 2100, startYear, endYear);
                if (outOfRange > 0) {
                    Map<String, Object> rangeIssue = new HashMap<>();
                    rangeIssue.put("type", "range_error");
                    rangeIssue.put("count", outOfRange);
                    rangeIssue.put("severity", outOfRange > 0 ? "medium" : "low");
                    rangeIssue.put("fields", Collections.singletonList("year"));
                    issues.add(rangeIssue);
                    suggestions.add("检查 year 字段的取值范围");
                    qualityScore -= Math.min(5, outOfRange * 0.2);
                }
            }
        }

        // 3. 一致性检查（如 year 字段唯一性）
        if (request.getCheckRules().contains("consistency_check")) {
            if ("population".equals(request.getDataType())) {
                List<Map<String, Object>> duplicates = dataQualityCheckDao.findYearDuplicates(table, startYear, endYear);
                if (!duplicates.isEmpty()) {
                    Map<String, Object> consIssue = new HashMap<>();
                    consIssue.put("type", "consistency_error");
                    consIssue.put("count", duplicates.size());
                    consIssue.put("severity", "high");
                    consIssue.put("fields", Collections.singletonList("year"));
                    consIssue.put("records", duplicates.stream().map(m -> m.get("year")).toList());
                    issues.add(consIssue);
                    suggestions.add("year 字段存在重复，请检查唯一性");
                    qualityScore -= Math.min(10, duplicates.size() * 2);
                }
            }
        }

        // 4. 重复检查（以所有字段重复为例）
        if (request.getCheckRules().contains("duplicate_check")) {
            if ("population".equals(request.getDataType())) {
                List<Map<String, Object>> dups = dataQualityCheckDao.findYearDuplicates(table, startYear, endYear);
                if (!dups.isEmpty()) {
                    Map<String, Object> dupIssue = new HashMap<>();
                    dupIssue.put("type", "duplicate");
                    dupIssue.put("count", dups.size());
                    dupIssue.put("severity", "high");
                    dupIssue.put("records", dups.stream().map(m -> m.get("year")).toList());
                    issues.add(dupIssue);
                    suggestions.add("合并 year 字段重复的记录");
                    qualityScore -= Math.min(10, dups.size() * 2);
                }
            }
        }

        // 评分下限
        if (qualityScore < 0) qualityScore = 0;

        DataQualityCheckResultVO result = new DataQualityCheckResultVO();
        result.setCheckId("quality_check_" + System.currentTimeMillis());
        result.setTotalRecords(totalRecords);
        result.setQualityScore(Math.round(qualityScore * 100.0) / 100.0);
        result.setIssues(issues);
        result.setSuggestions(suggestions);

        return result;
    }

    // 获取表名
    private String getTableName(String dataType) {
        return switch (dataType) {
            case "population" -> "population_basic";
            case "bed" -> "health_bed_category";
            case "personnel" -> "health_person_category";
            // 其它类型可继续扩展
            default -> throw new IllegalArgumentException("不支持的数据类型: " + dataType);
        };
    }

    // 获取需要做空值检查的字段
    private List<String> getNullCheckFields(String dataType) {
        return switch (dataType) {
            case "population" -> Arrays.asList("year");
            case "bed" -> Arrays.asList("year", "hospital", "community_health", "health_center", "total");
            case "personnel" -> Arrays.asList("year", "health_personnel", "licensed_physician", "nurse", "pharmacist", "total");
            default -> Collections.emptyList();
        };
    }

    // 构建年份区间 where 子句
    private String buildYearWhereClause(Integer startYear, Integer endYear) {
        return buildYearWhereClause(startYear, endYear, false);
    }

    private String buildYearWhereClause(Integer startYear, Integer endYear, boolean andPrefix) {
        StringBuilder sb = new StringBuilder();
        if (startYear != null || endYear != null) {
            if (andPrefix) sb.append(" AND ");
            else sb.append(" WHERE ");
            if (startYear != null) {
                sb.append("year >= ").append(startYear);
            }
            if (startYear != null && endYear != null) {
                sb.append(" AND ");
            }
            if (endYear != null) {
                sb.append("year <= ").append(endYear);
            }
        }
        return sb.toString();
    }
}