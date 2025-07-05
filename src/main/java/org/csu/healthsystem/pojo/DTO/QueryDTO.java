package org.csu.healthsystem.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csu.healthsystem.pojo.DO.Condition;
import org.csu.healthsystem.pojo.DO.PageInfo;
import org.csu.healthsystem.pojo.DO.SortOrder;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryDTO {
    private List<String> columns;
    private Double step;
    private Map<String, Condition> filters;
    private List<SortOrder> sort;
    private PageInfo pageInfo;
}
