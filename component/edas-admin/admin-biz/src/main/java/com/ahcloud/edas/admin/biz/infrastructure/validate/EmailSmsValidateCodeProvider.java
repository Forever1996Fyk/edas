package com.ahcloud.edas.admin.biz.infrastructure.validate;

import com.ahcloud.edas.admin.biz.domain.code.LoginEmailCodeSendMode;
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
public class EmailSmsValidateCodeProvider extends AbstractValidateCodeProvider {

    @Override
    public void doSend(String code, String sender) {
        // 发送短信验证码 todo
    }

    @Override
    public boolean support(SendMode sendMode) {
        return LoginEmailCodeSendMode.class.isAssignableFrom(sendMode.getClass());
    }
}
