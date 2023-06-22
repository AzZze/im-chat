package com.aze.imchat.service;

import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.entity.User;
import com.aze.imchat.entity.dto.LoginFormDto;
import com.aze.imchat.entity.dto.RegisterUserDto;
import com.aze.imchat.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * user 服务类
 * </p>
 *
 * @author aze
 * @since 2023-05-20
 */
public interface UserService extends IService<User> {

    LoginUser login(LoginFormDto loginForm);

    R registerByEmail(RegisterUserDto registerUserDto);

    User getUserByEmailOrMobile(String userIdentify);

    User getUserById(Long id);

    R findFriend(String userIdentify);

}
