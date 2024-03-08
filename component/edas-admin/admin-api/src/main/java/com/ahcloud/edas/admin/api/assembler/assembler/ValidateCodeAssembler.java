package com.ahcloud.edas.admin.api.assembler.assembler;

import cn.hutool.core.util.ReUtil;
import com.ahcloud.edas.admin.biz.domain.code.LoginEmailCodeSendMode;
import com.ahcloud.edas.admin.biz.domain.code.LoginSmsCodeSendMode;
import com.ahcloud.edas.admin.biz.domain.code.form.LoginEmailCodeForm;
import com.ahcloud.edas.admin.biz.domain.code.form.LoginSmsCodeForm;
import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;
import com.ahcloud.edas.admin.biz.infrastructure.validate.SendMode;
import com.ahcloud.edas.common.constant.CommonConstants;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 22:05
 **/
@Component
public class ValidateCodeAssembler {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public LoginSmsCodeSendMode convert(LoginSmsCodeForm form) {
        return LoginSmsCodeSendMode.builder()
                .sender(form.getPhone())
                .build();
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public LoginEmailCodeSendMode convert(LoginEmailCodeForm form) {
        return LoginEmailCodeSendMode.builder()
                .sender(form.getEmail())
                .build();
    }

    /**
     * 数据转换
     * @param sender
     * @return
     */
    public SendMode convert(String sender) {
        boolean match = ReUtil.isMatch(CommonConstants.PHONE_REGEX, sender);
        if (match) {
            return LoginSmsCodeSendMode.builder()
                    .sender(sender)
                    .build();
        } else {
            return LoginEmailCodeSendMode.builder()
                    .sender(sender)
                    .build();
        }
    }
}
