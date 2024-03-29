package com.ahcloud.edas.admin.biz.domain.role.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 16:37
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleVO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 角色类型
     */
    private Integer roleType;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型名称
     */
    private String roleTypeName;
}
