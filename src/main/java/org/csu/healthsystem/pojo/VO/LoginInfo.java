package org.csu.healthsystem.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csu.healthsystem.pojo.DO.Role;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private String token;
    private List<Role> roles;
    private Integer expiresIn;
}
