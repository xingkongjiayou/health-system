package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.DO.SysRole;
import org.csu.healthsystem.pojo.DO.User;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {
    User findUserByUserNameAndPassword(User user);
    User findUserByUsername(String userName);
    Integer addUser(User user);
    User findUserById(Integer id);
}
