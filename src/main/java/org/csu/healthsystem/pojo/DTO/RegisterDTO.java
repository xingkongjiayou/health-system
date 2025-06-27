package org.csu.healthsystem.pojo.DTO;

import jakarta.validation.Valid;
import lombok.Data;
import org.csu.healthsystem.pojo.DO.SysRole;
import org.csu.healthsystem.pojo.DO.User;

import java.util.List;

@Data
public class RegisterDTO {
    @Valid private User user;
    @Valid private List<SysRole> roles;
}
