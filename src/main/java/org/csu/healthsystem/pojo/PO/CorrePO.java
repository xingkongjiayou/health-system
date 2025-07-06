package org.csu.healthsystem.pojo.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrePO {
    List<String> columns;
    List<List<Double>> rows;
}
