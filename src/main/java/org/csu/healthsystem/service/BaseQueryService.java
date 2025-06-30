package org.csu.healthsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.Condition;
import org.csu.healthsystem.pojo.DO.PageInfo;
import org.csu.healthsystem.pojo.DO.SortOrder;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.util.BaseQueryDao;
import org.springframework.stereotype.Service;

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
        return resultVO;
    }

    private Map<String,Object> buildFilterMap(Map<String, Condition> filters){
        Map<String,Object> m = new HashMap<>();
        if(filters == null) return m;

        filters.forEach((field, cond) -> {
            String col = camelToSnake(field);
            if(!getAllowedColumns().contains(col) || cond==null) return;

            if(cond.getEq()  != null) m.put(col + "Eq",  cond.getEq());
            if(cond.getGt()  != null) m.put(col + "Gt",  cond.getGt());
            if(cond.getGte() != null) m.put(col + "Gte", cond.getGte());
            if(cond.getLt()  != null) m.put(col + "Lt",  cond.getLt());
            if(cond.getLte() != null) m.put(col + "Lte", cond.getLte());
        });
        return m;
    }

    public String buildOrderBy(List<SortOrder> sort){
        if(sort == null || sort.isEmpty()) return null;
        return sort.stream()
                .filter(s -> getAllowedColumns().contains(camelToSnake(s.getField())))
                .map(s -> camelToSnake(s.getField()) + " " +
                        ("desc".equalsIgnoreCase(s.getOrder()) ? "DESC" : "ASC"))
                .collect(Collectors.joining(", "));
    }
    /** 驼峰转下划线 */
    public String camelToSnake(String s) {
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
