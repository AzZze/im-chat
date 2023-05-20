package com.aze.imchat.service;

import com.aze.imchat.entity.LoginUser;
import com.aze.imchat.utils.R;

public interface TokenService {
    R createToken(LoginUser loginUser);
    public LoginUser getLoginUser(String token);
}
