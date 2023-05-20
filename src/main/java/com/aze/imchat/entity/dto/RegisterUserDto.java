package com.aze.imchat.entity.dto;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String email;
    private String username;
    private String firstPwd;
    private String secondPwd;
    private String verifyCode;
}
