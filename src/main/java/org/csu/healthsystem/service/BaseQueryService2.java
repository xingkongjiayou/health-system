package org.csu.healthsystem.service;


import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.Condition;
import org.csu.healthsystem.pojo.DO.PageInfo;
import org.csu.healthsystem.pojo.DO.SortOrder;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.util.BaseQueryDao;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service("BaseQueryService2")
@Slf4j
public abstract class BaseQueryService2<T> {
    // 每张表有的字段
    public abstract Set<String> getAllowedColumns();
    //每张表的Dao
    public abstract BaseQueryDao<T> getDao();

    public ResultVO<T> query(QueryDTO req){
        Map<String,Object> params = buildFilterMap(req.getFilters());
        String orderBy = buildOrderBy(req.getSort());
        int offset = req.getPageInfo().getIndex() * req.getPageInfo().getSize();
        int limit = req.getPageInfo().getSize();

        List<T> rows = getDao().selectByCondition(params, orderBy, offset, limit);
        Integer total = getDao().countByCondition(params);

        Map<String,Object> aggParam = new HashMap<>();
        aggParam.put("columns", req.getColumns());
        aggParam.put("step",    req.getStep());
        aggParam.put("p",       params);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setIndex(req.getPageInfo().getIndex());
        pageInfo.setSize(limit);
        pageInfo.setTotal(total);

        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setRows(rows);
        resultVO.setPageInfo(pageInfo);
        // 新增：聚合统计
        resultVO.setAggregations(buildAggregations(aggParam));
        return resultVO;
    }
    private Map<String,Object> buildFilterMap(Map<String, Condition> filters){
        Map<String,Object> m = new HashMap<>();
        if(filters == null) return m;

        filters.forEach((field, cond) -> {
            String col = camelToSnake(field);
            if(!getAllowedColumns().contains(col) || cond==null) return;
            if(cond.getEq()  != null) m.put(col + "Eq",  cond.getEq());
            if (cond.getNotEq()  != null) m.put(col + "NotEq",  cond.getNotEq());
            if(cond.getGt()  != null) m.put(col + "Gt",  cond.getGt());
            if(cond.getGte() != null) m.put(col + "Gte", cond.getGte());
            if(cond.getLt()  != null) m.put(col + "Lt",  cond.getLt());
            if(cond.getLte() != null) m.put(col + "Lte", cond.getLte());
            if (cond.getIn() != null && !cond.getIn().isEmpty()) {
                m.put(col + "In", cond.getIn());
            }
            if (cond.getNotIn()  != null && !cond.getNotIn().isEmpty())
                m.put(col + "NotIn",  cond.getNotIn());

            if (cond.getLike()    != null) m.put(col + "Like",    cond.getLike());
            if (cond.getNotLike() != null) m.put(col + "NotLike", cond.getNotLike());
        });
        return m;
    }

    @SuppressWarnings("unchecked")
    private Object buildAggregations(Map<String, Object> params) {

        List<String> cols = (List<String>) params.get("columns");
        Number       stepN = (Number)       params.get("step");
        Map<String,Object> p = (Map<String,Object>) params.get("p");

        if (cols == null || cols.isEmpty() || stepN == null) {
            return Collections.emptyMap();
        }
        Set<String> allowed = getAllowedColumns();   // ← 这里保持“驼峰形”的列名
        List<String> colsChecked = cols.stream()
                .filter(allowed::contains)           // 只留下合法列
                .toList();

        if (colsChecked.isEmpty()) return Collections.emptyMap();

        if (!hasMethod(getDao(), "histogramAllNumerics")) {
            return Collections.emptyMap();
        }

        try {
            List<String> colsSnake = colsChecked.stream()
                    .map(this::camelToSnakeTrue)          // houseCount → house_count
                    .toList();
            Method m = getDao().getClass()
                    .getMethod("histogramAllNumerics",
                            List.class, Integer.class, Map.class);

            @SuppressWarnings("unchecked")
            List<Map<String,Object>> raw =
                    (List<Map<String,Object>>) m.invoke(getDao(), colsSnake, stepN.intValue(), p);

            Map<String,List<Map<String,Object>>> grouped = raw.stream()
                    .collect(Collectors.groupingBy(
                            r -> (String) r.get("field"),
                            LinkedHashMap::new,
                            Collectors.toList()));

            return Map.of("numeric_histogram", grouped);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("调用 histogramAllNumerics 失败", e);
        }
    }

    public String buildOrderBy(List<SortOrder> sort){
        if(sort == null || sort.isEmpty()) return null;
        return sort.stream()
                .filter(s -> getAllowedColumns().contains(camelToSnake(s.getField())))
                .map(s -> camelToSnakeTrue(s.getField()) + " " +
                        ("desc".equalsIgnoreCase(s.getOrder()) ? "DESC" : "ASC"))
                .collect(Collectors.joining(", "));
    }
    /** 驼峰转下划线 */
    public String camelToSnake(String s) {
        return s/*.replaceAll("([A-Z])", "_$1").toLowerCase()*/;
    }
    public String camelToSnakeTrue(String s) {
        return s.replaceAll("([A-Z])", "_$1").toLowerCase();
    }

    /** 解析表达式，比如 ">=2010" 返回 suffix = "Gte" 和 value = 2010 */
    public BaseQueryService.OperatorValue parseExpr(String expr) {
        if (expr.startsWith(">=")) {
            return new BaseQueryService.OperatorValue("Gte", parseValue(expr.substring(2)));
        } else if (expr.startsWith(">")) {
            return new BaseQueryService.OperatorValue("Gt", parseValue(expr.substring(1)));
        } else if (expr.startsWith("<=")) {
            return new BaseQueryService.OperatorValue("Lte", parseValue(expr.substring(2)));
        } else if (expr.startsWith("<")) {
            return new BaseQueryService.OperatorValue("Lt", parseValue(expr.substring(1)));
        } else if (expr.startsWith("=")) {
            return new BaseQueryService.OperatorValue("Eq", parseValue(expr.substring(1)));
        } else {
            // 默认等于
            return new BaseQueryService.OperatorValue("Eq", parseValue(expr));
        }
    }

    public Object parseValue(String s) {
        s = s.trim();
        try {
            if (s.contains(".")) {
                return Double.parseDouble(s);
            }
            return Integer.parseInt(s);
        } catch (Exception e) {
            return s; // 可能是字符串
        }
    }

    public static class OperatorValue {
        String suffix;
        Object value;
        public OperatorValue(String suffix, Object value) {
            this.suffix = suffix;
            this.value = value;
        }
    }

    public boolean hasMethod(Object obj, String methodName) {
        for (java.lang.reflect.Method m : obj.getClass().getMethods()) {
            if (m.getName().equals(methodName)) return true;
        }
        return false;
    }


}
