package com.finnal.blogit.service.imp;

import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.TopicEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.service.inter.IEmailService;
import com.finnal.blogit.untils.EmailUntil;
import com.finnal.blogit.untils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private EmailUntil emailUntil;

    @Override
    @Transactional
    public void approvedPost(ArticleEntity entity,String topic, HttpServletRequest servletRequest) throws UnsupportedEncodingException, MessagingException, ParseException {
        String link = Utility.getSiteURL(servletRequest) + "/posts/" + entity.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = entity.getPublishedDate().format(formatter);
        StringBuilder message = new StringBuilder();
        String subject = "Your post has been published";
        message.append("<h3>Hello</h3>");
        message.append("<p><strong>Great. </strong>Your post has been published <3</p>");
        message.append("<p>Title post: <b>").append(entity.getTitle()).append("</b></p>");
        message.append("<p>Category: <b>").append(topic).append("</b></p>");
        message.append("<p>Published Date: <b>").append(date).append("</b></p>");
        message.append("<p>Please check it or follow this link to view. </p> <a href='").append(link).append("'>Link post.</a>");
        emailUntil.sendEmailNotification(entity.getUserAccount().getEmail(),subject, message.toString());
    }

    @Override
    @Transactional
    public void rejectPost(ArticleEntity entity,String topic, HttpServletRequest servletRequest) throws UnsupportedEncodingException, MessagingException {
        StringBuilder message = new StringBuilder();
        String subject = "Your post has been rejected";
        message.append("<h3>Hello</h3>");
        message.append("<p>We are so sorry. Your post is not eligible to be published :((. </p>");
        message.append("<p>Title post: <b>").append(entity.getTitle()).append("</b></p>");
        message.append("<p>Category: <b>").append(topic).append("</b></p>");
        message.append("<p>Don't give up, try your best for the next post. Fighting Fighting !!</p>");
        emailUntil.sendEmailNotification(entity.getUserAccount().getEmail(),subject, message.toString());
    }

    @Override
    @Transactional
    public void newPostSubmitted(ArticleEntity entity, String topic, List<String> emails,
                                 HttpServletRequest servletRequest)
            throws UnsupportedEncodingException, MessagingException, ParseException {
//        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = entity.getModifiedDate().format(formatter);
        String link = Utility.getSiteURL(servletRequest) + "/admin/post/" + entity.getId();
        String linkAuthor = Utility.getSiteURL(servletRequest) + "/author/" + entity.getUserAccount().getId() + "?page=1";
        String nameAuthor = entity.getUserAccount().getUserDetailEntity().getFirstName() + " " + entity.getUserAccount().getUserDetailEntity().getLastName();
        StringBuilder message = new StringBuilder();
        String subject = "A new post has been submitted";
        message.append("<h3>Hello Admin</h3>");
        message.append("<p>You are receiving this email because a new post has been submitted by <a href='");
        message.append(linkAuthor).append("'>").append(nameAuthor).append("</a></p>");
        message.append("<p>Title post: <b>").append(entity.getTitle()).append("</b></p>");
        message.append("<p>Category: <b>").append(topic).append("</b></p>");
        message.append("<p>Date: <b>").append(date).append("</b></p>");
        message.append("<p>Please check it or follow this link to view detail. </p> <a href='").append(link).append("'>Link detail.</a>");
        emailUntil.sendListEmailNotification(emails,subject, message.toString());
    }

    @Override
    @Transactional
    public void resetPassword(HttpServletRequest servletRequest, UserAccountEntity userAccount, String token) throws UnsupportedEncodingException, MessagingException {
        String link = Utility.getSiteURL(servletRequest) + "/resetPassword?token=" + token;
        StringBuilder message = new StringBuilder();
        String subject = "Reset Password";
        message.append("<h3>Hello</h3>");
        message.append("<p>You are receiving this email because we received a password reset request for your account.</p>");
        message.append("<p>Follow this link to reset your account");
        message.append(" <a href='").append(link).append("'>link to reset password</a></p>");
        emailUntil.sendEmailNotification(userAccount.getEmail(), subject, message.toString());
    }

    @Override
    @Transactional
    public void confirmAccount(HttpServletRequest servletRequest, UserAccountEntity userAccount) throws UnsupportedEncodingException, MessagingException {
        StringBuilder message = new StringBuilder();
        String link = Utility.getSiteURL(servletRequest) + "/confirmAccount?token=" + userAccount.getToken();
        String subject = "Confirm Account";
        message.append("<h3>Hello</h3>");
        message.append("<p>You are receiving this email because we received a creation account request.</p>");
        message.append("<p>Follow this link to confirm your account");
        message.append(" <a href='").append(link).append("'>link to confirm account</a></p>");
        emailUntil.sendEmailNotification(userAccount.getEmail(), subject, message.toString());
    }


}
