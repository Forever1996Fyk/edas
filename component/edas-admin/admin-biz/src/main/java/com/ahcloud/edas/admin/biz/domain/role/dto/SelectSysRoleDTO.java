package com.ahcloud.edas.admin.biz.domain.role.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-01 14:13
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectSysRoleDTO {
    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;
}
