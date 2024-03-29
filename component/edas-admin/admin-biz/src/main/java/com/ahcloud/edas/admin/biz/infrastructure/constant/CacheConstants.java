package com.ahcloud.edas.admin.biz.infrastructure.constant;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 18:24
 **/
public class CacheConstants {

    /**
     * 登录短信验证码前缀
     */
    public static final String LOGIN_SMS_CODE_PREFIX = "login:sms:code:";

    /**
     * 注册短信验证码前缀
     */
    public static final String REGISTER_SMS_CODE_PREFIX = "register:sms:code:";

    /**
     * 忘记密码短信验证码前缀
     */
    public static final String FORGET_PASSWORD_SMS_CODE_PREFIX = "forget:sms:code:";

    /**
     * 登录邮箱验证码前缀
     */
    public static final String LOGIN_EMAIL_CODE_PREFIX = "login:email:code:";

    /**
     * api权限缓存前缀
     */
    public static final String AUTHORITY_API_PREFIX = "authority_api";

    /**
     * im分布式session 前缀
     */
    public static final String IM_DISTRIBUTION_SESSION_PREFIX = "im:user:session:";

    /**
     * im监听节点 前缀
     */
    public static final String IM_LISTENER_NODE_PREFIX = "im:listener:node:";

    /**
     * 离线消息列表 前缀
     */
    public static final String OFFLINE_MESSAGE_LIST_PREFIX = "offline:message:list:";

    /**
     * 聊天室 前缀
     */
    public static final String CHAT_ROOM_PREFIX = "online:chatroom:";

    /**
     * 群组成员 前缀
     */
    public static final String GROUP_MEMBER_KE_PREFIX = "group:member:";
}
