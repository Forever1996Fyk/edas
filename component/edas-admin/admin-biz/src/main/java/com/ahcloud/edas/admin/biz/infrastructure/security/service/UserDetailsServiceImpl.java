package com.ahcloud.edas.admin.biz.infrastructure.security.service;

import com.ahcloud.edas.admin.biz.application.checker.EdasUserChecker;
import com.ahcloud.edas.admin.biz.application.helper.EdasUserHelper;
import com.ahcloud.edas.admin.biz.application.manager.UserAuthManager;
import com.ahcloud.edas.admin.biz.domain.user.UserAuthorityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-22 15:15
 **/
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserAuthManager userAuthManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthorityDTO userAuthorityDTO = userAuthManager.createUserAuthorityByUsername(username);
        /*
        1. 用户数据校验
        2. 用户状态校验
        3. 用户权限校验
         */
        EdasUserChecker.checkUserAuthority(userAuthorityDTO);
        /*
        构建当前登录用户信息
         */
        return EdasUserHelper.buildEdasUser(userAuthorityDTO);
    }
}
