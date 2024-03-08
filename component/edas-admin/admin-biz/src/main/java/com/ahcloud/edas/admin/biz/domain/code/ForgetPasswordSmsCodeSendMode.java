package com.ahcloud.edas.admin.biz.domain.code;

import cn.hutool.core.util.ReUtil;
import com.ahcloud.edas.admin.biz.infrastructure.constant.CacheConstants;
import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;
import com.ahcloud.edas.admin.biz.infrastructure.validate.SendMode;
import com.ahcloud.edas.common.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 15:27
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordSmsCodeSendMode implements SendMode {

    /**
     * 手机号
     */
    private String sender;

    @Override
    public boolean isLegal() {
        return ReUtil.isMatch(CommonConstants.PHONE_REGEX, this.sender);
    }


    @Override
    public String getCacheKey() {
        return CacheConstants.FORGET_PASSWORD_SMS_CODE_PREFIX + this.sender;
    }
}
