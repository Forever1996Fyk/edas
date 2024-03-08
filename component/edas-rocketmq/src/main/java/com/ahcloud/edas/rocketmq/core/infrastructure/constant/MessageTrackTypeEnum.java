package com.ahcloud.edas.rocketmq.core.infrastructure.constant;

import lombok.Getter;
import org.apache.rocketmq.tools.admin.api.TrackType;

import java.util.Arrays;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 17:11
 **/
@Getter
public enum MessageTrackTypeEnum {

    /**
     * 消息轨迹类型
     */
    UNKNOWN(-1, "未知消息", TrackType.UNKNOWN),
    CONSUMED(1, "以消费", TrackType.CONSUMED),
    CONSUMED_BUT_FILTERED(2, "消费失败", TrackType.CONSUMED_BUT_FILTERED),
    PULL(3, "已拉取", TrackType.PULL),
    NOT_CONSUME_YET(4, "未消费", TrackType.NOT_CONSUME_YET),
    NOT_ONLINE(5, "消费组未在线", TrackType.NOT_ONLINE),
    CONSUME_BROADCASTING(6, "广播消费", TrackType.CONSUME_BROADCASTING),

    ;

    private final int type;
    private final String desc;
    private final TrackType trackType;

    MessageTrackTypeEnum(int type, String desc, TrackType trackType) {
        this.type = type;
        this.desc = desc;
        this.trackType = trackType;
    }

    public static MessageTrackTypeEnum getByType(TrackType trackType) {
        return Arrays.stream(values())
                .filter(messageTrackTypeEnum -> messageTrackTypeEnum.getTrackType() == trackType)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
