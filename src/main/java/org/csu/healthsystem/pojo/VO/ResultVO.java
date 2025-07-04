package org.csu.healthsystem.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csu.healthsystem.pojo.DO.PageInfo;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultVO<T> {
    private List<T> rows;
    private PageInfo pageInfo;
    private Object aggregations;
}
