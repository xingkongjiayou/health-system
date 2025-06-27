package org.csu.healthsystem.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.User;
import org.csu.healthsystem.pojo.VO.LoginInfo;
import org.csu.healthsystem.util.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
@Slf4j
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpSession session;
    public LoginInfo login(User user) {
        User u=userDao.findUserByUserNameAndPassword(user);
        if(u!=null) {
            log.info("aaaaaaaaaaaaaaaaaaaa");
            return new LoginInfo(" ", u.getId(), u.getRole(), 3600);
        }
        return null;
    }

    @Transactional
    public Integer register(User user,String captcha) {
        if(session.getAttribute("captcha")==captcha) {
            Integer insert= userDao.addUser(user);
            if(insert==1) {

                return insert;
            }
            else
                return null;
        }
        return null;
    }
}


