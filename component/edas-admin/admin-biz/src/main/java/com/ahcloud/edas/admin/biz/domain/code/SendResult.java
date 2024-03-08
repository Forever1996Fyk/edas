package com.ahcloud.edas.admin.biz.domain.code;

import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.common.domain.common.Result;
import com.ahcloud.edas.common.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 发送结果
 * @author: YuKai Fan
 * @create: 2022-01-19 15:52
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendResult<T> implements Result {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * traceId
     */
    private String traceId;

    public static SendResult<Void> ofSuccess() {
        return ofSuccess(null);
    }

    public static <T> SendResult<T> ofSuccess(T data) {
        return ofSuccess(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static SendResult<Void> ofSuccess(int code, String message) {
        return ofSuccess(code, message, null);
    }

    public static <T> SendResult<T> ofSuccess(int code, String message, T data) {
        return SendResult.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .success(true)
                .build();
    }

    public static SendResult<Void> ofFailed(int code, String message) {
        return SendResult.<Void>builder()
                .code(code)
                .message(message)
                .success(false)
                .build();
    }

    public static SendResult<Void> ofFailed(ErrorCode errorCode) {
        return SendResult.<Void>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .success(false)
                .build();
    }

    public static SendResult<Void> ofFailed() {
        return ofFailed(FAILED_CODE, FAILED_MESSAGE);
    }

    @JsonIgnore
    public boolean isFailed() {
        return !success;
    }

}
