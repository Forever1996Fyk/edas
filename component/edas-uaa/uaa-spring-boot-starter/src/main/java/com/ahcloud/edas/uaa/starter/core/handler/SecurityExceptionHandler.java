package com.ahcloud.edas.uaa.starter.core.handler;

import com.ahcloud.edas.common.enums.ErrorCode;
import com.ahcloud.edas.uaa.starter.core.constant.enums.UaaRetCodeEnum;
import com.ahcloud.edas.uaa.starter.core.exception.ApiAuthorityErrorException;
import com.ahcloud.edas.uaa.starter.core.exception.SecurityErrorException;
import com.ahcloud.edas.uaa.starter.core.exception.UserAccountErrorException;
import com.ahcloud.edas.uaa.starter.core.exception.WriteApiCannotAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

/**
 * @program: permissions-center
 * @description: Security异常类处理
 * @author: YuKai Fan
 * @create: 2022-01-04 15:07
 **/
public class SecurityExceptionHandler {

    public static ErrorCode extractErrorCodeEnum(AccessDeniedException e) {
        ErrorCode errorCodeEnum = UaaRetCodeEnum.UNKNOWN_PERMISSION;
        if (e instanceof ApiAuthorityErrorException) {
            errorCodeEnum = ((ApiAuthorityErrorException) e).getErrorCode();
        }
        return errorCodeEnum;
    }

    public static ErrorCode extractErrorCodeEnum(AuthenticationException e) {
        ErrorCode errorCodeEnum = UaaRetCodeEnum.UNKNOWN_PERMISSION;
        if (e instanceof SecurityErrorException) {
            errorCodeEnum = ((SecurityErrorException) e).getErrorCode();
        } else if (e instanceof UserAccountErrorException) {
            errorCodeEnum = ((UserAccountErrorException) e).getErrorCode();
        } else if (e instanceof WriteApiCannotAccessException) {
            errorCodeEnum = ((WriteApiCannotAccessException) e).getErrorCode();
        }
        return errorCodeEnum;
    }
}
