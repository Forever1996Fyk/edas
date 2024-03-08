package com.ahcloud.edas.admin.biz.infrastructure.validate;

import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 15:25
 **/
public interface SendMode {

    /**
     * 发送方
     * @return
     */
    String getSender();

    /**
     * 校验目标是否合法
     * @return
     */
    boolean isLegal();

    /**
     * 生成验证码长度
     * @return
     */
    default Integer generateCodeLength() {
        return AdminConstants.DEFAULT_GENERATE_CODE_LENGTH;
    }

    /**
     * 前缀类型
     * @return
     */
    String getCacheKey();

}
