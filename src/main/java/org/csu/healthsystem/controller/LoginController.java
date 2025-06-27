package org.csu.healthsystem.controller;



import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.User;
import org.csu.healthsystem.pojo.VO.LoginInfo;
import org.csu.healthsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/auth")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public CommonResponse<LoginInfo> login(@RequestBody User user) {
        log.info("登录：{}", user);
        LoginInfo loginInfo= userService.login(user);
        if(loginInfo!=null) {
            return CommonResponse.createForSuccess(loginInfo);
        }
        return CommonResponse.createForError("用户名或密码错误");
    }
    @PostMapping
    public CommonResponse<Integer> register(@RequestBody User user,@RequestParam String captcha) {
        log.info("注册：{}", user);
        Integer userId=userService.register(user,captcha);
        if(userId!=null) {
            return CommonResponse.createForSuccess(userId);
        }
        return CommonResponse.createForError("注册失败");
    }
}


