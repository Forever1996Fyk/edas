package com.ahcloud.edas.uaa.starter.domain;

import com.ahcloud.edas.uaa.starter.domain.authority.DefaultAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @program: permissions-center
 * @description: 用户权限信息
 * @author: YuKai Fan
 * @create: 2022-04-05 20:11
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityInfo implements Serializable {

    /**
     * 用户权限集合
     */
    private Set<DefaultAuthority> authorities;

    /**
     * 角色编码集合
     */
    private Set<String> roleCodeSet;

    /**
     * 菜单集合
     */
    private Set<Long> menuIdSet;
}
