package com.ahcloud.edas.rocketmq.core.infrastructure.constant;

import lombok.Getter;
import org.apache.rocketmq.remoting.protocol.body.CMResult;

import java.util.Arrays;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 17:45
 **/
@Getter
public enum CMResultEnum {

    /**
     * 消费结果
     */
    CR_SUCCESS(0, "消费成功", CMResult.CR_SUCCESS),
    CR_LATER(1, "延迟消费", CMResult.CR_LATER),
    CR_ROLLBACK(2, "消息回滚", CMResult.CR_ROLLBACK),
    CR_COMMIT(3, "消息提交", CMResult.CR_COMMIT),
    CR_THROW_EXCEPTION(4, "消息抛出异常", CMResult.CR_THROW_EXCEPTION),
    CR_RETURN_NULL(5, "消息返回null", CMResult.CR_RETURN_NULL);
    ;

    private final int type;
    private final String desc;
    private final CMResult result;

    CMResultEnum(int type, String desc, CMResult result) {
        this.type = type;
        this.desc = desc;
        this.result = result;
    }

    public static CMResultEnum getByResult(CMResult result) {
        return Arrays.stream(values())
                .filter(cmResultEnum -> cmResultEnum.getResult() == result)
                .findFirst()
                .orElse(CR_RETURN_NULL);
    }
}
