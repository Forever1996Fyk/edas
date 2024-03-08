package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service;

import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.MessagePage;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.MessageView;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.MessageQuery;
import org.apache.rocketmq.common.Pair;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.body.ConsumeMessageDirectlyResult;
import org.apache.rocketmq.tools.admin.api.MessageTrack;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 10:45
 **/
public interface MessageService {

    /**
     * 查询消息
     * @param topic
     * @param msgId
     * @return
     */
    Pair<MessageView, List<MessageTrack>> viewMessage(String topic, final String msgId);

    /**
     * 分页查询消息列表
     * @param query
     * @return
     */
    MessagePage queryMessageByPage(MessageQuery query);

    /**
     * 查询消息轨迹
     * @param msg
     * @return
     */
    List<MessageTrack> messageTrackDetail(MessageExt msg);

    /**
     * 直接消费结果
     * @param topic
     * @param msgId
     * @param consumerGroup
     * @param clientId
     * @return
     */
    ConsumeMessageDirectlyResult consumeMessageDirectly(String topic, String msgId, String consumerGroup,
                                                        String clientId);
}
