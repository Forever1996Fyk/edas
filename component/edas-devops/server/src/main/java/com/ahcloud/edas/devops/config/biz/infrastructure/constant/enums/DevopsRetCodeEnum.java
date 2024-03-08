package com.ahcloud.edas.devops.config.biz.infrastructure.constant.enums;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 21:24
 **/
@Getter
public enum DevopsRetCodeEnum implements ErrorCode {
    /**
     * 公共系统响应码
     */
    SUCCESS(0,"成功"),
    UNKNOWN_ERROR(100_0_001,"未知错误"),
    SYSTEM_ERROR(100_0_002,"系统异常"),
    SYSTEM_BUSY(100_0_003, "系统繁忙,请稍后再试"),
    PARAM_MISS(100_1_001,"缺少必要参数【%s】"),

    /**
     * 公共参数响应码
     */
    PARAM_ILLEGAL(100_1_002,"参数非法"),
    PARAM_ILLEGAL_FIELD(100_1_004,"[%s]参数非法"),


    DATA_NOT_EXISTED(100_3_008, "当前数据不存在"),
    DATA_EXISTED(100_3_009, "当前数据已存在"),

    CONFIG_NOT_EXISTED(100_3_010, "配置不存在"),
    ;


    private final int code;
    private final String message;

    DevopsRetCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
