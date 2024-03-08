package com.ahcloud.edas.rocketmq.core.infrastructure.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 17:02
 **/
@Getter
public enum AclPermEnum {

    /**
     * 拒绝
     */
    DENY("DENY", "拒绝"),

    /**
     * PUB和SUB权限
     */
    ANY("PUB|SUB", "PUB或SUB权限"),

    /**
     * 发送权限
     */
    PUB("PUB", "发送权限"),

    /**
     * 订阅权限
     */
    SUB("SUB", "订阅权限"),
    ;

    private final String perm;

    public final String desc;

    AclPermEnum(String perm, String desc) {
        this.perm = perm;
        this.desc = desc;
    }

    public static AclPermEnum getByPerm(String perm) {
        return Arrays.stream(values())
                .filter(permEnum -> permEnum.getPerm().equals(perm))
                .findFirst()
                .orElse(DENY);
    }
}
