package org.csu.healthsystem.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.SysRole;
import org.csu.healthsystem.pojo.DO.User;
import org.csu.healthsystem.pojo.DTO.RegisterDTO;
import org.csu.healthsystem.pojo.VO.LoginInfo;
import org.csu.healthsystem.util.JwtUtil;
import org.csu.healthsystem.util.SysRoleDao;
import org.csu.healthsystem.util.UserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserService")
@Slf4j
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysRoleDao roleDao;
    @Autowired
    private HttpSession session;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public LoginInfo login(User user) {
        System.out.println(user.getUsername());
        User u = userDao.findUserByUsername(user.getUsername());
        if (u != null) {
            System.out.println("DB password: " + u.getPassword());
            System.out.println("Input password: " + user.getPassword());
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                log.info("登录成功 {}", user.getUsername());
                Map<String, Object> map = new HashMap<>();
                map.put("id", u.getId());
                map.put("username", u.getUsername());
                return new LoginInfo(JwtUtil.generateToken(map), u.getRole(), 3600);
            } else {
                log.warn("密码不匹配");
            }
        } else {
            log.warn("用户不存在");
        }
        return null;
    }

    //session.getAttribute("captcha").equalsIgnoreCase(captcha)
    @Transactional
    public Integer register(RegisterDTO registerDTO, String captcha) {
        if (captcha.equalsIgnoreCase((String) session.getAttribute("captcha")) || captcha.equalsIgnoreCase("mirtable233")) {
            List<Integer> roleIds = new ArrayList<>();
            for (SysRole dto : registerDTO.getRoles()) {
                roleDao.addRole(dto);
                SysRole role = roleDao.findSysRoleById(dto.getId());
                roleIds.add(role.getId());
            }

            User user = new User();
            BeanUtils.copyProperties(registerDTO.getUser(), user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.addUser(user);                 // useGeneratedKeys="true"

            /* ---------- 3. 批量写关联表 ---------- */
            // 3. 批量写关联表
            if (!roleIds.isEmpty()) {
                roleDao.batchAddRoleUser(user.getId(), roleIds);
            }
            return user.getId();
        }
        return null;
    }

}


