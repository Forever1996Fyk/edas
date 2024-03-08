package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.impl;

import cn.hutool.core.convert.Convert;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.rocketmq.core.infrastructure.config.RMQProperties;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.RmqRetCodeEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.QueueStatInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.TopicConfigInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.TopicConsumerInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.AbstractCommonService;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.TopicService;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.admin.ConsumeStats;
import org.apache.rocketmq.remoting.protocol.body.ClusterInfo;
import org.apache.rocketmq.remoting.protocol.body.Connection;
import org.apache.rocketmq.remoting.protocol.body.ConsumerConnection;
import org.apache.rocketmq.remoting.protocol.body.ConsumerRunningInfo;
import org.apache.rocketmq.remoting.protocol.body.GroupList;
import org.apache.rocketmq.tools.command.CommandUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 16:32
 **/
@Slf4j
@Service
public class TopicServiceImpl extends AbstractCommonService implements TopicService {
    @Resource
    private RMQProperties properties;

    @Override
    public void createOrUpdate(TopicConfigInfo topicConfigInfo, boolean create) {
        try {
            ClusterInfo clusterInfo = mqAdminExt.examineBrokerClusterInfo();
            for (String brokerName : changeToBrokerNameSet(clusterInfo.getClusterAddrTable(),
                    properties.getClusterNameList(), topicConfigInfo.getBrokerNameList())) {
                TopicConfig topicConfig = new TopicConfig(topicConfigInfo.getTopicName()
                        , topicConfigInfo.getReadQueueNums()
                        , topicConfigInfo.getWriteQueueNums()
                        , topicConfigInfo.getPerm()
                );
                topicConfig.setOrder(topicConfigInfo.isOrder());
                topicConfig.setAttributes(topicConfigInfo.getAttributes());
                mqAdminExt.createAndUpdateTopicConfig(clusterInfo.getBrokerAddrTable().get(brokerName).selectBrokerAddr(), topicConfig);
            }
        } catch (Exception err) {
            log.error("TopicServiceImpl[createOrUpdate] topic createOrUpdate error, cause is {}" , Throwables.getStackTraceAsString(err));
            throw new BizException(create ? RmqRetCodeEnum.CREATED_TOPIC_FAILED : RmqRetCodeEnum.UPDATED_TOPIC_FAILED);
        }
    }

    @Override
    public boolean deleteTopic(String topic, String clusterName) {
        try {
            if (StringUtils.isBlank(clusterName)) {
                return deleteTopic(topic);
            }
            Set<String> masterSet = CommandUtil.fetchMasterAddrByClusterName(mqAdminExt, clusterName);
            mqAdminExt.deleteTopicInBroker(masterSet, topic);
            Set<String> nameServerSet = null;
            if (StringUtils.isNotBlank(properties.getNamesrvAddr())) {
                String[] ns = properties.getNamesrvAddr().split(";");
                nameServerSet = new HashSet<>(Arrays.asList(ns));
            }
            mqAdminExt.deleteTopicInNameServer(nameServerSet, topic);
        } catch (Exception err) {
            Throwables.throwIfUnchecked(err);
            throw new BizException(RmqRetCodeEnum.DELETED_TOPIC_FAILED);
        }
        return false;
    }

    @Override
    public boolean deleteTopic(String topic) {
        ClusterInfo clusterInfo = null;
        try {
            clusterInfo = mqAdminExt.examineBrokerClusterInfo();
        } catch (Exception err) {
            Throwables.throwIfUnchecked(err);
            throw new BizException(RmqRetCodeEnum.DELETED_TOPIC_FAILED);
        }
        for (String clusterName : clusterInfo.getClusterAddrTable().keySet()) {
            deleteTopic(topic, clusterName);
        }
        return true;
    }

    @Override
    public TopicConsumerInfo queryConsumeStateByTopicNameAndGroup(String topic, String groupName) {
        ConsumeStats consumeStats;
        try {
            consumeStats = mqAdminExt.examineConsumeStats(groupName, topic);
        }
        catch (Exception e) {
            Throwables.throwIfUnchecked(e);
            throw new BizException(RmqRetCodeEnum.QUERY_CONSUMER_STATE_FAILED);
        }
        List<MessageQueue> mqList = Lists.newArrayList(Iterables.filter(consumeStats.getOffsetTable().keySet(), o -> StringUtils.isBlank(topic) || o.getTopic().equals(topic)));
        Collections.sort(mqList);
        List<TopicConsumerInfo> topicConsumerInfoList = Lists.newArrayList();
        TopicConsumerInfo nowTopicConsumerInfo = null;
        Map<MessageQueue, String> messageQueueClientMap = getClientConnection(groupName);
        for (MessageQueue mq : mqList) {
            if (nowTopicConsumerInfo == null || (!StringUtils.equals(mq.getTopic(), nowTopicConsumerInfo.getTopic()))) {
                nowTopicConsumerInfo = new TopicConsumerInfo(mq.getTopic());
                topicConsumerInfoList.add(nowTopicConsumerInfo);
            }
            QueueStatInfo queueStatInfo = QueueStatInfo.fromOffsetTableEntry(mq, consumeStats.getOffsetTable().get(mq));
            queueStatInfo.setClientInfo(messageQueueClientMap.get(mq));
            nowTopicConsumerInfo.appendQueueStatInfo(queueStatInfo);
        }
        return CollectionUtils.isNotEmpty(topicConsumerInfoList) ? topicConsumerInfoList.get(0) : new TopicConsumerInfo(topic);
    }

    /**
     * 获取客户端连接
     * @param groupName
     * @return
     */
    private Map<MessageQueue, String> getClientConnection(String groupName) {
        Map<MessageQueue, String> results = Maps.newHashMap();
        try {
            ConsumerConnection consumerConnection = mqAdminExt.examineConsumerConnectionInfo(groupName);
            for (Connection connection : consumerConnection.getConnectionSet()) {
                String clinetId = connection.getClientId();
                ConsumerRunningInfo consumerRunningInfo = mqAdminExt.getConsumerRunningInfo(groupName, clinetId, false);
                for (MessageQueue messageQueue : consumerRunningInfo.getMqTable().keySet()) {
                    results.put(messageQueue, clinetId);
                }
            }
        }
        catch (Exception err) {
            log.error("op=getClientConnection_error", err);
        }
        return results;
    }
}
