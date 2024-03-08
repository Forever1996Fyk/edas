package com.ahcloud.edas.admin.biz.application.manager.login;

import com.ahcloud.edas.admin.biz.application.helper.AuthenticationHelper;
import com.ahcloud.edas.admin.biz.domain.login.LoginForm;
import com.ahcloud.edas.admin.biz.domain.login.ValidateCodeLoginForm;
import com.ahcloud.edas.uaa.starter.domain.token.AccessToken;
import com.ahcloud.edas.uaa.starter.core.service.WebSecurityTokenService;
import com.ahcloud.edas.admin.biz.infrastructure.security.token.ValidateCodeAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:58
 **/
@Component
public class ValidateCodeLoginManager extends AbstractLoginManager<ValidateCodeAuthenticationToken, AccessToken> {
    @Resource
    private AuthenticationHelper authenticationHelper;
    @Resource
    private WebSecurityTokenService webSecurityTokenService;

    @Override
    protected ValidateCodeAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return authenticationHelper.buildValidateCodeAuthenticationToken((ValidateCodeLoginForm) loginForm);
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return ValidateCodeLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
