package com.ahcloud.edas.uaa.starter.core.service;




import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import com.ahcloud.edas.uaa.starter.domain.token.AccessToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-21 17:20
 **/
public interface WebSecurityTokenService extends TokenService<AccessToken> {

    /**
     * 获取当前Security 用户信息
     * @param request
     * @return
     */
    EdasUser getEdasUser(HttpServletRequest request);

    /**
     * 获取当前Security 用户信息
     * @param token
     * @return
     */
    EdasUser getEdasUser(String token);

    /**
     * 删除当前Security 用户信息
     * @param key
     */
    void deleteEdasUser(String key);
}
