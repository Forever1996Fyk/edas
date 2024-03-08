package com.ahcloud.edas.nacos.biz.infrastructure.constant.enums;

import java.util.Arrays;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 09:34
 **/
public enum ConfigTypeEnum {

    /**
     * 配置类型
     */
    TEXT("text"),
    JSON("json"),
    XML("xml"),
    YAML("yaml"),
    HTML("html"),
    Properties("properties"),
    ;

    private final String type;

    ConfigTypeEnum(String type) {
        this.type = type;
    }

    public static ConfigTypeEnum getByType(String type) {
        return Arrays.stream(values())
                .filter(configTypeEnum -> configTypeEnum.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    public boolean isYaml() {
        return this == YAML;
    }

    public boolean isProperties() {
        return this == Properties;
    }

    public String getType() {
        return type;
    }
}
