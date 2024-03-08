package com.ahcloud.edas.devops.jenkins.core.job.enums;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 21:24
 **/
@Getter
public enum JenkinsRetCodeEnum implements ErrorCode {
    /**
     * 公共系统响应码
     */
    SUCCESS(0,"成功"),
    UNKNOWN_ERROR(100_0_001,"未知错误"),
    SYSTEM_ERROR(100_0_002,"系统异常"),
    SYSTEM_BUSY(100_0_003, "系统繁忙,请稍后再试"),

    DATA_NOT_EXISTED(100_3_008, "当前数据不存在"),
    DATA_EXISTED(100_3_009, "当前数据已存在"),

    /**
     * job
     */
    JOB_NOT_BUILDING(5_0_00_1, "任务不在构建中"),
    JOB_CONFIG_NOT_EXISTED(5_0_00_2, "配置不存在"),
    JOB_ABORTED_FAILED(5_0_00_3, "任务终止失败"),
    JOB_ENDED(5_0_00_4, "任务已结束"),
    JOB_BUILD_FAILED(5_0_00_5, "任务构建失败, 原因为:【%s】"),

    JOB_BUILDING_REPEATED(5_0_00_6, "当前应用已在构建中，请勿重复构建"),
    JOB_ABORTED_REPEATED(5_0_00_7, "任务终止失败，当前应用正在终止"),
    ;


    private final int code;
    private final String message;

    JenkinsRetCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
