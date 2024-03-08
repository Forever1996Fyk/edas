package com.ahcloud.edas.admin.biz.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description: 部门信息
 * @author: YuKai Fan
 * @create: 2022-06-27 11:31
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DeptDTO {

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 部门名称
     */
    private String deptName;
}
