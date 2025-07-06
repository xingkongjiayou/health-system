package org.csu.healthsystem.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrelationVO {
    List<String> columns;
    List<List<Double>> correlations;
}
