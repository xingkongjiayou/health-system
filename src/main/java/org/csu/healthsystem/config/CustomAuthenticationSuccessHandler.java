package org.csu.healthsystem.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.csu.healthsystem.pojo.DO.User;
import org.csu.healthsystem.util.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserDao userDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = authToken.getPrincipal();

        User oauthUser = new User();
        oauthUser.setUsername(user.getAttribute("login"));
        oauthUser.setPassword("OAUTH_USER");
        // 从用户信息中提取需要的数据，比如用户名
        boolean isExist = userDao.findUserByUsername(oauthUser.getUsername())!= null;
        if(!isExist) userDao.addUser(oauthUser);         // 若没使用 OAuth2 登录过，则加入到数据库中
        // 将用户信息存入 session
        request.getSession().setAttribute("loginUser", oauthUser);

        // 重定向到首页或其他页面
        response.sendRedirect("/mainForm");
    }
}
//该处理器接管了 OAuth2 登录成功的后续处理。
//
//完成了：从 OAuth2 提供的用户信息创建或更新本地用户数据。
//
//把用户信息保存进 Session。
//
//重定向用户到首页。