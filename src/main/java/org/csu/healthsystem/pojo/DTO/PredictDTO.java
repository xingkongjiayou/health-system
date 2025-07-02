package org.csu.healthsystem.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csu.healthsystem.pojo.DO.PredictDO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictDTO<T> {
    PredictDO predictDO;
    T parameter;
}
