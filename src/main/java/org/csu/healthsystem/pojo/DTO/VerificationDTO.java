package org.csu.healthsystem.pojo.DTO;

import jakarta.validation.Valid;
import lombok.Data;
import org.csu.healthsystem.pojo.DO.SysRole;
import org.csu.healthsystem.pojo.DO.User;


@Data
public class VerificationDTO {
    @Valid
    private User user;
    @Valid private SysRole role;
}
