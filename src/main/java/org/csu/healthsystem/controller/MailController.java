package org.csu.healthsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DTO.VerificationDTO;
import org.csu.healthsystem.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("api/mail")
@RestController
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/code")
    public CommonResponse<String> sendCode(@RequestBody VerificationDTO verificationDTO) {
        mailService.verify(verificationDTO);
        return CommonResponse.createForSuccess("邮件发送成功");
    }

    @PostMapping("/verify")
    public CommonResponse<String> verify(@RequestParam String email,
                                         @RequestParam String code) {
        if(mailService.validate(email, code).equals("success")) {
            return CommonResponse.createForSuccess("验证成功");
        }
        else if(mailService.validate(email, code).equals("fail")) {
            return CommonResponse.createForError("验证码错误，请重新输入");
        }
        else return CommonResponse.createForError("验证码已过期");
    }
}
