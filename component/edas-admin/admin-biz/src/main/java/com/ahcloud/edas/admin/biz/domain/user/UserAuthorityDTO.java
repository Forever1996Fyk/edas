package com.ahcloud.edas.admin.biz.domain.user;

import com.ahcloud.edas.uaa.starter.core.constant.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @program: permissions-center
 * @description: 权限用户实体
 * @author: YuKai Fan
 * @create: 2021-12-22 18:11
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorityDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 账号状态枚举
     */
    private UserStatusEnum userStatusEnum;

    /**
     * 用户权限集合
     */
    private Set<String> authorities;

    /**
     * 角色编码集合
     */
    private Set<String> roleCodeSet;

    /**
     * 菜单集合
     */
    private Set<Long> menuIdSet;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;
}
