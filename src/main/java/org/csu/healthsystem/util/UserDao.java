package org.csu.healthsystem.util;

import org.csu.healthsystem.pojo.DO.User;

public interface UserDao {
    User findUserByUserNameAndPassword(String userName, String password);
}
