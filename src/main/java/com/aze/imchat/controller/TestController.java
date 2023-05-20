package com.aze.imchat.controller;

import com.aze.imchat.constants.SecurityConstants;
import com.aze.imchat.context.SecurityContextHolder;
import com.aze.imchat.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("test")
    public R test(){
        String value= SecurityContextHolder.get(SecurityConstants.LOGIN_USER);
        return R.ok();
    }
}
