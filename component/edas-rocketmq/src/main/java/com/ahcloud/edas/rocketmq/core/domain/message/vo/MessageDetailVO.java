package com.ahcloud.edas.rocketmq.core.domain.message.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 16:36
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDetailVO {

    /**
     * 消息数据
     */
    private MessageDataVO messageData;

    /**
     * 消息轨迹
     */
    private List<MessageTrackVO> messageTrackList;
}
