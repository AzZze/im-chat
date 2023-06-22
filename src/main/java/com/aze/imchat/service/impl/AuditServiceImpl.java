package com.aze.imchat.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.aze.imchat.context.SecurityContextHolder;
import com.aze.imchat.entity.Audit;
import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.entity.User;
import com.aze.imchat.entity.param.ApplyFriendParam;
import com.aze.imchat.entity.vo.FriendAuditVo;
import com.aze.imchat.enums.AuditStatusEnum;
import com.aze.imchat.enums.BusinessTypeEnum;
import com.aze.imchat.mapper.AuditMapper;
import com.aze.imchat.service.AuditService;
import com.aze.imchat.service.UserService;
import com.aze.imchat.utils.AESUtil;
import com.aze.imchat.utils.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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


    private final static String Key="userId";
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
                 userIdentify = AESUtil.encrypt(Key, id.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            friendAuditVo.setAuditId(item.getId());
            friendAuditVo.setUserIdentify(userIdentify);
            friendAuditVo.setAvatar(user.getAvatar());
            friendAuditVo.setStatus(item.getStatus());

            return friendAuditVo;
        }).collect(Collectors.toList());

        return R.ok().data("data",result);
    }

    @Override
    public R applyFriend(ApplyFriendParam applyFriendParam) {
        String userIdentify = applyFriendParam.getUserIdentify();
        String decrypt;
        try {
            //好友的id
            decrypt = AESUtil.decrypt(Key, userIdentify);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Integer userId = Integer.valueOf(decrypt);
        Audit audit = new Audit();
        audit.setApplyReason(applyFriendParam.getApplyReason());

        audit.setAuditUserId(userId);
        audit.setApplyTime(LocalDateTime.now());
        audit.setStatus(AuditStatusEnum.NO_AUDIT.getStatus());
        LoginUser loginUser = SecurityContextHolder.getLoginUser();
        Long id = loginUser.getUserId();
        audit.setUserId(id);
        audit.setBusinessType(BusinessTypeEnum.CONTACT.getType());
        audit.setBusinessId(userId);

        boolean save = save(audit);

        return save?R.ok().message("申请成功"):R.error().message("申请失败") ;
    }
}
