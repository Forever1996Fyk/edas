package com.ahcloud.edas.admin.biz.application.manager.login;

import com.ahcloud.edas.admin.biz.domain.login.LoginForm;
import com.ahcloud.edas.uaa.starter.domain.token.Token;
import com.ahcloud.edas.uaa.starter.core.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:36
 **/
@Component
public abstract class AbstractLoginManager<A extends Authentication, T extends Token> implements LoginManager {
    @Resource
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Token getToken(LoginForm loginForm) {
        A authentication = buildAuthentication(loginForm);
        Authentication authenticate = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        return tokenService.createToken(userDetails);
    }

    /**
     * 构建认证数据
     * @param loginForm
     * @return
     */
    protected abstract A buildAuthentication(LoginForm loginForm);
}
