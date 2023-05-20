package com.aze.imchat.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aze.imchat.constants.CacheConstants;
import com.aze.imchat.constants.SecurityConstants;
import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.service.TokenService;
import com.aze.imchat.utils.JwtUtils;
import com.aze.imchat.utils.R;
import com.aze.imchat.utils.StringUtils;
import com.aze.imchat.utils.ip.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;



    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public R createToken(LoginUser loginUser) {

            String token = IdUtil.fastUUID();
            String userName = loginUser.getUserName();
            Long userId = loginUser.getUserId();
            loginUser.setToken(token);
            loginUser.setUserId(userId);
            loginUser.setUserName(userName);
            loginUser.setIpaddr(IpUtils.getIpAddr());
            refreshToken(loginUser);

            // Jwt存储信息
            Map<String, Object> claimsMap = new HashMap<String, Object>();
            claimsMap.put(SecurityConstants.USER_KEY, token);
            claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
            claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

            // 接口返回信息
            Map<String, Object> rspMap = new HashMap<String, Object>();
            rspMap.put("access_token", JwtUtils.createToken(claimsMap));
            rspMap.put("expires_in", expireTime);
            return R.ok().data(rspMap);
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);

        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        stringRedisTemplate.opsForValue().set(userKey, JSON.toJSONString(loginUser), expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }
    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    @Override
    public LoginUser getLoginUser(String token)
    {
        try
        {
            if (StringUtils.isNotEmpty(token))
            {
                String userkey = JwtUtils.getUserKey(token);
              String  value = stringRedisTemplate.opsForValue().get(getTokenKey(userkey));
                LoginUser loginUser = JSONObject.parseObject(value, LoginUser.class);
                return loginUser;
            }
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

}
