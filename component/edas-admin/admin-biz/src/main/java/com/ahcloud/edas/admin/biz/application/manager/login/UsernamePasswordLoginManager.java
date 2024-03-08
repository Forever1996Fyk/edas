package com.ahcloud.edas.admin.biz.application.manager.login;

import com.ahcloud.edas.admin.biz.application.helper.AuthenticationHelper;
import com.ahcloud.edas.admin.biz.domain.login.LoginForm;
import com.ahcloud.edas.admin.biz.domain.login.UsernamePasswordLoginForm;
import com.ahcloud.edas.uaa.starter.domain.token.AccessToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:39
 **/
@Component
public class UsernamePasswordLoginManager extends AbstractLoginManager<UsernamePasswordAuthenticationToken, AccessToken> {
    @Resource
    private AuthenticationHelper authenticationHelper;



    @Override
    protected UsernamePasswordAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return authenticationHelper.buildUsernamePasswordAuthenticationToken((UsernamePasswordLoginForm) loginForm);
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return UsernamePasswordLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
