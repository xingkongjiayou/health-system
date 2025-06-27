package org.csu.healthsystem.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private Boolean status;
    private List<Role> role;
    private String avatar;
    private Integer deptId;
    private Date createTime;
    private Date updateTime;
}

