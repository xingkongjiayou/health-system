package org.csu.healthsystem.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csu.healthsystem.pojo.DO.Condition;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrelationDTO {
    List<String> columns;
    Map<String, Condition> filters;
}
