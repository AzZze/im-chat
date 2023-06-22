package com.aze.imchat.controller;


import com.aze.imchat.constants.SecurityConstants;
import com.aze.imchat.context.SecurityContextHolder;
import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.service.AuditService;
import com.aze.imchat.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aze
 * @since 2023-06-22
 */
@RestController
@RequestMapping("/imchat/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;


    @GetMapping("getFriendApplyList")
    private R friendApplyList(){
        LoginUser loginUser = SecurityContextHolder.getLoginUser();
       return auditService.friendApplyList(loginUser.getUserId());
    }

}
