package com.ahcloud.edas.uaa.starter.core.exception;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-24 10:53
 **/
@Getter
public class UserAccountErrorException extends UsernameNotFoundException {

    private ErrorCode errorCode;

    public UserAccountErrorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public UserAccountErrorException(String msg) {
        super(msg);
    }

    public UserAccountErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
