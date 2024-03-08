package com.ahcloud.edas.admin.biz.domain.user.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 14:21
 **/
@Data
public class SysUserUpdateForm {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 用户昵称
     */
    @NotEmpty(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String name;

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
     * 部门编码
     */
    @NotEmpty(message = "部门编码不能为空")
    private String deptCode;

    /**
     * 数据权限类型
     */
    private Integer dataScope;
}
