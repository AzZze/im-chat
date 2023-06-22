package com.aze.imchat.entity.vo;

import lombok.Data;

@Data
public class FriendAuditVo {

    private String userIdentify;
    //昵称
    private String nickName;
    //头像
    private String avatar;
    //状态
    private Integer status;
}
