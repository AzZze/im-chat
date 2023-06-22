package com.aze.imchat.controller;


import com.aze.imchat.service.ContactService;
import com.aze.imchat.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aze
 * @since 2023-06-22
 */
@RestController
@RequestMapping("/imchat/contact")
public class ContactController {


    @Autowired
    private ContactService contactService;
    @PostMapping
    public R findFriend(@RequestParam String userIdentify){

        return contactService.findFriend(userIdentify);
    }
}
