package com.ahcloud.edas.uaa.starter.core.filter;

import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.uaa.starter.core.constant.enums.UaaRetCodeEnum;
import com.ahcloud.edas.uaa.starter.core.exception.SecurityErrorException;
import com.ahcloud.edas.uaa.starter.core.access.AccessExecutor;
import com.ahcloud.edas.uaa.starter.core.service.WebSecurityTokenService;
import com.ahcloud.edas.uaa.starter.core.token.InMemoryAuthenticationToken;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 请求验证过滤器
 * @author: YuKai Fan
 * @create: 2021-12-22 15:28
 **/
@Slf4j
@Component
public class RedisAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private AccessExecutor accessExecutor;
    @Resource
    private WebSecurityTokenService tokenService;

    /**
     * 校验失败处理器
     */
    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        从 request 中提取到token，获取当前登录用户
         */
        try {
            String token = tokenService.getToken(request);
            EdasUser edasUser = tokenService.getEdasUser(token);
            log.info("RedisAuthenticationTokenFilter[doFilterInternal] 根据token获取当前登录用户信息 localUser:{}", JsonUtils.toJsonString(edasUser));
            if (ObjectUtils.isNotEmpty(edasUser)) {
                // 重新刷新token过期时间
                tokenService.refreshToken(token);
                // 判断当前登录人信息是否可用
                if (!edasUser.isEnabled()) {
                    throw new SecurityErrorException(UaaRetCodeEnum.AUTHENTICATION_ACCOUNT_DISABLED);
                }
                // 判断当前上下文是否存储认证信息
                if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) || accessExecutor.isUserAuthChanged(edasUser)) {
                    InMemoryAuthenticationToken authenticationToken = buildInMemoryAuthenticationToken(edasUser);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    /*
                    设置到上下文 方便获取当前登录人信息 可自定义策略SecurityContextHolderStrategy
                    通过spring.security.strategy 设置对应的策略类全限定名(类路径)即可
                     */
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    accessExecutor.resetUserAuthChangedMark(edasUser, false);
                }
            }
        }  catch (AuthenticationException e) {
            log.error("RedisAuthenticationTokenFilter[doFilterInternal] 根据token认证用户信息异常 exception:{}", Throwables.getStackTraceAsString(e));
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }


    /**
     * 构建内存存储认证token
     * @param userDetails
     * @return
     */
    public InMemoryAuthenticationToken buildInMemoryAuthenticationToken(UserDetails userDetails) {
        return new InMemoryAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
