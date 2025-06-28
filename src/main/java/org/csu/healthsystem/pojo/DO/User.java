package org.csu.healthsystem.pojo.DO;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
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
    private Date lastLoginTime;
    private String loginIp;
}

