package com.ahcloud.edas.uaa.client;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.uaa.starter.core.constant.UaaConstants;
import com.ahcloud.edas.uaa.starter.core.constant.enums.UaaRetCodeEnum;
import com.ahcloud.edas.uaa.starter.domain.BaseUserInfo;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/1 16:00
 **/
@Slf4j
public class UserUtils {

    /**
     * 获取当前edas用户信息
     * @return
     */
    public static EdasUser getEdasUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (Objects.isNull(authentication)) {
                throw new BizException(UaaRetCodeEnum.CURRENT_USER_IS_NOT_EXIST);
            }
            if (authentication instanceof AnonymousAuthenticationToken) {
                throw new BizException(UaaRetCodeEnum.ANONYMOUS_USER_HAS_NO_INFO);
            }
            return (EdasUser) authentication.getPrincipal();
        } catch (BizException e) {
            log.error("UserUtils[getEdasUser] 获取当前登录信息异常, e={}", Throwables.getStackTraceAsString(e));
            throw e;
        }
    }


    /**
     * 获取当前登录用户的基础信息
     * @return
     */
    public static BaseUserInfo getBaseUserInfo() {
        return getEdasUser().getUserInfo();
    }

    /**
     * 获取当前登录人名称
     *
     * @return
     */
    public static String getUserNameBySession() {
        try {
            BaseUserInfo baseUserInfo = getBaseUserInfo();
            return StringUtils.isBlank(baseUserInfo.getName()) ? "SYSTEM" : baseUserInfo.getName();
        } catch (Throwable throwable) {
            return "SYSTEM";
        }
    }

    /**
     * 获取当前登录人id
     *
     * @return
     */
    public static Long getUserIdBySession() {
        try {
            BaseUserInfo baseUserInfo = getBaseUserInfo();
            return baseUserInfo.getUserId();
        } catch (Throwable throwable) {
            log.error("UserUtils[getUserIdBySession] get userId from cache error, reason is {}", Throwables.getStackTraceAsString(throwable));
            return 0L;
        }
    }
}
