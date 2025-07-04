package org.csu.healthsystem.pojo.DO;

import lombok.Data;

import java.util.List;

@Data
public class Condition {
    private String eq;
    private String gt;
    private String gte;
    private String lt;
    private String lte;
    private List<Object> in;
    private String notEq;
    private List<Object> notIn;
    private String like;     // 例如 "%abc%"
    private String notLike;
}
