package com.ahcloud.edas.admin.biz.infrastructure.security.third.impl;

import com.ahcloud.edas.admin.biz.domain.user.third.ThirdUserInfo;
import com.ahcloud.edas.admin.biz.domain.user.third.request.BaseRequest;
import com.ahcloud.edas.admin.biz.domain.user.third.request.FeishuGetUserInfoByCodeRequest;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ThirdSourceEnum;
import com.ahcloud.edas.admin.biz.infrastructure.security.third.ThirdAuthService;
import com.ahcloud.edas.admin.biz.infrastructure.security.third.ThirdAuthServiceFactory;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthFeishuRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/2 17:27
 **/
@Component
public class ThirdAuthFeishuByCodeServiceImpl implements ThirdAuthService, InitializingBean {
    private final static String CLIENT_ID = "cli_a53629a214f8500b";
    private final static String CLIENT_SECRET = "K5H3kDZ7nAKq3cbFGFobDvTZtPKZs0ij";
    private final static String REDIRECT_URI = "http://localhost:15000";

    @Override
    public ThirdUserInfo getThirdUserInfoByCode(String code) {
        AuthConfig authConfig = AuthConfig.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .ignoreCheckState(true)
                .build();
        AuthFeishuRequest feishuRequest = new AuthFeishuRequest(authConfig);
        AuthCallback authCallback = AuthCallback.builder()
                .code(code)
                .build();
        AuthResponse response = feishuRequest.login(authCallback);
        AuthUser authUser = (AuthUser) response.getData();
        // 飞书原用户数据
        JSONObject rawUserInfo = authUser.getRawUserInfo();
        // 飞书手机号格式为 +86136000993
        JSONObject data = rawUserInfo.getJSONObject("data");
        String mobile = "";
        if (Objects.nonNull(data)) {
            mobile = data.getString("mobile");
        }
        if (StringUtils.isNotBlank(mobile)) {
            // 去除前缀
            mobile = StringUtils.substring(mobile, 3);
        }
        return ThirdUserInfo.builder()
                .avatar(authUser.getAvatar())
                .uid(authUser.getUuid())
                .username(authUser.getUsername())
                .mobile(mobile)
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ThirdAuthServiceFactory.register(ThirdSourceEnum.FEISHU_QR_CODE, this);
    }
}
