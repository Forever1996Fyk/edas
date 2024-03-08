package com.ahcloud.edas.admin.biz.domain.role.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 15:37
 **/
@Data
public class SysRoleAddForm {

    /**
     * 角色编码
     */
    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色类型
     */
    private Integer roleType;
}
