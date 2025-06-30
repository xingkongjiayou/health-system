package org.csu.healthsystem.pojo.DO;

import lombok.Data;

@Data
public class Condition {
    private Integer eq;
    private Integer gt;
    private Integer gte;
    private Integer lt;
    private Integer lte;
}
