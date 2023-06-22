package com.aze.imchat.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import com.aze.imchat.entity.Contact;
import com.aze.imchat.entity.User;
import com.aze.imchat.entity.vo.UserFriendApplyVo;
import com.aze.imchat.mapper.ContactMapper;
import com.aze.imchat.service.ContactService;
import com.aze.imchat.service.UserService;
import com.aze.imchat.utils.AESUtil;
import com.aze.imchat.utils.MD5Utils;
import com.aze.imchat.utils.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aze
 * @since 2023-06-22
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {


    @Autowired
    private UserService userService;
    @Override
    public R findFriend(String userIdentify) {
        User friend = userService.getUserByEmailOrMobile(userIdentify);
        Assert.notNull(friend,"联系人不存在");
        UserFriendApplyVo userFriendApplyVo = new UserFriendApplyVo();

        String encrypt;
        try {
            encrypt=  AESUtil.encrypt("userId",friend.getUserId().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        userFriendApplyVo.setUserIdentify(encrypt);
        userFriendApplyVo.setNickName(friend.getNickName());
        userFriendApplyVo.setAvatar(friend.getAvatar());
        return R.ok().data("data",userFriendApplyVo);
    }


}
