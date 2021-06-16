package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.request.ChangePasswordRequest;
import com.finnal.blogit.dto.request.ResetPasswordRequest;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.RegisterResponse;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.dto.request.LoginRequest;
import com.finnal.blogit.dto.request.RegisterRequest;
import com.finnal.blogit.service.inter.IUserAccountService;
import com.finnal.blogit.untils.EmailUntil;
import com.finnal.blogit.untils.Utility;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import com.finnal.blogit.exception.api.*;
import net.bytebuddy.utility.RandomString;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/authen")
public class AuthenticationAPI {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmailUntil emailUntil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication.getName();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request, HttpServletRequest servletRequest) throws APIException, UnsupportedEncodingException, MessagingException {
        Optional<UserAccountEntity> userAccountEntity = userAccountService.findByUsername(request.getEmail());
        StringBuilder message = new StringBuilder();
        if(userAccountEntity.isPresent()){
            throw new ItemExitedException("Email is existed");
        }
        if(!request.getConfirmPass().equals(request.getPassword())){
            throw new ItemCannotEmptyException("Confirm password not match");
        }
        UserAccountEntity userAccount = userAccountService.registryAccount(request);
        String link = Utility.getSiteURL(servletRequest) +"/confirmAccount?token=" + userAccount.getToken();
        String subject = "Confirm Account";
        message.append("<h3>Hello</h3>");
        message.append("<p>You are receiving this email because we received a creation account request.</p>");
        message.append("<p>Follow this link to confirm your account");
        message.append(" <a href='"+link+"'>link to confirm account</a></p>");
        emailUntil.sendEmailNotification(userAccount.getEmail(),subject,message.toString());
        return new ResponseEntity<>(new RegisterResponse(userAccount.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<MessageDTO> resetPassword(@RequestBody ResetPasswordRequest request, HttpServletRequest servletRequest) throws APIException, UnsupportedEncodingException, MessagingException {
        Optional<UserAccountEntity> accountEntityOp = userAccountService.findByUsername(request.getEmail());
        String token = RandomString.make(45);
        String link = Utility.getSiteURL(servletRequest) +"/resetPassword?token=" + token;
        StringBuilder message = new StringBuilder();
        String subject = "Reset Password";
        if(!accountEntityOp.isPresent()){
            throw new ItemNotFoundException("Email invalid");
        }
        UserAccountEntity userAccountEntity = accountEntityOp.get();
        message.append("<h3>Hello</h3>");
        message.append("<p>You are receiving this email because we received a password reset request for your account.</p>");
        message.append("<p>Follow this link to reset your account");
        message.append(" <a href='"+link+"'>link to reset password</a></p>");
        userAccountService.updateResetToken(userAccountEntity,token);
        emailUntil.sendEmailNotification(request.getEmail(),subject,message.toString());
        return new ResponseEntity<>(new MessageDTO("Send success"),HttpStatus.OK);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<MessageDTO> updatePassword(@RequestBody ResetPasswordRequest request) throws APIException{
        Optional<UserAccountEntity> accountEntity = userAccountService.findById(request.getId());
        UserAccountEntity entity;
        if(!accountEntity.isPresent()){
            throw new ItemCanNotModifyException("Cannot change password now. Please try later.");
        }
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new ItemCannotEmptyException("Confirm password not match.");
        }
        if(request.getToken()==null || Strings.isEmpty(request.getToken())){
            throw new ItemCanNotModifyException("Cannot change password now. Please try again.");
        }
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new ItemCannotEmptyException("Confirm password is not match.");
        }
        entity = accountEntity.get();
        if(!entity.getToken().equals(request.getToken())){
            throw new ItemCanNotModifyException("Cannot change password now. Please try again.");
        }
        entity.setPassword(encoder.encode(request.getNewPassword()));
        entity.setToken(null);
        userAccountService.save(entity);
        return new ResponseEntity<>(new MessageDTO("Reset Successfully"),HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<MessageDTO> changePassword(@RequestBody ChangePasswordRequest request) throws APIException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new ItemNotFoundException("User not found");
        }
        Optional<UserAccountEntity> accountEntity = userAccountService.findById(userDetail.getId());
        UserAccountEntity entity;
        if(!accountEntity.isPresent()){
            throw new ItemNotFoundException("Not Found User");
        }
        if(request.getCurrentPassword() == null || Strings.isEmpty(request.getCurrentPassword())){
            throw new ItemCannotEmptyException("Please input complete information");
        }
        if(request.getNewPassword() == null || Strings.isEmpty(request.getNewPassword())){
            throw new ItemCannotEmptyException("Please input complete information");
        }
        if(request.getConfirmPassword() == null || Strings.isEmpty(request.getConfirmPassword())){
            throw new ItemCannotEmptyException("Please input complete information");
        }
        if(!request.getConfirmPassword().equals(request.getNewPassword())){
            throw new ItemCannotEmptyException("Confirm password not match");
        }
        entity = accountEntity.get();
        if(!encoder.matches(request.getCurrentPassword(),entity.getPassword())){
            throw new ItemCannotEmptyException("Current password not correct");
        }
        entity.setPassword(encoder.encode(request.getNewPassword()));
        userAccountService.save(entity);
        return new ResponseEntity<>(new MessageDTO("Update success"),HttpStatus.OK);
    }
}
