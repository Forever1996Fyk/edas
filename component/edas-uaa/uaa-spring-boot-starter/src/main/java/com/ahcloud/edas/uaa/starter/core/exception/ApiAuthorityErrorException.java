package com.ahcloud.edas.uaa.starter.core.exception;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-24 17:04
 **/
@Getter
public class ApiAuthorityErrorException extends AccessDeniedException {

    private ErrorCode errorCode;

    public ApiAuthorityErrorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiAuthorityErrorException(String msg) {
        super(msg);
    }

    public ApiAuthorityErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
