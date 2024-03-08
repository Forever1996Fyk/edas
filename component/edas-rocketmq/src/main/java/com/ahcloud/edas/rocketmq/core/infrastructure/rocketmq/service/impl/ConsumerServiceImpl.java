package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.impl;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.rocketmq.core.infrastructure.config.RMQProperties;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.RmqRetCodeEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.ConsumerGroupRollBackStat;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.GroupConsumeInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.ConsumerConfigInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.DeleteSubGroupRequest;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.ResetOffsetRequest;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.AbstractCommonService;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.ConsumerService;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.MQVersion;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.ResponseCode;
import org.apache.rocketmq.remoting.protocol.admin.ConsumeStats;
import org.apache.rocketmq.remoting.protocol.admin.RollbackStats;
import org.apache.rocketmq.remoting.protocol.body.ClusterInfo;
import org.apache.rocketmq.remoting.protocol.body.ConsumerConnection;
import org.apache.rocketmq.remoting.protocol.subscription.SubscriptionGroupConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/10 16:29
 **/
@Slf4j
@Service
public class ConsumerServiceImpl extends AbstractCommonService implements ConsumerService, InitializingBean {
    @Resource
    private RMQProperties rmqProperties;

    private ExecutorService executorService;

    @Override
    public void afterPropertiesSet() {
        Runtime runtime = Runtime.getRuntime();
        int corePoolSize = Math.max(10, runtime.availableProcessors() * 2);
        int maximumPoolSize = Math.max(20, runtime.availableProcessors() * 2);
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicLong threadIndex = new AtomicLong(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "QueryGroup_" + this.threadIndex.incrementAndGet());
            }
        };
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
        this.executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5000), threadFactory, handler);
    }

    @Override
    public List<GroupConsumeInfo> queryGroupList(Collection<String> groupNameList) {
        CountDownLatch countDownLatch = new CountDownLatch(groupNameList.size());
        List<GroupConsumeInfo> groupConsumeInfoList = Collections.synchronizedList(Lists.newArrayList());
        for (String consumerGroup : groupNameList) {
            executorService.submit(() -> {
                try {
                    GroupConsumeInfo consumeInfo = queryGroup(consumerGroup);
                    groupConsumeInfoList.add(consumeInfo);
                } catch (Exception e) {
                    log.error("queryGroup exception, consumerGroup: {}", consumerGroup, e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("query consumerGroup countDownLatch await Exception", e);
        }
        return groupConsumeInfoList;
    }

    @Override
    public GroupConsumeInfo queryGroup(String consumerGroup) {
        GroupConsumeInfo groupConsumeInfo = new GroupConsumeInfo();
        try {
            ConsumeStats consumeStats = null;
            try {
                consumeStats = mqAdminExt.examineConsumeStats(consumerGroup);
            } catch (Exception e) {
                log.warn("examineConsumeStats exception to consumerGroup {}, response [{}]", consumerGroup, e.getMessage());
            }

            ConsumerConnection consumerConnection = null;
            try {
                consumerConnection = mqAdminExt.examineConsumerConnectionInfo(consumerGroup);
            } catch (Exception e) {
                log.warn("examineConsumeStats exception to consumerGroup {}, response [{}]", consumerGroup, e.getMessage());
            }

            groupConsumeInfo.setGroup(consumerGroup);

            if (consumeStats != null) {
                groupConsumeInfo.setConsumeTps((int) consumeStats.getConsumeTps());
                groupConsumeInfo.setDiffTotal(consumeStats.computeTotalDiff());
            }

            if (consumerConnection != null) {
                groupConsumeInfo.setCount(consumerConnection.getConnectionSet().size());
                groupConsumeInfo.setMessageModel(consumerConnection.getMessageModel());
                groupConsumeInfo.setConsumeType(consumerConnection.getConsumeType());
                groupConsumeInfo.setVersion(MQVersion.getVersionDesc(consumerConnection.computeMinVersion()));
            }
        } catch (Exception e) {
            log.warn("examineConsumeStats or examineConsumerConnectionInfo exception, "
                    + consumerGroup, e);
        }
        return groupConsumeInfo;
    }

    @Override
    public boolean createAndUpdateSubscriptionGroupConfig(ConsumerConfigInfo consumerConfigInfo) {
        try {
            ClusterInfo clusterInfo = mqAdminExt.examineBrokerClusterInfo();
            for (String brokerName : changeToBrokerNameSet(clusterInfo.getClusterAddrTable(),
                    consumerConfigInfo.getClusterNameList(), consumerConfigInfo.getBrokerNameList())) {
                mqAdminExt.createAndUpdateSubscriptionGroupConfig(clusterInfo.getBrokerAddrTable().get(brokerName).selectBrokerAddr(), consumerConfigInfo.getSubscriptionGroupConfig());
            }
        } catch (Exception err) {
            Throwables.throwIfUnchecked(err);
            throw new RuntimeException(err);
        }
        return true;
    }

    @Override
    public boolean deleteSubGroup(List<String> brokerNameList, DeleteSubGroupRequest deleteSubGroupRequest) {
        Set<String> brokerSet = Sets.newHashSet(brokerNameList);
        List<String> brokerList = deleteSubGroupRequest.getBrokerNameList();
        boolean deleteInNsFlag = false;
        // If the list of brokers passed in by the request contains the list of brokers that the consumer is in, delete RETRY and DLQ topic in namesrv
        if (brokerList.containsAll(brokerSet)) {
            deleteInNsFlag = true;
        }
        try {
            ClusterInfo clusterInfo = mqAdminExt.examineBrokerClusterInfo();
            for (String brokerName : deleteSubGroupRequest.getBrokerNameList()) {
                log.info("addr={} groupName={}", clusterInfo.getBrokerAddrTable().get(brokerName).selectBrokerAddr(), deleteSubGroupRequest.getGroupName());
                mqAdminExt.deleteSubscriptionGroup(clusterInfo.getBrokerAddrTable().get(brokerName).selectBrokerAddr(), deleteSubGroupRequest.getGroupName(), true);
                // Delete %RETRY%+Group and %DLQ%+Group in broker and namesrv
                deleteResources(MixAll.RETRY_GROUP_TOPIC_PREFIX + deleteSubGroupRequest.getGroupName(), brokerName, clusterInfo, deleteInNsFlag);
                deleteResources(MixAll.DLQ_GROUP_TOPIC_PREFIX + deleteSubGroupRequest.getGroupName(), brokerName, clusterInfo, deleteInNsFlag);
            }
        } catch (Exception e) {
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Map<String, ConsumerGroupRollBackStat> resetOffset(ResetOffsetRequest resetOffsetRequest) {
        Map<String, ConsumerGroupRollBackStat> groupRollbackStats = Maps.newHashMap();
        for (String consumerGroup : resetOffsetRequest.getConsumerGroupList()) {
            try {
                Map<MessageQueue, Long> rollbackStatsMap =
                        mqAdminExt.resetOffsetByTimestamp(resetOffsetRequest.getTopic(), consumerGroup, resetOffsetRequest.getResetTime(), resetOffsetRequest.isForce());
                ConsumerGroupRollBackStat consumerGroupRollBackStat = new ConsumerGroupRollBackStat(true);
                List<RollbackStats> rollbackStatsList = consumerGroupRollBackStat.getRollbackStatsList();
                for (Map.Entry<MessageQueue, Long> rollbackStatsEntity : rollbackStatsMap.entrySet()) {
                    RollbackStats rollbackStats = new RollbackStats();
                    rollbackStats.setRollbackOffset(rollbackStatsEntity.getValue());
                    rollbackStats.setQueueId(rollbackStatsEntity.getKey().getQueueId());
                    rollbackStats.setBrokerName(rollbackStatsEntity.getKey().getBrokerName());
                    rollbackStatsList.add(rollbackStats);
                }
                groupRollbackStats.put(consumerGroup, consumerGroupRollBackStat);
            } catch (MQClientException e) {
                if (ResponseCode.CONSUMER_NOT_ONLINE == e.getResponseCode()) {
                    try {
                        ConsumerGroupRollBackStat consumerGroupRollBackStat = new ConsumerGroupRollBackStat(true);
                        List<RollbackStats> rollbackStatsList = mqAdminExt.resetOffsetByTimestampOld(consumerGroup, resetOffsetRequest.getTopic(), resetOffsetRequest.getResetTime(), true);
                        consumerGroupRollBackStat.setRollbackStatsList(rollbackStatsList);
                        groupRollbackStats.put(consumerGroup, consumerGroupRollBackStat);
                        continue;
                    } catch (Exception err) {
                        log.error("op=resetOffset_which_not_online_error", err);
                    }
                } else {
                    log.error("op=resetOffset_error", e);
                }
                groupRollbackStats.put(consumerGroup, new ConsumerGroupRollBackStat(false, e.getMessage()));
            } catch (Exception e) {
                log.error("op=resetOffset_error", e);
                groupRollbackStats.put(consumerGroup, new ConsumerGroupRollBackStat(false, e.getMessage()));
            }
        }
        return groupRollbackStats;
    }

    @Override
    public ConsumerConfigInfo examineSubscriptionGroupConfig(String groupName) {
        try {
            ClusterInfo clusterInfo = mqAdminExt.examineBrokerClusterInfo();
            for (String brokerName : clusterInfo.getBrokerAddrTable().keySet()) {
                String brokerAddress = clusterInfo.getBrokerAddrTable().get(brokerName).selectBrokerAddr();
                SubscriptionGroupConfig subscriptionGroupConfig = mqAdminExt.examineSubscriptionGroupConfig(brokerAddress, groupName);
                if (subscriptionGroupConfig == null) {
                    continue;
                }
                return new ConsumerConfigInfo(Lists.newArrayList(brokerName), subscriptionGroupConfig);
            }
            throw new BizException(RmqRetCodeEnum.QUERY_CONSUMER_CONFIG_FAILED);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            Throwables.throwIfUnchecked(e);
            throw new BizException(RmqRetCodeEnum.QUERY_CONSUMER_CONFIG_FAILED);
        }
    }

    private void deleteResources(String topic, String brokerName, ClusterInfo clusterInfo, boolean deleteInNsFlag) throws Exception {
        mqAdminExt.deleteTopicInBroker(Sets.newHashSet(clusterInfo.getBrokerAddrTable().get(brokerName).selectBrokerAddr()), topic);
        Set<String> nameServerSet = null;
        if (StringUtils.isNotBlank(rmqProperties.getNamesrvAddr())) {
            String[] ns = rmqProperties.getNamesrvAddr().split(";");
            nameServerSet = new HashSet<>(Arrays.asList(ns));
        }
        if (deleteInNsFlag) {
            mqAdminExt.deleteTopicInNameServer(nameServerSet, topic);
        }
    }
}
