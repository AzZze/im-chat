package com.aze.imchat.service.impl;

import com.aze.imchat.entity.Audit;
import com.aze.imchat.entity.User;
import com.aze.imchat.entity.vo.FriendAuditVo;
import com.aze.imchat.enums.BusinessTypeEnum;
import com.aze.imchat.mapper.AuditMapper;
import com.aze.imchat.service.AuditService;
import com.aze.imchat.service.UserService;
import com.aze.imchat.utils.AESUtil;
import com.aze.imchat.utils.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aze
 * @since 2023-06-22
 */
@Service
public class AuditServiceImpl extends ServiceImpl<AuditMapper, Audit> implements AuditService {

    @Autowired
    private UserService userService;

    @Override
    public R friendApplyList(Long userId) {


        List<Audit> list = lambdaQuery().eq(Audit::getAuditUserId, userId)
                .eq(Audit::getBusinessType, BusinessTypeEnum.CONTACT.getType())
                .list();

        List<FriendAuditVo> result = list.stream().map(item -> {
            FriendAuditVo friendAuditVo = new FriendAuditVo();
            Long id = item.getUserId();
            User user = userService.getUserById(id);
            friendAuditVo.setNickName(user.getNickName());
            String userIdentify;
            try {
                 userIdentify = AESUtil.encrypt("userId", id.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            friendAuditVo.setUserIdentify(userIdentify);
            friendAuditVo.setAvatar(user.getAvatar());
            friendAuditVo.setStatus(item.getStatus());

            return friendAuditVo;
        }).collect(Collectors.toList());

        return R.ok().data("data",result);
    }
}
