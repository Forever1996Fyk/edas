package com.ahcloud.edas.common.exception;


import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @Description 业务异常
 * @Author YuKai Fan
 * @Date 2021/9/27 14:26
 * @Version 1.0
 */
@Getter
public class BizException extends BaseException {
    private static final long serialVersionUID = 4705328637119420414L;

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    private String errorMessage;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }

    public BizException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
        this.errorMessage = super.getMessage();
        this.errorCode = errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return this.errorCode.getCode();
    }
}
