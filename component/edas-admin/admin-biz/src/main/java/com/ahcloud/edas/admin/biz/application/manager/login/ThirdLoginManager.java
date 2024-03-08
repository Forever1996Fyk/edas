package com.ahcloud.edas.admin.biz.application.manager.login;

import com.ahcloud.edas.admin.biz.application.helper.AuthenticationHelper;
import com.ahcloud.edas.admin.biz.domain.login.LoginForm;
import com.ahcloud.edas.admin.biz.domain.login.ThirdCodeLoginForm;
import com.ahcloud.edas.admin.biz.infrastructure.security.token.ThirdAuthenticationToken;
import com.ahcloud.edas.uaa.starter.domain.token.AccessToken;
import org.springframework.stereotype.Component;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/2 16:39
 **/
@Component
public class ThirdLoginManager extends AbstractLoginManager<ThirdAuthenticationToken, AccessToken> {
    @Override
    protected ThirdAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return AuthenticationHelper.buildThirdAuthenticationToken((ThirdCodeLoginForm) loginForm);
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return ThirdCodeLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
