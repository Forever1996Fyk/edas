package com.ahcloud.edas.rocketmq.core.infrastructure.constant;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 15:23
 **/
@Getter
public enum RmqRetCodeEnum implements ErrorCode {

    /**
     * 公共系统响应码
     */
    SUCCESS(0, "成功"),

    UNKNOWN_ERROR(100_0_001, "未知错误"),

    SYSTEM_ERROR(100_0_002, "系统异常"),

    SYSTEM_BUSY(100_0_003, "系统繁忙,请稍后再试"),

    /**
     * 公共参数响应码
     */
    PARAM_MISS(100_1_001, "缺少必要参数[%s]"),

    PARAM_ILLEGAL(100_1_002, "参数非法"),

    PARAM_ILLEGAL_FIELD(100_1_004, "[%s]参数非法"),


    /**
     * 公共业务异常
     */
    BUSINESS_FAIL(100_2_001, "业务处理异常"),

    DATE_CANNOT_ACROSS_THE_MONTH(100_2_002, "日期不能跨月"),

    ALARM_PUSH_FAILED(100_2_003, "告警推送异常"),

    ALARM_PUSH_FAILED_MSG_MAX_SIZE(100_2_004, "告警推送异常, 超过最大字数[%s]"),

    ACCOUNT_CANCEL_FAILED(100_2_005, "账号注销失败"),

    /**
     * 公共操作异常
     */
    CALL_LIMITED(100_3_001, "调用次数过多"),

    TOKEN_EXCEPTION(100_3_002, "Token验证异常"),

    VERSION_ERROR(100_3_003, "数据版本异常，请重试"),

    SELECTOR_NOT_EXISTED(100_3_004, "[%s]选择处理器不存在"),

    LOGIN_INVALID(100_3_005, "登录失效，请重新登录"),

    LOGOUT_SUCCESS(100_3_006, "退出登录成功"),

    VISITOR_MODE_CANNOT_ACCESS_RESOURCE(100_3_007, "游客模式无法访问资源"),

    DATA_NOT_EXISTED(100_3_008, "当前数据不存在"),

    DATA_EXISTED(100_3_009, "当前数据已存在"),

    /**
     * topic异常
     */
    CREATED_TOPIC_FAILED(8_0_00_001, "创建topic失败"),
    UPDATED_TOPIC_FAILED(8_0_00_002, "更新topic失败"),
    DELETED_TOPIC_FAILED(8_0_00_003, "删除topic失败"),

    /**
     * acl异常
     */
    ACL_CONFIG_NOT_EXISTED(8_1_00_001, "acl配置不存在"),

    /**
     * consumer
     */
    QUERY_CONSUMER_CONFIG_FAILED(8_2_00_001, "查询消费者配置失败"),
    QUERY_CONSUMER_STATE_FAILED(8_2_00_002, "查询消费者状态异常"),

    /**
     * message
     */
    VIEW_MESSAGE_FAILED(8_3_00_001, "查询消息详情错误"),
    PAGE_MESSAGES_FAILED(8_3_00_002, "分页查询消息列表错误"),

    /**
     * broker
     */
    BROKER_NAME_LIST_FAILED(8_4_00_001, "查询broker列表错误"),
    ;

    private final int code;
    private final String message;

    RmqRetCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
