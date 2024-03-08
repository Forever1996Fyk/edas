package com.ahcloud.edas.pulsar.core.infrastructure.constant.enums;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @Description 错误码
 * @Author YuKai fan
 * @Date 2021/9/27 14:31
 * @Version 1.0
 */
@Getter
public enum PulsarRetCodeEnum implements ErrorCode {

    /**
     * 公共系统响应码
     */
    SUCCESS(0,"成功"),
    UNKNOWN_ERROR(100_0_001,"未知错误"),
    SYSTEM_ERROR(100_0_002,"系统异常"),
    SYSTEM_BUSY(100_0_003, "系统繁忙,请稍后再试"),

    /**
     * 公共参数响应码
     */
    PARAM_MISS(100_1_001,"缺少必要参数[%s]"),
    PARAM_ILLEGAL(100_1_002,"参数非法"),
    PARAM_ILLEGAL_FIELD(100_1_004,"[%s]参数非法"),


    /**
     * 公共业务异常
     */
    BUSINESS_FAIL(100_2_001,"业务处理异常"),
    DATE_CANNOT_ACROSS_THE_MONTH(100_2_002, "日期不能跨月"),
    ALARM_PUSH_FAILED(100_2_003, "告警推送异常"),
    ALARM_PUSH_FAILED_MSG_MAX_SIZE(100_2_004, "告警推送异常, 超过最大字数[%s]"),
    ACCOUNT_CANCEL_FAILED(100_2_005, "账号注销失败"),

    /**
     * 公共操作异常
     */
    CALL_LIMITED(100_3_001,"调用次数过多"),
    TOKEN_EXCEPTION(100_3_002, "Token验证异常"),
    VERSION_ERROR(100_3_003, "数据版本异常，请重试"),
    SELECTOR_NOT_EXISTED(100_3_004, "[%s]选择处理器不存在"),
    LOGIN_INVALID(100_3_005, "登录失效，请重新登录"),
    LOGOUT_SUCCESS(100_3_006, "退出登录成功"),
    VISITOR_MODE_CANNOT_ACCESS_RESOURCE(100_3_007, "游客模式无法访问资源"),
    DATA_NOT_EXISTED(100_3_008, "当前数据不存在"),
    DATA_EXISTED(100_3_009, "当前数据已存在"),

    /**
     * 租户不存在
     */
    TENANT_NOT_EXISTED(2_0_00_001, "租户不存在"),
    TENANT_QUERY_FAILED(2_0_00_002, "查询租户详情错误"),

    /**
     * 命名空间不存在
     */
    NAMESPACE_NOT_EXISTED(3_0_00_001, "命名空间不存在"),

    /**
     * topic
     */
    TOPIC_NOT_EXISTED(4_0_00_001, "topic不存在"),
    SUBSCRIPTION_NOT_EXISTED(4_1_00_001, "订阅不存在"),

    /**
     * auth token
     */
    GENERATE_TOKEN_FAILED(5_0_00_001, "认证token 生成失败"),
    ROLE_NOT_EXISTED(5_0_00_002, "角色不存在"),
    APP_ROLE_NOT_EXISTED(5_0_00_003, "应用对应token不存在，请申请开通"),

    /**
     * 策略异常
     */
    PARTITION_NUM_ERROR(6_0_00_001, "分区数必须大于0且小于32"),
    PARTITION_NUM_MUST_MORE_THAN(6_0_00_002, "分区数必须大于%s"),
    ;

    private final int code;
    private final String message;

    PulsarRetCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
