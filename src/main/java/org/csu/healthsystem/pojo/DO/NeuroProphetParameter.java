package org.csu.healthsystem.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeuroProphetParameter {
    Integer start;
    Integer steps;
//    String freq;默认年
    Boolean yearly_seasonality;
    Boolean weekly_seasonality;
    Boolean daily_seasonality;
    String seasonality_mode;
    Integer epochs;
    Float learning_rate;

}
