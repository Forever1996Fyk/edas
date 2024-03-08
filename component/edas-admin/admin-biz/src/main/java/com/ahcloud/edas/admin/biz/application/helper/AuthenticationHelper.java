package com.ahcloud.edas.admin.biz.application.helper;

import com.ahcloud.edas.admin.biz.domain.login.ThirdCodeLoginForm;
import com.ahcloud.edas.admin.biz.domain.login.UsernamePasswordLoginForm;
import com.ahcloud.edas.admin.biz.domain.login.ValidateCodeLoginForm;
import com.ahcloud.edas.admin.biz.infrastructure.security.token.ThirdAuthenticationToken;
import com.ahcloud.edas.admin.biz.infrastructure.security.token.ValidateCodeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-17 18:02
 **/
@Component
public class AuthenticationHelper {

    /**
     * 构建密码认证token
     * @param form
     * @return
     */
    public UsernamePasswordAuthenticationToken buildUsernamePasswordAuthenticationToken(UsernamePasswordLoginForm form) {
        return new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());
    }

    /**
     * 构建密码认证token
     * @param form
     * @return
     */
    public ValidateCodeAuthenticationToken buildValidateCodeAuthenticationToken(ValidateCodeLoginForm form) {
        return new ValidateCodeAuthenticationToken(form.getSender(), form.getValidateCode());
    }

    /**
     * 构建第三方认证token
     * @param form
     * @return
     */
    public static ThirdAuthenticationToken buildThirdAuthenticationToken(ThirdCodeLoginForm form) {
        return new ThirdAuthenticationToken(form.getLoginTmpCode(), form.getSource());
    }
}
