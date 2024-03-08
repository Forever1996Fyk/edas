package com.ahcloud.edas.admin.biz.application.helper;

import com.ahcloud.edas.uaa.starter.domain.authority.DefaultAuthority;
import com.ahcloud.edas.uaa.starter.domain.AuthorityInfo;
import com.ahcloud.edas.uaa.starter.domain.BaseUserInfo;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import com.ahcloud.edas.admin.biz.domain.user.UserAuthorityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 当前登录用户信息
 * @author: YuKai Fan
 * @create: 2021-12-23 13:56
 **/
@Slf4j
@Component
public class EdasUserHelper {

    public static EdasUser buildEdasUser(UserAuthorityDTO userAuthorityDTO) {
        BaseUserInfo baseUserInfo = BaseUserInfo.builder()
                .userId(userAuthorityDTO.getUserId())
                .name(userAuthorityDTO.getName())
                .avatar(userAuthorityDTO.getAvatar())
                .build();
        AuthorityInfo authorityInfo = AuthorityInfo.builder()
                .authorities(buildDefaultAuthorities(userAuthorityDTO.getAuthorities()))
                .menuIdSet(userAuthorityDTO.getMenuIdSet())
                .roleCodeSet(userAuthorityDTO.getRoleCodeSet())
                .build();
        return EdasUser.builder()
                .username(userAuthorityDTO.getAccount())
                .password(userAuthorityDTO.getPassword())
                .userInfo(baseUserInfo)
                .authorityInfo(authorityInfo)
                .enabled(Boolean.TRUE)
                .credentialsNonExpired(Boolean.TRUE)
                .accountNonExpired(Boolean.TRUE)
                .accountNonLocked(Boolean.TRUE)
                .build();
    }

    /**
     * 构建默认权限实体集合
     * @param authorities
     * @return
     */
    public static Set<DefaultAuthority> buildDefaultAuthorities(Set<String> authorities) {
        return authorities.stream()
                .map(EdasUserHelper::buildDefaultAuthority)
                .collect(Collectors.toSet());
    }

    /**
     * 构建默认权限实体
     * @param authority
     * @return
     */
    public static DefaultAuthority buildDefaultAuthority(String authority) {
        return new DefaultAuthority(authority);
    }
}
