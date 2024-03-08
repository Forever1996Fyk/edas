package com.ahcloud.edas.pulsar.core.infrastructure.pulsar.schedule;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.pulsar.core.application.service.PulsarConsumerStatsService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarNamespaceService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarProducerStatsService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarSubscriptionStatsService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTenantService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTopicService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTopicStatsService;
import com.ahcloud.edas.pulsar.core.domain.broker.dto.PulsarManagerConsumerStats;
import com.ahcloud.edas.pulsar.core.domain.broker.dto.PulsarManagerPublisherStats;
import com.ahcloud.edas.pulsar.core.domain.broker.dto.PulsarManagerSubscriptionStats;
import com.ahcloud.edas.pulsar.core.domain.broker.dto.PulsarManagerTopicStats;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarConsumerStats;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarNamespace;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarProducerStats;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarSubscriptionStats;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTenant;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopic;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopicStats;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.admin.BrokerStats;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/3/4 16:25
 **/
@Slf4j
@Component
public class ScheduleCollectStats {
    @Value("${clear.stats.interval:300000}")
    private Long clearStatsInterval;

    @Resource
    private PulsarAdminService pulsarAdminService;
    @Resource
    private PulsarTopicService pulsarTopicService;
    @Resource
    private PulsarTenantService pulsarTenantService;
    @Resource
    private PulsarNamespaceService pulsarNamespaceService;
    @Resource
    private PulsarTopicStatsService pulsarTopicStatsService;
    @Resource
    private PulsarConsumerStatsService pulsarConsumerStatsService;
    @Resource
    private PulsarProducerStatsService pulsarProducerStatsService;
    @Resource
    private PulsarSubscriptionStatsService pulsarSubscriptionStatsService;

    /**
     * 定时30秒收集状态数据
     */
    @Scheduled(initialDelay = 0, fixedDelay = 30000)
    public void collectStats() throws PulsarAdminException {
        List<PulsarTenant> pulsarTenantList = pulsarTenantService.list(
                new QueryWrapper<PulsarTenant>().lambda()
                        .select(PulsarTenant::getId, PulsarTenant::getTenantName)
                        .eq(PulsarTenant::getDeleted, DeletedEnum.NO.value)
        );
        Map<String, Long> tenantMap = CollectionUtils.convertMap(pulsarTenantList, PulsarTenant::getTenantName, PulsarTenant::getId);
        List<PulsarNamespace> pulsarNamespaceList = pulsarNamespaceService.list(
                new QueryWrapper<PulsarNamespace>().lambda()
                        .in(PulsarNamespace::getTenantId, tenantMap.values())
                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
        );
        List<PulsarTopic> pulsarTopicList = pulsarTopicService.list(
                new QueryWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
        );
        Map<Long, Map<Long, Map<String, Long>>> tenantNamespaceTopicMap = pulsarTopicList.stream().collect(
                Collectors.groupingBy(PulsarTopic::getTenantId, Collectors.groupingBy(PulsarTopic::getNamespaceId, Collectors.toMap(PulsarTopic::getTopicName, PulsarTopic::getId)))
        );
        Map<Long, Map<String, Long>> tenantNamespaceMap = pulsarNamespaceList.stream().collect(Collectors.groupingBy(PulsarNamespace::getTenantId, Collectors.toMap(PulsarNamespace::getNamespaceName, PulsarNamespace::getId)));

        long unixTime = System.currentTimeMillis() / 1000L;
        BrokerStats brokerStats = pulsarAdminService.brokerStats();
        String topics = brokerStats.getTopics();
        Map<String, Map<String, Map<String, Map<String, PulsarManagerTopicStats>>>> brokerStatsTopicEntity =
                JsonUtils.stringToMap3(topics, new TypeReference<>() {});
        if (CollectionUtils.isEmpty(brokerStatsTopicEntity)) {
            log.warn("brokerStats is empty");
            return;
        }
        List<PulsarTopicStats> topicStatsList = Lists.newArrayList();
        List<PulsarSubscriptionStats> subscriptionStatsList = Lists.newArrayList();
        List<PulsarConsumerStats> consumerStatsList = Lists.newArrayList();
        List<PulsarProducerStats> producerStatsList = Lists.newArrayList();
        log.info("Start collecting stats");
        brokerStatsTopicEntity.forEach((namespace, namespaceStats) -> {
            namespaceStats.forEach((bundle, bundleStats) -> {
                bundleStats.forEach((persistent, persistentStats) -> {
                    persistentStats.forEach((topic, topicStats) -> {
                        PulsarTopicStats pulsarTopicStats = new PulsarTopicStats();
                        String[] topicPath = this.parseTopic(topic);
                        Long tenantId = tenantMap.get(topicPath[0]);
                        if (Objects.nonNull(tenantId)) {
                            pulsarTopicStats.setTenantId(tenantId);
                            Map<String, Long> namespaceMap = tenantNamespaceMap.get(tenantId);
                            if (CollectionUtils.isNotEmpty(namespaceMap)) {
                                Long namespaceId = namespaceMap.get(topicPath[1]);
                                if (Objects.nonNull(namespaceId)) {
                                    pulsarTopicStats.setNamespaceId(namespaceId);
                                    pulsarTopicStats.setNamespaceName(topicPath[1]);
                                    pulsarTopicStats.setBundle(bundle);
                                    pulsarTopicStats.setPersistent(persistent);
                                    pulsarTopicStats.setTopicName(topicPath[2]);
                                    Map<Long, Map<String, Long>> namespaceTopicMap = tenantNamespaceTopicMap.get(tenantId);
                                    if (CollectionUtils.isNotEmpty(namespaceTopicMap)) {
                                        Map<String, Long> topicMap = namespaceTopicMap.get(namespaceId);
                                        if (CollectionUtils.isNotEmpty(topicMap)) {
                                            String topicName = renameTopic(topicPath[2]);
                                            Long topicId = topicMap.get(topicName);
                                            if (Objects.nonNull(topicId)) {
                                                pulsarTopicStats.setTopicId(topicId);
                                                pulsarTopicStats.setTopicName(topicPath[2]);
                                                pulsarTopicStats.setMsgRateIn(doubleToBigDecimalScale2(topicStats.getMsgRateIn()));
                                                pulsarTopicStats.setMsgRateOut(doubleToBigDecimalScale2(topicStats.getMsgRateOut()));
                                                pulsarTopicStats.setMsgThroughputIn(doubleToBigDecimalScale2(topicStats.getMsgThroughputIn()));
                                                pulsarTopicStats.setMsgThroughputOut(doubleToBigDecimalScale2(topicStats.getMsgThroughputOut()));
                                                pulsarTopicStats.setAverageMsgSize(doubleToBigDecimalScale2(topicStats.getAverageMsgSize()));
                                                pulsarTopicStats.setMsgInCounter(topicStats.getMsgInCount());
                                                pulsarTopicStats.setMsgOutCounter(topicStats.getMsgOutCount());
                                                pulsarTopicStats.setBytesInCounter(topicStats.getBytesInCount());
                                                pulsarTopicStats.setBytesOutCounter(topicStats.getBytesOutCount());
                                                pulsarTopicStats.setStorageSize(topicStats.getStorageSize());
                                                pulsarTopicStats.setTimeStamp(unixTime);
                                                topicStatsList.add(pulsarTopicStats);
                                                Map<String, PulsarManagerSubscriptionStats> subscriptions = topicStats.getSubscriptions();
                                                if (CollectionUtils.isNotEmpty(subscriptions)) {
                                                    subscriptions.forEach((subscription, subscriptionStats) -> {
                                                        PulsarSubscriptionStats pulsarSubscriptionStats = new PulsarSubscriptionStats();
                                                        pulsarSubscriptionStats.setNamespaceId(namespaceId);
                                                        pulsarSubscriptionStats.setTenantId(tenantId);
                                                        pulsarSubscriptionStats.setNamespaceName(topicPath[1]);
                                                        pulsarSubscriptionStats.setTenantName(topicPath[0]);
                                                        pulsarSubscriptionStats.setMsgRateOut(doubleToBigDecimalScale2(subscriptionStats.getMsgRateOut()));
                                                        pulsarSubscriptionStats.setMsgRateExpired(doubleToBigDecimalScale2(subscriptionStats.getMsgRateExpired()));
                                                        pulsarSubscriptionStats.setMsgThroughputOut(doubleToBigDecimalScale2(subscriptionStats.getMsgThroughputOut()));
                                                        pulsarSubscriptionStats.setMsgRateRedeliver(doubleToBigDecimalScale2(subscriptionStats.getMsgRateRedeliver()));
                                                        pulsarSubscriptionStats.setTimeStamp(unixTime);
                                                        subscriptionStatsList.add(pulsarSubscriptionStats);
                                                        List<PulsarManagerConsumerStats> consumers = subscriptionStats.getConsumers();
                                                        if (CollectionUtils.isNotEmpty(consumers)) {
                                                            for (PulsarManagerConsumerStats consumer : consumers) {
                                                                PulsarConsumerStats pulsarConsumerStats = new PulsarConsumerStats();
                                                                pulsarConsumerStats.setTenantId(tenantId);
                                                                pulsarConsumerStats.setNamespaceId(namespaceId);
                                                                pulsarConsumerStats.setTenantName(topicPath[0]);
                                                                pulsarConsumerStats.setNamespaceName(topicPath[1]);
                                                                pulsarConsumerStats.setConsumerName(consumer.getConsumerName());
                                                                pulsarConsumerStats.setMsgRateOut(doubleToBigDecimalScale2(subscriptionStats.getMsgRateOut()));
                                                                pulsarConsumerStats.setMsgThroughputOut(doubleToBigDecimalScale2(subscriptionStats.getMsgThroughputOut()));
                                                                pulsarConsumerStats.setMsgRateRedeliver(doubleToBigDecimalScale2(subscriptionStats.getMsgRateRedeliver()));
                                                                pulsarConsumerStats.setTimeStamp(unixTime);
                                                                consumerStatsList.add(pulsarConsumerStats);
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                            List<PulsarManagerPublisherStats> publishers = topicStats.getPublishers();
                                            if (CollectionUtils.isNotEmpty(publishers)) {
                                                for (PulsarManagerPublisherStats publisher : publishers) {
                                                    PulsarProducerStats pulsarProducerStats = new PulsarProducerStats();
                                                    pulsarProducerStats.setTopicId(topicId);
                                                    pulsarProducerStats.setTenantName(topicPath[2]);
                                                    pulsarProducerStats.setNamespaceId(namespaceId);
                                                    pulsarProducerStats.setTenantId(tenantId);
                                                    pulsarProducerStats.setNamespaceName(topicPath[1]);
                                                    pulsarProducerStats.setTenantName(topicPath[0]);
                                                    pulsarProducerStats.setProducerId(publisher.getProducerId());
                                                    pulsarProducerStats.setProducerName(publisher.getProducerName());
                                                    pulsarProducerStats.setMsgRateIn(doubleToBigDecimalScale2(topicStats.getMsgRateIn()));
                                                    pulsarProducerStats.setMsgThroughputIn(doubleToBigDecimalScale2(topicStats.getMsgThroughputIn()));
                                                    pulsarProducerStats.setAverageMsgSize(doubleToBigDecimalScale2(topicStats.getAverageMsgSize()));
                                                    pulsarProducerStats.setStorageSize(topicStats.getStorageSize());
                                                    pulsarProducerStats.setTimeStamp(unixTime);
                                                    producerStatsList.add(pulsarProducerStats);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    });
                });
            });
        });
        if (CollectionUtils.isNotEmpty(topicStatsList)) {
            pulsarTopicStatsService.saveBatch(topicStatsList);
        }
        if (CollectionUtils.isNotEmpty(subscriptionStatsList)) {
            pulsarSubscriptionStatsService.saveBatch(subscriptionStatsList);
        }
        if (CollectionUtils.isNotEmpty(consumerStatsList)) {
            pulsarConsumerStatsService.saveBatch(consumerStatsList);
        }
        if (CollectionUtils.isNotEmpty(producerStatsList)) {
            pulsarProducerStatsService.saveBatch(producerStatsList);
        }
        log.info("Start clearing stats from broker");
        clearStats(unixTime, clearStatsInterval / 1000);
    }

    public void clearStats(long nowTime, long timeInterval) {
        long refTime = nowTime - timeInterval;
        pulsarTopicStatsService.remove(
                new QueryWrapper<PulsarTopicStats>().lambda()
                        .lt(PulsarTopicStats::getTimeStamp, refTime)
        );
        pulsarSubscriptionStatsService.remove(
                new QueryWrapper<PulsarSubscriptionStats>().lambda()
                        .lt(PulsarSubscriptionStats::getTimeStamp, refTime)
        );
        pulsarConsumerStatsService.remove(
                new QueryWrapper<PulsarConsumerStats>().lambda()
                        .lt(PulsarConsumerStats::getTimeStamp, refTime)
        );
        pulsarProducerStatsService.remove(
                new QueryWrapper<PulsarProducerStats>().lambda()
                        .lt(PulsarProducerStats::getTimeStamp, refTime)
        );
    }

    private String renameTopic(String topicPartition) {
        return topicPartition.replaceAll("-partition-\\d+", "");
    }

    private BigDecimal doubleToBigDecimalScale2(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.DOWN);
    }

    private List<String> buildTenantNamespaceKey(List<PulsarTenant> pulsarTenantList, List<PulsarNamespace> pulsarNamespaceList) {
        Map<Long, List<PulsarNamespace>> tenantNamespaceMap = pulsarNamespaceList.stream().collect(Collectors.groupingBy(PulsarNamespace::getTenantId));
        Set<String> keySet = Sets.newHashSet();
        for (PulsarTenant pulsarTenant : pulsarTenantList) {
            List<PulsarNamespace> pulsarNamespaces = tenantNamespaceMap.get(pulsarTenant.getId());
            if (CollectionUtils.isEmpty(pulsarNamespaces)) {
                continue;
            }
            for (PulsarNamespace pulsarNamespace : pulsarNamespaces) {
                keySet.add(String.format("%s/%s", pulsarTenant.getTenantName(), pulsarNamespace.getNamespaceName()));
            }
        }
        return Lists.newArrayList(keySet);
    }

    private String[] parseTopic(String topic) {
        String tntPath = topic.split("://")[1];
        return tntPath.split("/");
    }

}
