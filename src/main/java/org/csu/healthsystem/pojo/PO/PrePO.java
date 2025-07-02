package org.csu.healthsystem.pojo.PO;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrePO<T> {
    List<Double> series;
    @JsonUnwrapped
    T parameter;
}
