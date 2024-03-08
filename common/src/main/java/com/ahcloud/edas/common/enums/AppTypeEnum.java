package com.ahcloud.edas.common.enums;

import java.util.Arrays;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/8 10:28
 **/
public enum AppTypeEnum {

    /**
     * devops类型
     */
    JAVA(1, "java"),
    NODE(2, "node"),
    HTML(3, "html"),
    ;

    private final int type;;
    private final String desc;

    AppTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static AppTypeEnum getByType(int type) {
        return Arrays.stream(values())
                .filter(devopsTypeEnum -> devopsTypeEnum.getType() == type)
                .findFirst()
                .orElse(JAVA);
    }

    public boolean isJava() {
        return this == JAVA;
    }
}
