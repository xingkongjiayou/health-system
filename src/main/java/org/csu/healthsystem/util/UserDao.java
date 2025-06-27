package org.csu.healthsystem.util;

import org.apache.ibatis.annotations.Mapper;
import org.csu.healthsystem.pojo.DO.User;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {
    User findUserByUserNameAndPassword(User user);
    User findUserByUserName(String userName);
    User addUser(User user);
    User findUserById(int id);
}
