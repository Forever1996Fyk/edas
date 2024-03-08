package com.ahcloud.edas.pulsar.core.application.helper;

import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.pulsar.core.domain.message.dto.MessageBaseInfoDTO;
import com.ahcloud.edas.pulsar.core.domain.message.vo.MessageDetailVO;
import com.ahcloud.edas.pulsar.core.domain.message.vo.MessageVO;
import com.google.common.collect.Maps;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.impl.BatchMessageIdImpl;
import org.apache.pulsar.client.impl.MessageIdImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 09:22
 **/
public class MessageHelper {

    /**
     * 数据转换
     * @param message
     * @return
     */
    public static MessageVO convertToVO(Message<byte[]> message) {
        MessageVO.MessageVOBuilder builder = MessageVO.builder()
                .messageId(message.getMessageId().toString())
                .producer(message.getProducerName())
                .key(message.getKey())
                .publishTime(DateUtils.parse(message.getPublishTime()));
        MessageId messageId = message.getMessageId();
        if (messageId instanceof BatchMessageIdImpl) {
            BatchMessageIdImpl msgId = (BatchMessageIdImpl) messageId;
            builder.ledgerId(msgId.getLedgerId())
                    .entryId(msgId.getEntryId())
                    .batch(true)
                    .batchIndex(msgId.getBatchIndex());
        } else {
            MessageIdImpl msgId = (MessageIdImpl) messageId;
            builder.ledgerId(msgId.getLedgerId())
                    .entryId(msgId.getEntryId())
                    .batch(false);
        }
        return builder.build();
    }

    /**
     * 数据转换
     * @param messages
     * @return
     */
    public static List<MessageVO> convertToVOList(List<Message<byte[]>> messages) {
        return messages.stream()
                .map(MessageHelper::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 数据转换
     * @param message
     * @return
     */
    public static MessageDetailVO convertToDetail(Message<byte[]> message) {
        String data = new String(message.getValue(), StandardCharsets.UTF_8);
        Map<String, String> map = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(message.getProperties())) {
            map.putAll(message.getProperties());
        }
        return MessageDetailVO.builder()
                .messageBody(data)
                .baseInfo(convertToBaseInfo(message))
                .properties(map)
                .build();
    }

    /**
     * 数据转换
     * @param message
     * @return
     */
    public static MessageBaseInfoDTO convertToBaseInfo(Message<byte[]> message) {
        MessageId messageId = message.getMessageId();
        MessageBaseInfoDTO.MessageBaseInfoDTOBuilder builder = MessageBaseInfoDTO.builder()
                .messageId(messageId.toString())
                .producer(message.getProducerName())
                .key(message.getKey())
                .publishTime(DateUtils.parse(message.getPublishTime()));
        if (messageId instanceof BatchMessageIdImpl) {
            BatchMessageIdImpl msgId = (BatchMessageIdImpl) messageId;
            builder.ledgerId(msgId.getLedgerId())
                    .entryId(msgId.getEntryId())
                    .batch(true)
                    .batchIndex(msgId.getBatchIndex());
        } else {
            MessageIdImpl msgId = (MessageIdImpl) messageId;
            builder.ledgerId(msgId.getLedgerId())
                    .entryId(msgId.getEntryId())
                    .batch(false);
        }
        return builder.build();
    }
}
