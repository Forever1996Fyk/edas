package com.ahcloud.edas.uaa.starter.core.constant;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 15:28
 **/
public class UaaConstants {

    /**
     * 超级管理员id
     */
    public static final Long SUPER_ADMIN = 100000L;

    /**
     * redis token key prefix
     */
    public static final String TOKEN_KEY_PREFIX = "ACCESS:";

    /**
     * redis app token key prefix
     */
    public static final String APP_TOKEN_KEY_PREFIX = "APP_ACCESS:";

    /**
     * redis token key
     */
    public static final String TOKEN_USER_KEY = "ACCESS_USER_AUTHORITY";

    /**
     * token 类型
     */
    public static final String TOKEN_TYPE = "Bearer";

    /**
     * 认证请求头
     */
    public static final String TOKEN_HEAD = "Authorization";


    /**
     * 认证请求头
     */
    public static final String API_CACHE_AUTHORITY = "API_CACHE_AUTHORITY";

}
