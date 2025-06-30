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
    List<T> rows;
    PageInfo pageInfo;
}
