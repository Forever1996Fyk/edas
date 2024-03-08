package com.ahcloud.edas.admin.biz.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @program: permissions-center
 * @description: 用户角色信息dto
 * @author: YuKai Fan
 * @create: 2022-06-27 07:45
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserScopeInfoDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户角色集合
     */
    private Set<RoleDTO> roleSet;

    /**
     * 用户部门信息
     */
    private DeptDTO deptDTO;
}
