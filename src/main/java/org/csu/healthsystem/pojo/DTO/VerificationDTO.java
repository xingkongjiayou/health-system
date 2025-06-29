package org.csu.healthsystem.pojo.DTO;

import jakarta.validation.Valid;
import lombok.Data;
import org.csu.healthsystem.pojo.DO.SysRole;
import org.csu.healthsystem.pojo.DO.User;


@Data
public class VerificationDTO {
    private User user;
    private SysRole role;
}
