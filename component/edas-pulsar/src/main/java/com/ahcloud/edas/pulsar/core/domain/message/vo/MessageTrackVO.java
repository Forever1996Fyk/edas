package com.ahcloud.edas.pulsar.core.domain.message.vo;

import com.ahcloud.edas.pulsar.core.domain.message.dto.MessageConsumeDTO;
import com.ahcloud.edas.pulsar.core.domain.message.dto.MessageProduceDTO;
import com.ahcloud.edas.pulsar.core.domain.message.dto.MessageStorageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 10:35
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageTrackVO {

    /**
     * 消息生产
     */
    private MessageProduceDTO messageProduce;

    /**
     * 消息存储
     */
    private MessageStorageDTO messageStorage;

    /**
     * 消息消费
     */
    private List<MessageConsumeDTO> messageConsumeList;
}
