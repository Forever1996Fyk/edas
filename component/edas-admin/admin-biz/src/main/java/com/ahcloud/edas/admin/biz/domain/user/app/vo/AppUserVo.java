package com.ahcloud.edas.admin.biz.domain.user.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/26 14:54
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppUserVo {

    /**
     * 唯一主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别(1: 男, 2: 女)
     */
    private Integer sex;

    /**
     * 账号状态(1:正常,2:停用,3:注销)
     */
    private Integer status;
}
