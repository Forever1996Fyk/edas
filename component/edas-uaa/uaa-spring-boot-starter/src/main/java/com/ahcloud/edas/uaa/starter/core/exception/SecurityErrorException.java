package com.ahcloud.edas.uaa.starter.core.exception;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * @program: permissions-center
 * @description: 自定义认证权限异常
 * @author: YuKai Fan
 * @create: 2021-12-22 17:21
 **/
@Getter
public class SecurityErrorException extends AuthenticationException {

    private ErrorCode errorCode;

    public SecurityErrorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public SecurityErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityErrorException(String msg) {
        super(msg);
    }
}
