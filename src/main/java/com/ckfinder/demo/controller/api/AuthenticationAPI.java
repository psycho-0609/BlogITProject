package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.dto.response.RegisterResponse;
import com.ckfinder.demo.entity.UserAccountEntity;
import com.ckfinder.demo.dto.request.LoginRequest;
import com.ckfinder.demo.dto.request.RegisterRequest;
import com.ckfinder.demo.exception.api.APIException;
import com.ckfinder.demo.exception.api.ItemExitedException;
import com.ckfinder.demo.service.inter.IUserAccountService;
import com.ckfinder.demo.untils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationAPI {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private UploadFileUtils fileUtils;

    @PostMapping("/api/login")
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

    @PostMapping("/api/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) throws APIException {
        UserAccountEntity userAccountEntity = userAccountService.findByUsername(request.getEmail());
        if(userAccountEntity != null){
            throw new ItemExitedException("Email is existed");
        }
        UserAccountEntity userAccount = userAccountService.registryAccount(request);
        return new ResponseEntity<>(new RegisterResponse(userAccount.getEmail()), HttpStatus.OK);
    }
}
