package com.finnal.blogit.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PersonalInforRequest {
    private String base64;
    private String fileName;
    private String lastName;
    private String firstName;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    public String getBase64() {
        return base64 == null || base64.isEmpty() ? null : base64.split(",")[1];
    }
}
