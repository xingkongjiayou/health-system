package org.csu.healthsystem.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.util.CaptchaUtil;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RequestMapping("/api")
@RestController
public class CaptchaController {
    // 图片验证码的核心逻辑就是理解image中的src请求实际上就是一次get

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) {
        // 调用之前写的 CaptchaUtil 类生成验证码图片并返回验证码文本
        String captchaText = CaptchaUtil.createCaptchaImage(response);
        // 将验证码文本存入 session，用于后续验证
        session.setAttribute("captcha", captchaText);
    }

    @PostMapping("/cloud")
    public CommonResponse<String> verifyCaptcha(@RequestParam("cf-turnstile-response") String token) {
        String secretKey = "0x4AAAAAABizYM04ayvZj27kUhVUjWDop84";

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", secretKey);
        params.add("response", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params);
        Map response = restTemplate.postForObject(
                "https://challenges.cloudflare.com/turnstile/v0/siteverify",
                request,
                Map.class
        );
        if ((Boolean) response.get("success")) {
            return CommonResponse.createForSuccess("验证通过");
        } else {
            return CommonResponse.createForError(403,"验证失败");
        }
    }


}
