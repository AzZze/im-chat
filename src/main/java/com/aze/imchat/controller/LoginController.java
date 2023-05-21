package com.aze.imchat.controller;


import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.entity.dto.LoginFormDto;
import com.aze.imchat.entity.dto.RegisterUserDto;
import com.aze.imchat.service.SendEmailService;
import com.aze.imchat.service.TokenService;
import com.aze.imchat.service.UserService;
import com.aze.imchat.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * user 前端控制器
 * </p>
 *
 * @author aze
 * @since 2023-05-20
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private SendEmailService sendEmailService;

    @PostMapping()
    public R login(@RequestBody LoginFormDto loginForm) {

        LoginUser loginUser = userService.login(loginForm);
        return tokenService.createToken(loginUser);


    }

    @PostMapping("registerByEmail")
    public R registerByEmail(@RequestBody RegisterUserDto registerUserDto){

       return userService.registerByEmail(registerUserDto);
    }

    @PostMapping("sendEmail")
    public R sendEmail(String email){

        sendEmailService.sendEmail(email);
        return R.ok();
    }
}
