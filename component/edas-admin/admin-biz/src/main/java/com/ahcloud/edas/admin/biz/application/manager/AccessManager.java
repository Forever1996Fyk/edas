package com.ahcloud.edas.admin.biz.application.manager;

import com.ahcloud.edas.admin.biz.application.helper.EdasUserHelper;
import com.ahcloud.edas.admin.biz.domain.user.UserAuthorityDTO;
import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description: 认证管理器
 * @author: YuKai Fan
 * @create: 2021-12-17 15:35
 **/
@Slf4j
@Component
public class AccessManager {
    @Resource
    private UserAuthManager userAuthManager;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置用户信息缓存
     *  key: userId
     *  value: localUser
     * @param userId
     * @param userDetails
     */
    public void setUserAuthRedisCache(Long userId, UserDetails userDetails) {
        redisTemplate.opsForHash().put(AdminConstants.TOKEN_USER_KEY, String.valueOf(userId), userDetails);
    }
    /**
     * 重新构造当前登录人信息, 并放入缓存
     * @param userId
     */
    public void updateUserCache(Long userId) {
        UserAuthorityDTO userAuthorityDTO = userAuthManager.createUserAuthorityByUserId(userId);
        EdasUser edasUser = EdasUserHelper.buildEdasUser(userAuthorityDTO);
        this.setUserAuthRedisCache(userId, edasUser);
    }
}
