package com.ahcloud.edas.admin.biz.infrastructure.security.third;

import com.ahcloud.edas.admin.biz.domain.user.third.ThirdUserInfo;
import com.ahcloud.edas.admin.biz.domain.user.third.request.BaseRequest;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/2 17:23
 **/
public interface ThirdAuthService {

    /**
     * 获取第三方用户信息
     * @param code
     * @return
     */
    ThirdUserInfo getThirdUserInfoByCode(String code);
}
