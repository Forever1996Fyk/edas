package com.ahcloud.edas.powerjob.biz.infrastructure.constant.enums;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @Description 错误码
 * @Author YuKai fan
 * @Date 2021/9/27 14:31
 * @Version 1.0
 */
@Getter
public enum PowerJobRetCodeEnum implements ErrorCode {

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
     * appInfo
     */
    APP_INFO_SAVE_FAILED(9_0_00_001, "保存APP信息失败"),

    /**
     * job
     */
    JOB_ADD_FAILED(9_1_00_001, "新增任务失败"),
    JOB_UPDATE_FAILED(9_1_00_002, "更新任务失败"),
    JOB_INFO_QUERY_FAILED(9_1_00_003, "查询任务详情失败"),
    JOB_DELETE_FAILED(9_1_00_004, "删除任务失败"),
    JOB_RUN_FAILED(9_1_00_005, "任务执行失败"),
    JOB_DISABLE_FAILED(9_1_00_006, "禁止任务失败"),
    JOB_ENABLE_FAILED(9_1_00_007, "启动任务失败"),
    JOB_PAGE_QUERY_FAILED(9_1_00_008, "分页查询任务失败"),

    /**
     * instance
     */
    INSTANCE_PAGE_QUERY_FAILED(9_2_00_001, "分页查询实例失败"),
    INSTANCE_STOP_FAILED(9_2_00_002, "停止实例失败"),
    INSTANCE_CANCEL_FAILED(9_2_00_003, "取消实例失败"),
    INSTANCE_RETRY_FAILED(9_2_00_004, "重试实例失败"),
    INSTANCE_QUERY_STATUS_FAILED(9_2_00_005, "查询实例状态失败"),

    /**
     * 表达式Validate
     */
    INVALID_TIME_EXPRESSION(9_3_00_001, "表达式不正确"),
    DELAY_TIME_EXCEPTION(9_3_10_001, "延迟间隔时间必须大于0且小于120000ms")
    ;

    private final int code;
    private final String message;

    PowerJobRetCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
