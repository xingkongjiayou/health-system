package org.csu.healthsystem.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.DO.SysRole;
import org.csu.healthsystem.pojo.DO.User;
import org.csu.healthsystem.pojo.DTO.VerificationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service("MailService")
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final StringRedisTemplate redis;
    @Value("${spring.mail.username}")
    private String fromAddress;
    @Value("${mail.from.name}")
    private String fromName;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public void verify(VerificationDTO verificationDTO) throws MailException {
        User user = verificationDTO.getUser();
        SysRole role = verificationDTO.getRole();
        String toAddress = user.getEmail();
        final String code = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));
        String redisKey = "verify:email:" + toAddress;
        redis.opsForValue().set(redisKey, code, Duration.ofMinutes(2));
        MimeMessagePreparator preparatory = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            mimeMessage.setFrom(new InternetAddress(fromAddress, fromName));
            mimeMessage.setSubject("Health System 注册验证码");
            String htmlContent = buildHtmlContent(user, role,code);
            mimeMessage.setContent(htmlContent, "text/html;charset=UTF-8");
        };
        mailSender.send(preparatory);
        log.info("验证码 {} 已发送至 {}", code, toAddress);
    }


    private String buildHtmlContent(User user,SysRole role,String verificationCode) {
        return "<html>" +
                "<body style='font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 30px;'>" +
                "<div style='max-width: 600px; margin:auto; background:#fff; padding:30px; border-radius:10px; box-shadow:0 4px 12px rgba(0,0,0,.1);'>" +
                "<h2 style='color:#2E8B57; text-align:center;'>欢迎加入 Health System</h2>" +

                "<p>尊敬的 <strong>" + user.getUsername() + " " + role.getRoleName() + "</strong>，您好：</p>" +
                "<p>感谢您注册我们的平台。以下是您的<strong style='color:#2E8B57;'>注册验证码</strong>，有效期 <strong>2 分钟</strong>：</p>" +

                "<div style='text-align:center; margin:30px 0;'>" +
                "<span style='display:inline-block; font-size:28px; letter-spacing:8px; padding:12px 24px; border:2px dashed #2E8B57; border-radius:8px; font-weight:bold;'>" +
                verificationCode +
                "</span>" +
                "</div>" +

                "<p>请在验证页面输入此验证码以完成注册。如果您并未发起此请求，请忽略此邮件。</p>" +

                "<br/>" +
                "<p style='color:#999;font-size:12px; text-align:center;'>此邮件由系统自动发送，请勿直接回复。</p>" +
                "<p style='color:#999;font-size:12px; text-align:center;'>—— Health System 团队</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    public String validate(String email, String inputCode) {
            String key = "verify:email:" + email;
            String real = redis.opsForValue().get(key);
            log.info("从Redis取验证码，key = {}, value = {}", key, real);
            if (real == null) {
                return "null";
            }
            if (!real.equals(inputCode)) {
                return "fail";
            }
            redis.delete(key);
            return "success";// 一次性使用
        }


}
