package com.ahcloud.edas.common.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/20 16:08
 **/
@Getter
public enum ResourceTypeEnum {

    /**
     * mysql
     */
    MYSQL(1, "mysql"),

    /**
     * redis
     */
    REDIS(2, "redis"),

    /**
     * es
     */
    ES(3, "es"),

    /**
     * mongodb
     */
    MONGODB(3, "mongodb"),
    ;

    private final int type;
    private final String desc;

    ResourceTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ResourceTypeEnum getByType(int type) {
        return Arrays.stream(values())
                .filter(resourceTypeEnum -> resourceTypeEnum.getType() == type)
                .findFirst()
                .orElse(MYSQL);
    }


}
