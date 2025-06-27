package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.DO.SysRole;
import org.csu.healthsystem.pojo.DO.User;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {
    User findUserByUserNameAndPassword(User user);
    User findUserByUserName(String userName);
    Integer addUser(User user);
    Integer addRole(SysRole role);
    Integer addRoleUser(User user, SysRole role);
    User findUserById(Integer id);
}
