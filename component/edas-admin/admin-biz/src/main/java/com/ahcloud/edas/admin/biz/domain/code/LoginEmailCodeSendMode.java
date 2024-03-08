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
 * @create: 2022-01-19 15:28
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginEmailCodeSendMode implements SendMode {
    /**
     * 邮箱
     */
    private String sender;

    @Override
    public boolean isLegal() {
        return ReUtil.isMatch(CommonConstants.EMAIL_REGEX, this.sender);
    }


    @Override
    public String getCacheKey() {
        return CacheConstants.LOGIN_EMAIL_CODE_PREFIX + this.sender;
    }


}
