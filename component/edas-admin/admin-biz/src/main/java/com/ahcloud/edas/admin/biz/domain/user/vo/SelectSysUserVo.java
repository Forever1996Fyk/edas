package com.ahcloud.edas.admin.biz.domain.user.vo;

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
 * @create: 2022-07-01 14:44
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectSysUserVo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String name;
}
