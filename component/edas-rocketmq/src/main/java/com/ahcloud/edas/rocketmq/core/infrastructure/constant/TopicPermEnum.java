package com.ahcloud.edas.rocketmq.core.infrastructure.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 11:11
 **/
@Getter
public enum TopicPermEnum {

    /**
     * topic读写权限
     */
    UNKNOWN(-1, "未知"),
    WRITE(2, "读"),
    READ(4, "写"),
    READ_WRITE(6, "读写"),
    ;
    private final int perm;
    private final String desc;

    TopicPermEnum(int perm, String desc) {
        this.perm = perm;
        this.desc = desc;
    }

    public static boolean isValid(Integer value) {
        return getByPerm(value) != UNKNOWN;
    }

    public static TopicPermEnum getByPerm(int value) {
        return Arrays.stream(values())
                .filter(topicPermEnum -> topicPermEnum.getPerm() == value)
                .findFirst()
                .orElse(UNKNOWN);
    }

}
