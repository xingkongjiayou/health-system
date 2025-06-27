package org.csu.healthsystem.pojo.DO;

import lombok.Data;

@Data
public class SysRole {
    private Integer id;
    private String roleName;
    private String roleCode;
    private String description;
    private Integer status;
}
