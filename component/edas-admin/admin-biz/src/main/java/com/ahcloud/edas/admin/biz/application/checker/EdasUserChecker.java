package com.ahcloud.edas.admin.biz.application.checker;

import com.ahcloud.edas.admin.biz.domain.user.UserAuthorityDTO;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.uaa.starter.core.constant.enums.UserStatusEnum;
import com.ahcloud.edas.uaa.starter.core.exception.UserAccountErrorException;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-23 16:13
 **/
public class EdasUserChecker {

    /**
     * 校验用户权限信息
     * @param userAuthorityDTO
     */
    public static void checkUserAuthority(UserAuthorityDTO userAuthorityDTO) {
        if (Objects.isNull(userAuthorityDTO)) {
            throw new UserAccountErrorException(ErrorCodeEnum.AUTHENTICATION_ACCOUNT_ERROR);
        }
        checkUserStatus(userAuthorityDTO.getUserStatusEnum());
        checkUserAuthorities(userAuthorityDTO.getAuthorities());
    }

    /**
     * 校验用户状态
     * @param userStatusEnum
     */
    public static void checkUserStatus(UserStatusEnum userStatusEnum) {
        if (Objects.equals(userStatusEnum, UserStatusEnum.DISABLED)) {
            throw new UserAccountErrorException(ErrorCodeEnum.AUTHENTICATION_ACCOUNT_DISABLED);
        }

        if (Objects.equals(userStatusEnum, UserStatusEnum.LOG_OFF)) {
            throw new UserAccountErrorException(ErrorCodeEnum.AUTHENTICATION_ACCOUNT_LOG_OFF);
        }
    }

    /**
     * 校验用户权限
     * @param authorities
     */
    public static void checkUserAuthorities(Set<String> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            throw new UserAccountErrorException(ErrorCodeEnum.PERMISSION_DENY);
        }
    }
}
