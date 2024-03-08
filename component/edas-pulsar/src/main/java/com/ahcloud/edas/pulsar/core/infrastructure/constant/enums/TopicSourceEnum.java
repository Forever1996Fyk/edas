package com.ahcloud.edas.pulsar.core.infrastructure.constant.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 16:07
 **/
@Getter
public enum TopicSourceEnum {

    /**
     * 租户类型
     */
    UNKNOWN(-1, "未知"),
    SYSTEM(1, "系统创建"),
    USER(2, "用户创建"),
    ;

    private final int type;
    private final String desc;

    TopicSourceEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static boolean isValid(Integer type) {
        return getByType(type) != UNKNOWN;
    }

    public static TopicSourceEnum getByType(Integer type) {
        return Arrays.stream(values())
                .filter(tenantTypeEnum -> Objects.equals(tenantTypeEnum.getType(), type))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
