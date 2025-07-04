package org.csu.healthsystem.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbatsParameter {
    Integer steps;
    List<Integer> seasonal_periods;
    Boolean use_arma_errors;
    Boolean use_box_cox;
    Boolean use_trend;
    Boolean use_damped_trend;
    Boolean show_warnings;
    Integer n_jobs;
}
