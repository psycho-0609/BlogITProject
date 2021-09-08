package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.TopicEntity;
import com.finnal.blogit.entity.UserAccountEntity;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IEmailService {

     void approvedPost(ArticleEntity entity, String topic, HttpServletRequest servletRequest) throws UnsupportedEncodingException, MessagingException;
     void rejectPost(ArticleEntity entity, String topic, HttpServletRequest servletRequest) throws UnsupportedEncodingException, MessagingException;
     void newPostSubmitted(ArticleEntity entity, String topic, List<String> emails, HttpServletRequest servletRequest) throws UnsupportedEncodingException, MessagingException;
     void resetPassword(HttpServletRequest request, UserAccountEntity entity, String token) throws UnsupportedEncodingException, MessagingException;
     void confirmAccount(HttpServletRequest request, UserAccountEntity entity) throws UnsupportedEncodingException, MessagingException;
}
