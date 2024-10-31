package com.zysblog.zysblog.common.util;

import com.zysblog.zysblog.common.exception.ApiException;
import com.zysblog.zysblog.dto.vo.Mail;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.zysblog.zysblog.common.constants.MailConstant.CODE_KEY_PREFIX;
import static com.zysblog.zysblog.common.exception.ErrorCode.MAIL_REPEAT_EXCEPTION;

@Slf4j
@Service
public class MailUtils {
//    @Autowired
//    RedisTemplate redisTemplate;
//    @Resource
//    private JavaMailSenderImpl mailSender;
//    public void sendMail(Mail mail) throws MailSendException {
//        try {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
//            if (mail.getFrom() == null || mail.getFrom().isEmpty()) {
//                mail.setFrom("xxx");
//            }
//            //邮件发信人
//            messageHelper.setFrom(mailSender.getUsername() + '(' + mail.getFrom() + ')');
//            //邮件收信人
//            messageHelper.setTo(mail.getTo().split(","));
//            //邮件主题
//            messageHelper.setSubject(mail.getSubject());
//            //邮件内容
//            messageHelper.setText(mail.getText());
//            //发送邮件
//            mailSender.send(messageHelper.getMimeMessage());
//        } catch (Exception e) {
//            log.warn("邮件发送失败：{}", e.getMessage());
//            throw new MailSendException("邮件发送失败:" + e.getMessage());
//        }
//    }
//    /**
//     * 发送验证码邮件
//     *
//     * @param mail 邮件信息
//     */
//    public void sendVerificationCode(Mail mail) {
//        // 判断当前待发送邮箱是否已经有验证码
//        String key = CODE_KEY_PREFIX + mail.getTo();
//        Integer code = (Integer) redisTemplate.opsForValue().get(key);
//        if (code != null) {
//            throw new ApiException(MAIL_REPEAT_EXCEPTION,"当前邮箱已经发送验证码");
//        }
//        // 生成随机 6位验证码
//        int idenCode = (int) ((Math.random() * 9 + 1) * 100000);
//        mail.setSubject("xxx");
//        mail.setText("验证码：" + idenCode);
//        redisTemplate.opsForValue().set(key, idenCode, 60, TimeUnit.SECONDS);
//        sendMail(mail);
//    }
}