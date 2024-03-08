package com.ahcloud.edas.uaa.starter.core.access;

import com.ahcloud.edas.uaa.starter.configuration.UaaProperties;
import com.ahcloud.edas.uaa.starter.core.constant.UaaConstants;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/1 11:12
 **/
@Slf4j
public class AccessExecutor {
    private final UaaProperties uaaProperties;
    @Resource
    private List<AccessProvider> accessProviderList;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 路径匹配器
     */
    protected final static AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public AccessExecutor(UaaProperties uaaProperties) {
        this.uaaProperties = uaaProperties;
    }

    /**
     * Spring Security 权限校验表达式方法
     *
     * @param request
     * @param authentication
     * @return
     */
    public boolean access(HttpServletRequest request, Authentication authentication) {
         /*
        获取当前请求uri
         */
        String uri = request.getRequestURI();
        /*
        判断当前uri是否可放行
         */
        if (checkUriIsPermit(uri)) {
            return Boolean.TRUE;
        }
        int currentPosition = 0;
        int size = this.accessProviderList.size();
        for (AccessProvider accessProvider : accessProviderList) {
            if (!accessProvider.support(authentication)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("AccessManager request with %s (%d/%d)",
                        authentication.getClass().getSimpleName(), ++currentPosition, size)));
            }
            return accessProvider.access(request, authentication);
        }
        log.error("AccessManager has no Provider");
        return false;
    }

    /**
     * 校验uri是否可放行
     *
     * @param uri
     * @return
     */
    public boolean checkUriIsPermit(String uri) {
        return uaaProperties.getPermitAll().stream()
                .anyMatch(item -> PATH_MATCHER.match(item, uri));
    }


    /**
     * 重置用户权限标志
     * @param edasUser
     */
    public void resetUserAuthChangedMark(EdasUser edasUser, boolean changed) {
        edasUser.setAuthorityChanged(changed);
        setUserAuthRedisCache(edasUser.getUserInfo().getUserId(), edasUser);
    }

    /**
     * 设置用户信息缓存
     *  key: userId
     *  value: localUser
     * @param userId
     * @param userDetails
     */
    public void setUserAuthRedisCache(Long userId, UserDetails userDetails) {
        redisTemplate.opsForHash().put(UaaConstants.TOKEN_USER_KEY, String.valueOf(userId), userDetails);
    }

    /**
     * 从redis获取当前用户信息
     * @param userId
     * @return
     */
    public UserDetails getUserDetailsFromRedis(String userId) {
        Object result = redisTemplate.opsForHash().get(UaaConstants.TOKEN_USER_KEY, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return (UserDetails) result;
    }


    /**
     * 校验用户权限是否变更
     * @param edasUser
     * @return
     */
    public boolean isUserAuthChanged(EdasUser edasUser) {
        return edasUser.isAuthorityChanged();
    }

}
