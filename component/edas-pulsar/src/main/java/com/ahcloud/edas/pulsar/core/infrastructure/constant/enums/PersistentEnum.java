package com.ahcloud.edas.pulsar.core.infrastructure.constant.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/28 20:58
 **/
@Getter
public enum PersistentEnum {

    /**
     * 持久化类型
     */
    UNKNOWN(-2, "UNKNOWN", "未知"),
    PERSISTENT(-1, "persistent://", "持久化"),
    NON_PERSISTENT(0, "non-persistent://", "非持久化"),
    ;

    private final int type;
    private final String value;
    private final String desc;

    PersistentEnum(int type, String value, String desc) {
        this.type = type;
        this.value = value;
        this.desc = desc;
    }

    public static boolean isValid(Integer type) {
        return getByType(type) != UNKNOWN;
    }

    public static PersistentEnum getByType(Integer type) {
        return Arrays.stream(values())
                .filter(persistentEnum -> Objects.equals(persistentEnum.getType(), type))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public boolean isPersistent() {
        return this == PERSISTENT;
    }
}
