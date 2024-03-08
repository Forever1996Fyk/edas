package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service;

import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.TopicConfigInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.TopicConsumerInfo;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 16:30
 **/
public interface TopicService {

    /**
     * 创建或更新topic
     * @param topicCreateOrUpdateRequest
     * @param create
     */
    void createOrUpdate(TopicConfigInfo topicCreateOrUpdateRequest, boolean create);

    /**
     * 删除topic
     * @param topic
     * @param clusterName
     * @return
     */
    boolean deleteTopic(String topic, String clusterName);

    /**
     * 删除topic
     * @param topic
     * @return
     */
    boolean deleteTopic(String topic);

    /**
     * 查询消费者状态
     * @param topic
     * @param group
     * @return
     */
    TopicConsumerInfo queryConsumeStateByTopicNameAndGroup(String topic, String group);
}
