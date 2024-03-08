package com.ahcloud.edas.devops.gitlab.core.enums;

import com.ahcloud.edas.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 21:24
 **/
@Getter
public enum GitlabRetCodeEnum implements ErrorCode {
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
     * gitlab
     */
    GITLAB_PROJECT_QUERY_FAILED(6_0_00_1, "gitlab项目查询异常"),
    GITLAB_BRANCH_QUERY_FAILED(6_0_00_2, "gitlab项目分支查询异常"),
    ;


    private final int code;
    private final String message;

    GitlabRetCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
