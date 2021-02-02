package com.shangma.cn.common.untils;

import com.shangma.cn.common.container.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
public class MailUtils {

    public static void sendHtmlMail(String subject, String to, String text) {
        try {
            JavaMailSender mailSender = SpringBeanUtils.getBean(JavaMailSender.class);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setFrom("罗最帅<luozuishuai@126.com>");
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("邮件发送错误：", e);
        }
    }
}
