package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest extends ResetPasswordRequest{
    private String currentPassword;
}
