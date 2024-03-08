package com.ahcloud.edas.uaa.starter.core.exception;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * @program: permissions-center
 * @description: 写类型的api无法访问异常
 * @author: YuKai Fan
 * @create: 2022-08-23 14:48
 **/
@Getter
public class WriteApiCannotAccessException extends AuthenticationException {

    private final ErrorCode errorCode;

    public WriteApiCannotAccessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
