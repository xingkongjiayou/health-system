package org.csu.healthsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DTO.VerificationDTO;
import org.csu.healthsystem.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/mail")
@RestController
public class MailController {
    @Autowired
    private MailService mailService;

    @GetMapping("/verify")
    public CommonResponse<String> verify(VerificationDTO verificationDTO) {
        mailService.verify(verificationDTO);
        return CommonResponse.createForSuccess("邮件发送成功");
    }
}
