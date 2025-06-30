package org.csu.healthsystem.pojo.DO;

import lombok.Data;

import java.util.List;

@Data
public class Condition {
    private Integer eq;
    private Integer gt;
    private Integer gte;
    private Integer lt;
    private Integer lte;
    private List<Object> in;
    private Integer notEq;
    private List<Object> notIn;
    private String like;     // 例如 "%abc%"
    private String notLike;
}
