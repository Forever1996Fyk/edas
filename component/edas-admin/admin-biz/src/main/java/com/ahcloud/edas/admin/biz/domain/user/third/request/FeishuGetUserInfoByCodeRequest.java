package com.ahcloud.edas.admin.biz.domain.user.third.request;

import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/2 17:27
 **/
@Data
public class FeishuGetUserInfoByCodeRequest implements BaseRequest {

    /**
     * 登录授权码
     */
    private String loginTmpCode;
}
