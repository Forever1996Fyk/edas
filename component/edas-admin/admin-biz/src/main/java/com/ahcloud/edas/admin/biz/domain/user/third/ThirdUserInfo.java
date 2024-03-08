package com.ahcloud.edas.admin.biz.domain.user.third;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description: 统一第三方用户信息
 * @author: YuKai Fan
 * @create: 2024/2/2 17:23
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ThirdUserInfo {

    /**
     * 第三方用户id
     */
    private String uid;

    /**
     * openId
     */
    private String openId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String username;
}
