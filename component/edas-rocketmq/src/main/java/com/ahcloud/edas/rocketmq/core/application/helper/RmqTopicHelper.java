package com.ahcloud.edas.rocketmq.core.application.helper;

import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.vo.BrokerQueueStateInfo;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.vo.SubscribeGroupConsumeDetailVO;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.QueueStatInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.TopicConsumerInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/11 16:09
 **/
public class RmqTopicHelper {

    /**
     * 数据转换
     * @param groupName
     * @param topicConsumerInfo
     * @return
     */
    public static SubscribeGroupConsumeDetailVO convert(String groupName, TopicConsumerInfo topicConsumerInfo) {
        return SubscribeGroupConsumeDetailVO.builder()
                .diffTotal(topicConsumerInfo.getDiffTotal())
                .groupName(groupName)
                .lastConsumeTime(DateUtils.parse(topicConsumerInfo.getLastTimestamp()))
                .queueStateInfoList(convertToList(topicConsumerInfo.getQueueStatInfoList()))
                .build();
    }

    /**
     * 数据转换
     * @param queueStatInfo
     * @return
     */
    public static BrokerQueueStateInfo convert(QueueStatInfo queueStatInfo) {
        return BrokerQueueStateInfo.builder()
                .queueId(queueStatInfo.getQueueId())
                .brokerName(queueStatInfo.getBrokerName())
                .brokerOffset(queueStatInfo.getBrokerOffset())
                .consumerOffset(queueStatInfo.getConsumerOffset())
                .lastConsumeTime(DateUtils.parse(queueStatInfo.getLastTimestamp()))
                .build();
    }

    /**
     * 数据转换
     * @param queueStatInfoList
     * @return
     */
    public static List<BrokerQueueStateInfo> convertToList(List<QueueStatInfo> queueStatInfoList) {
        return queueStatInfoList.stream()
                .map(RmqTopicHelper::convert)
                .collect(Collectors.toList());
    }

    /**
     * 重构topicName
     * @param topicName
     * @param env
     * @return
     */
    public static String rebuildTopicName(String topicName, String appCode, String env) {
        return appCode + "-" + topicName + "-" + env;
    }
}
