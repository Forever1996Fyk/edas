package com.ahcloud.edas.gateway.core.infrastructure.constant.enums;


import com.ahcloud.edas.common.enums.ErrorCode;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/1/16 17:41
 **/
public enum GatewayRetCodeEnum implements ErrorCode {

    /**
     * 状态码
     */
    SUCCESS(0, "成功"),

    /**
     * http status
     */
    CERTIFICATE_EXPIRED_ERROR(401, "用户认证凭证已过期"),
    CERTIFICATE_EXCEPTION_ERROR(401_1, "用户认证凭证异常"),

    /**
     * 公共响应码
     */
    SYSTEM_ERROR(1_10_100_1001, "系统异常"),

//    PARAM_MISS(100_1_001,"缺少必要参数[%s]"),
    PARAM_ILLEGAL(1_10_100_1002,"参数非法"),
    PARAM_ILLEGAL_FIELD(1_10_100_1003,"[%s]参数非法"),
    PARAM_PARSING_FIELD(1_10_100_1004,"参数解析失败"),
    VERSION_ERROR(1_10_100_1005, "数据版本异常"),
    ENV_PARAM_ERROR(1_10_100_1006,"环境参数异常"),
    APP_INSTANCE_ERROR(100_2_001,"当前【%s】【%s】应用实例异常"),
    PAYLOAD_TOO_LARGE(100_2_002,"Payload too large!"),
    APP_DATA_NOT_EXISTED(100_2_003,"应用不存在"),

    /**
     * 限流
     */
    SERVER_BUSY(1_20_100_1001, "服务器忙，请稍后再试!"),

    /**
     * 网关
     */
    GATEWAY_PARAM_MISS(1_1_100_1001, "网关缺少必要参数"),
    GATEWAY_API_DISABLED(1_1_100_1002, "接口已被禁用"),
    GATEWAY_API_OFFLINE(1_1_100_1003, "接口已下线"),

    GATEWAY_API_NOT_EXISTED(1_1_100_1004, "接口不存在"),

    /**
     * 网关接口
     */
    GATEWAY_API_CODE_EXITED(5_0_100_1001, "当前api编码已存在"),
    GATEWAY_API_NOT_EXITED(5_0_100_1002, "当前api不存在"),
    GATEWAY_API_ADD_FAILED(5_0_100_1003, "接口新增失败"),
    GATEWAY_API_UPDATE_FAILED(5_0_100_1004, "接口更新失败"),
    GATEWAY_API_DELETE_FAILED(5_0_100_1005, "接口删除失败"),


    ;

    private final int code;
    private final String message;

    GatewayRetCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
