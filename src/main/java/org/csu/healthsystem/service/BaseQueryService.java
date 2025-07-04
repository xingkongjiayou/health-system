package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.Condition;
import org.csu.healthsystem.pojo.DO.PageInfo;
import org.csu.healthsystem.pojo.DO.SortOrder;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.util.BaseQueryDao;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("BaseQueryService")
@Slf4j
public abstract class BaseQueryService<T> {
    // 每张表有的字段
    public abstract Set<String> getAllowedColumns();
    //每张表的Dao
    public abstract BaseQueryDao<T> getDao();

    public ResultVO<T> query(QueryDTO req) {
        Map<String,Object> params = buildFilterMap(req.getFilters());
        String orderBy = buildOrderBy(req.getSort());
        int offset = req.getPageInfo().getIndex() * req.getPageInfo().getSize();
        int limit = req.getPageInfo().getSize();

        List<T> rows = getDao().selectByCondition(params, orderBy, offset, limit);
        Integer total = getDao().countByCondition(params);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setIndex(req.getPageInfo().getIndex());
        pageInfo.setSize(limit);
        pageInfo.setTotal(total);

        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setRows(rows);
        resultVO.setPageInfo(pageInfo);
        // 新增：聚合统计
        resultVO.setAggregations(buildAggregations(params));
        return resultVO;
    }

    /**
     * 构建聚合统计结果，自动调用DAO的yearHistogram、totalStats、totalBuckets等方法
     */
    public Object buildAggregations(Map<String, Object> params)  {
        Map<String, Object> aggs = new HashMap<>();
        try {
            // 年份直方图
            if (hasMethod(getDao(), "yearHistogram")) {
                List<Map<String, Object>> yearHist = (List<Map<String, Object>>) getDao().getClass()
                        .getMethod("yearHistogram", Map.class)
                        .invoke(getDao(), params);
                aggs.put("year_histogram", yearHist);
            }
            // 总量分桶
            if (hasMethod(getDao(), "totalBuckets")) {
                List<Map<String, Object>> totalBuckets = (List<Map<String, Object>>) getDao().getClass()
                        .getMethod("totalBuckets", Map.class)
                        .invoke(getDao(), params);
                aggs.put("total_buckets", totalBuckets);
            }
            // 总量统计
            if (hasMethod(getDao(), "totalStats")) {
                Map<String, Object> totalStats = (Map<String, Object>) getDao().getClass()
                        .getMethod("totalStats", Map.class)
                        .invoke(getDao(), params);
                aggs.put("total_stats", totalStats);
            }
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                log.error("聚合统计组装异常: {}", cause.getMessage(), cause);
            } else {
                log.error("聚合统计组装异常", e);
            }
        }
        return aggs;
    }

    /** 判断DAO是否有某方法 */
    public boolean hasMethod(Object obj, String methodName) {
        for (java.lang.reflect.Method m : obj.getClass().getMethods()) {
            if (m.getName().equals(methodName)) return true;
        }
        return false;
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
    public OperatorValue parseExpr(String expr) {
        if (expr.startsWith(">=")) {
            return new OperatorValue("Gte", parseValue(expr.substring(2)));
        } else if (expr.startsWith(">")) {
            return new OperatorValue("Gt", parseValue(expr.substring(1)));
        } else if (expr.startsWith("<=")) {
            return new OperatorValue("Lte", parseValue(expr.substring(2)));
        } else if (expr.startsWith("<")) {
            return new OperatorValue("Lt", parseValue(expr.substring(1)));
        } else if (expr.startsWith("=")) {
            return new OperatorValue("Eq", parseValue(expr.substring(1)));
        } else {
            // 默认等于
            return new OperatorValue("Eq", parseValue(expr));
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
}
