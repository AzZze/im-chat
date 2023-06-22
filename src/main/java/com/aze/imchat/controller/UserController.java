package com.aze.imchat.controller;

import com.aze.imchat.service.UserService;
import com.aze.imchat.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imchat/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("findFriend")
    public R findFriend(@RequestParam String userIdentify){

        return userService.findFriend(userIdentify);
    }
}
