package com.aze.imchat.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import com.aze.imchat.constants.UserConstants;
import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.entity.User;
import com.aze.imchat.entity.dto.LoginFormDto;
import com.aze.imchat.entity.dto.RegisterUserDto;
import com.aze.imchat.entity.vo.UserFriendApplyVo;
import com.aze.imchat.exceptions.ServiceException;
import com.aze.imchat.mapper.UserMapper;
import com.aze.imchat.service.UserService;
import com.aze.imchat.utils.AESUtil;
import com.aze.imchat.utils.MD5Utils;
import com.aze.imchat.utils.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * <p>
 * user 服务实现类
 * </p>
 *
 * @author aze
 * @since 2023-05-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private  final  static String USER_EMAIL_CODE_PREFIX="users:email:code:";

    @Override
    public LoginUser login(LoginFormDto loginForm) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new ServiceException("用户名不在指定范围");
        }

        // 查询用户信息
        User userResult = lambdaQuery().eq(User::getUserName,username)
                .eq(User::getPassword, MD5Utils.md5Digest(password))
                .one();

        if (userResult==null)
        {
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(userResult,loginUser);
        loginUser.setUserId(Long.valueOf(userResult.getUserId()));

        return loginUser;

    }

    @Override
    public R registerByEmail(RegisterUserDto registerUserDto) {
        String email = registerUserDto.getEmail();
        User emailUser = lambdaQuery().eq(User::getEmail, email).one();
        if (emailUser!=null){
            throw  new ServiceException("该邮箱已被注册");
        }
        String firstPwd = registerUserDto.getFirstPwd();
        String secondPwd = registerUserDto.getSecondPwd();

        if (!firstPwd.equals(secondPwd)){
            throw  new ServiceException("两次密码不一致,请重新输入");
        }

        String verifyCode = registerUserDto.getVerifyCode();
        String code = stringRedisTemplate.opsForValue().get(USER_EMAIL_CODE_PREFIX.concat(email));
        if (!verifyCode.equals(code)){
            throw  new ServiceException("验证码输入错误");
        }
        User user = new User();
        String password = MD5Utils.md5Digest(firstPwd);
        user.setPassword(password);
        user.setUserName(registerUserDto.getUsername());
        user.setEmail(email);
        //todo
        user.setAvatar("默认头像");

        save(user);
        return R.ok().message("注册成功");
    }

    @Override
    public User getUserByEmailOrMobile(String userIdentify) {

       return lambdaQuery()
                .eq(User::getEmail, userIdentify)
                .or()
                .eq(User::getMobile, userIdentify).one();
    }

    @Override
    public User getUserById(Long id) {


        return getById(id);
    }

    @Override
    public R findFriend(String userIdentify) {
        User friend = getUserByEmailOrMobile(userIdentify);
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
