package com.finnal.blogit.untils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class EmailUntil {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailNotification(String toEmail, String subject, String message) throws UnsupportedEncodingException, MessagingException {
        String senderName = "Support";
        String mailContent = message;

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom("luuhung411@gmail.com",senderName);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(mailContent,true);

        mailSender.send(mimeMessage);

    }


}
