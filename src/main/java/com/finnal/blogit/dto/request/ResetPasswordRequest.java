package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private Long id;
    private String email;
    private String newPassword;
    private String confirmPassword;
    private String token;
}
