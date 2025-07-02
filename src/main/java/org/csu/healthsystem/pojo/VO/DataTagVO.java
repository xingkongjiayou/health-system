package org.csu.healthsystem.pojo.VO;


import lombok.Data;

@Data
public class DataTagVO {
    private Long id;
    private String tagName;
    private String tagCode;
    private String tagType;
    private String color;
    private String description;
    private String createTime;
}
