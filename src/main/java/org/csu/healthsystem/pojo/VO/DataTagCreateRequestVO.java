package org.csu.healthsystem.pojo.VO;


import lombok.Data;

@Data
public class DataTagCreateRequestVO {
    private String tagName;
    private String tagCode;
    private String tagType;
    private String color;
    private String description;
}
