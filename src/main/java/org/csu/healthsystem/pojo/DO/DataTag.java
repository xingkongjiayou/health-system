package org.csu.healthsystem.pojo.DO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DataTag {
    private Long id;
    private String tagName;
    private String tagCode;
    private String tagType;
    private String color;
    private String description;
    private LocalDateTime createTime;
}