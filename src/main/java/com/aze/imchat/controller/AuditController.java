package com.aze.imchat.controller;


import com.aze.imchat.constants.SecurityConstants;
import com.aze.imchat.context.SecurityContextHolder;
import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.entity.param.ApplyFriendParam;
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


    /**
     * 申请朋友
     *
     * @param applyFriendParam 应用朋友参数
     * @return {@link R}
     */
    @PostMapping
    public R applyFriend(@RequestBody ApplyFriendParam applyFriendParam){

        return auditService.applyFriend(applyFriendParam);

    }

    /**
     * 朋友申请列表
     *
     * @return {@link R}
     */
    @GetMapping("getFriendApplyList")
    private R friendApplyList(){
        LoginUser loginUser = SecurityContextHolder.getLoginUser();
       return auditService.friendApplyList(loginUser.getUserId());
    }

}
