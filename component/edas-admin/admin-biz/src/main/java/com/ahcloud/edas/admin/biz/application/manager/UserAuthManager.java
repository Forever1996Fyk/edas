package com.ahcloud.edas.admin.biz.application.manager;

import com.ahcloud.edas.admin.biz.application.helper.UserAuthorityHelper;
import com.ahcloud.edas.admin.biz.application.service.SysApiService;
import com.ahcloud.edas.admin.biz.application.service.ext.SysUserExtService;
import com.ahcloud.edas.admin.biz.application.service.ext.SysUserRoleExtService;
import com.ahcloud.edas.admin.biz.domain.user.UserAuthorityDTO;
import com.ahcloud.edas.admin.biz.domain.user.dto.UserScopeInfoDTO;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.*;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description: 用户权限管理
 * @author: YuKai Fan
 * @create: 2021-12-22 18:09
 **/
@Slf4j
@Component
public class UserAuthManager {

    @Resource
    private SysApiService sysApiService;
    @Resource
    private SysUserExtService sysUserExtService;
    @Resource
    private UserAuthorityHelper userAuthorityHelper;
    @Resource
    private SysUserRoleExtService sysUserRoleExtService;

    /**
     * 根据用户账号 构造用户权限实体数据
     *
     * @param username
     * @return
     */
    public UserAuthorityDTO createUserAuthorityByUsername(String username) {
        /*
        1. 根据账号查询用户信息
         */
        SysUser sysUser = sysUserExtService.getOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getAccount, username)
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysUser)) {
            log.warn("UserAuthManager[createUserAuthorityByUsername] query SysUser is empty by account={}", username);
            throw new BizException(ErrorCodeEnum.AUTHENTICATION_ACCOUNT_ERROR, username);
        }
        return getUserAuthorityDTO(sysUser);
    }


    /**
     * 根据用户id 构造用户权限实体数据
     *
     * @param userId
     * @return
     */
    public UserAuthorityDTO createUserAuthorityByUserId(Long userId) {
        /*
        1. 根据账号查询用户信息
         */
        SysUser sysUser = sysUserExtService.getOneByUserId(userId);
        if (Objects.isNull(sysUser)) {
            log.warn("UserAuthManager[createUserAuthorityByUsername] query SysUser is empty by userId={}", userId);
            throw new BizException(ErrorCodeEnum.AUTHENTICATION_USER_ID_ERROR, String.valueOf(userId));
        }
        return getUserAuthorityDTO(sysUser);
    }

    private UserAuthorityDTO getUserAuthorityDTO(SysUser sysUser) {
        /*
        数据转换成用户权限实体
        该操作查询的次数较多, 根据实际的请求并发数进行不同的技术优化
        1、提前把用户对应的menu, api, role数据加载到缓存中(内存/redis)
        2、使用线程池进行异步查询, 最后汇总
         */
        UserAuthorityDTO userAuthorityDTO = userAuthorityHelper.convertDTO(sysUser);
        if (userAuthorityHelper.isAdmin(sysUser.getUserId())) {
            List<SysApi> apiList = sysApiService.list(
                    new QueryWrapper<SysApi>().lambda()
                            .eq(SysApi::getDeleted, DeletedEnum.NO.value)
            );
            Set<String> apiCodeSet = CollectionUtils.convertSet(apiList, SysApi::getApiCode);
            userAuthorityDTO.setAuthorities(apiCodeSet);
        } else {
            Set<String> roleCodeSet = userAuthorityHelper.buildRoleCodeSet(sysUser.getUserId());
            Set<Long> menuIdSet = userAuthorityHelper.buildMenuIdSet(sysUser.getUserId(), roleCodeSet);
            Set<String> apiCodeSet = userAuthorityHelper.buildApiCodeSet(sysUser.getUserId(), roleCodeSet, menuIdSet);
            userAuthorityDTO.setAuthorities(apiCodeSet);
            userAuthorityDTO.setRoleCodeSet(roleCodeSet);
            userAuthorityDTO.setMenuIdSet(menuIdSet);
        }
        return userAuthorityDTO;
    }

    /**
     * 获取用户数据权限信息
     * @param userId
     * @return
     */
    public UserScopeInfoDTO getUserRoleInfoByUserId(Long userId) {
        SysUser sysUser = sysUserExtService.getOneByUserId(userId);
        return userAuthorityHelper.buildUserScopeInfo(sysUser);
    }

    /**
     * 批量获取用户数据权限信息
     * @param userIds
     * @return
     */
    public List<UserScopeInfoDTO> listUserRoleInfoByUserIds(Collection<Long> userIds) {
        return userAuthorityHelper.buildUserScopeInfoList(userIds);
    }

    /**
     * 根据角色编码获取所有的用户id集合
     * @param roleCodes
     * @return
     */
    public Set<Long> listUserIdByRoleCodes(Set<String> roleCodes) {
        if (CollectionUtils.isEmpty(roleCodes)) {
            return new HashSet<>();
        }
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .in(SysUserRole::getRoleCode, roleCodes)
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );
        return CollectionUtils.convertSet(sysUserRoleList, SysUserRole::getUserId);
    }

    /**
     * 根据部门编码, 获取所有用户集合 todo
     *
     * 直接根据部门编码查询即可, 此方法不分部门领导与部门成员
     *
     * @param deptCodes
     * @return
     */
    public List<SysUser> listUserIdByDeptCodes(Set<String> deptCodes) {
        return null;
    }
}
