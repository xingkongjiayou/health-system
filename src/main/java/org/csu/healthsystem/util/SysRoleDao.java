package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Param;
import org.csu.healthsystem.pojo.DO.SysRole;
import org.csu.healthsystem.pojo.DO.User;

import java.util.List;

public interface SysRoleDao {
    Integer addRole(SysRole role);
    SysRole findSysRoleById(Integer id);
    Integer batchAddRoleUser(@Param("userId") Integer userId,
                                 @Param("roleIds") List<Integer> roleIds);

}
