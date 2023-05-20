package com.aze.imchat.interceptor;

import com.aze.imchat.constants.CacheConstants;
import com.aze.imchat.constants.SecurityConstants;
import com.aze.imchat.constants.TokenConstants;
import com.aze.imchat.context.SecurityContextHolder;
import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.exceptions.GlobalException;
import com.aze.imchat.exceptions.ServiceException;
import com.aze.imchat.service.TokenService;
import com.aze.imchat.utils.JwtUtils;
import com.aze.imchat.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = getToken(request);
        if (StringUtils.isBlank(token)){
            throw new ServiceException("用户未登录");
        }
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null)
        {
            throw new  GlobalException( "令牌已过期或验证不正确！");
        }
        String userkey = JwtUtils.getUserKey(claims);
        boolean islogin = Boolean.TRUE.equals(stringRedisTemplate.opsForValue().getOperations().hasKey(getTokenKey(userkey)));
        if (!islogin)
        {
            throw new  GlobalException( "登录状态已过期");
        }
        String userid = JwtUtils.getUserId(claims);
        String username = JwtUtils.getUserName(claims);
        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username))
        {
            throw new  GlobalException( "令牌验证失败");
        }
        LoginUser loginUser = tokenService.getLoginUser(token);
        SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.remove();;
    }

    private String getTokenKey(String token)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }


    /**
     * 获取请求token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

}
