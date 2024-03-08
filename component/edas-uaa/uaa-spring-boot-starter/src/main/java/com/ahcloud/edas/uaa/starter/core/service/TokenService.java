package com.ahcloud.edas.uaa.starter.core.service;

import com.ahcloud.edas.uaa.starter.domain.token.Token;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: permissions-center
 * @description: token接口
 * @author: YuKai Fan
 * @create: 2021-12-17 18:26
 **/
public interface TokenService<T extends Token> {

    /**
     * 创建token
     * @param userDetails
     * @return
     */
    T createToken(UserDetails userDetails);

    /**
     * 验证 token 合法
     * @param token
     */
    void verifyToken(String token);

    /**
     * 刷新token有效期
     * @param token
     */
    void refreshToken(String token);

    /**
     * 请求解析token
     * @param request
     * @return
     */
    String getToken(HttpServletRequest request);

    /**
     * 清除token
     *
     * @param token
     */
    void clearToken(String token);

}
