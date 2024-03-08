package com.ahcloud.edas.admin.biz.infrastructure.constant.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/2 16:36
 **/
@Getter
public enum ThirdSourceEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 飞书扫码登录
     */
    FEISHU_QR_CODE(1, "飞书扫码登录"),
    ;

    private final int source;
    private final String desc;

    ThirdSourceEnum(int source, String desc) {
        this.source = source;
        this.desc = desc;
    }

    public static boolean isValid(Integer source) {
        return getBySource(source) != UNKNOWN;
    }

    public static ThirdSourceEnum getBySource(int source) {
        return Arrays.stream(values())
                .filter(thirdSourceEnum -> thirdSourceEnum.getSource() == source)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
