package com.ahcloud.edas.uaa.starter.core.service.impl;

import com.ahcloud.edas.uaa.starter.core.constant.UaaConstants;
import com.ahcloud.edas.uaa.starter.core.access.AccessExecutor;
import com.ahcloud.edas.uaa.starter.core.service.WebSecurityTokenService;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.common.util.NullUtils;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import com.ahcloud.edas.uaa.starter.domain.token.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description: 认证token 用户信息接口
 * @author: YuKai Fan
 * @create: 2021-12-21 18:05
 **/
@Slf4j
@Component
public class RedisWebSecurityTokenServiceImpl implements WebSecurityTokenService {
    @Resource
    private AccessExecutor accessExecutor;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 有效期，24小时，单位秒
     */
    private final static long EXPIRES_IN = 24 * 60 * 60;

    /**
     * 不足此时间，续期token。可理解为 12 小时内没有请求接口，则 token 失效。
     */
    private final static long RENEWAL_IN = 12 * 60 * 60;

    @Override
    public EdasUser getEdasUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        return this.getEdasUser(getToken(request));
    }

    /**
     * 根据token 获取当前登录信息
     *
     * @param accessToken
     * @return
     */
    @Override
    public EdasUser getEdasUser(String accessToken) {
        // 获取请求携带的令牌
        if (StringUtils.isNotEmpty(accessToken)) {
            String userId = redisTemplate.opsForValue().get(getTokenKey(accessToken));
            if (StringUtils.isEmpty(userId)) {
                return null;
            }
            return (EdasUser) accessExecutor.getUserDetailsFromRedis(userId);
        }
        return null;
    }

    @Override
    public void deleteEdasUser(String key) {
        redisTemplate.delete(getTokenKey(key));
    }

    @Override
    public AccessToken createToken(UserDetails userDetails) {
        log.info("RedisWebSecurityTokenServiceImpl[createToken] createToken localUser={}", JsonUtils.toJsonString(userDetails));
        String accessToken = UUID.randomUUID().toString();
        /*
        设置token缓存
        key: token
        value: userId
         */
        EdasUser edasUser = (EdasUser) userDetails;
        redisTemplate.opsForValue().set(getTokenKey(accessToken), String.valueOf(edasUser.getUserInfo().getUserId()), EXPIRES_IN, TimeUnit.SECONDS);
        accessExecutor.setUserAuthRedisCache(edasUser.getUserInfo().getUserId(), edasUser);
        return AccessToken.builder()
                .accessToken(accessToken)
                .expiresIn(EXPIRES_IN)
                .build();
    }

    @Override
    public void verifyToken(String token) {
    }

    @Override
    public void refreshToken(String token) {
        String tokenKey = getTokenKey(token);
        // 获取 当前 token 剩余存活时间
        long expire = NullUtils.of(redisTemplate.getExpire(tokenKey));
        if (expire > 0 && expire < RENEWAL_IN) {
            redisTemplate.expire(tokenKey, EXPIRES_IN, TimeUnit.SECONDS);
        }
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String header = request.getHeader(UaaConstants.TOKEN_HEAD);
        if (StringUtils.isEmpty(header)) {
            return null;
        }
        // 如果以Bearer开头，则提取。
        if (header.toLowerCase().startsWith(UaaConstants.TOKEN_TYPE.toLowerCase())) {
            String authHeaderValue = header.substring(UaaConstants.TOKEN_TYPE.length()).trim();
            int commaIndex = authHeaderValue.indexOf(',');
            if (commaIndex > 0) {
                authHeaderValue = authHeaderValue.substring(0, commaIndex);
            }
            return authHeaderValue;
        }
        return null;
    }

    @Override
    public void clearToken(String token) {

    }


    private String getTokenKey(String accessToken) {
        return UaaConstants.TOKEN_KEY_PREFIX + accessToken;
    }
}
