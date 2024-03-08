package com.ahcloud.edas.admin.biz.infrastructure.validate;

import com.ahcloud.edas.admin.biz.domain.code.LoginSmsCodeSendMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 21:07
 **/
@Slf4j
@Component
public class LoginSmsValidateCodeProvider extends AbstractValidateCodeProvider {

    @Override
    public void doSend(String code, String sender) {
        log.info("LoginSmsValidateCodeProvider[doSend] 发送短信验证码 code:{}, sender:{}", code, sender);
    }

    @Override
    public boolean support(SendMode sendMode) {
        return LoginSmsCodeSendMode.class.isAssignableFrom(sendMode.getClass());
    }
}
