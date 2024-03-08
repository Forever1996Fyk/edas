package com.ahcloud.edas.pulsar.core.application.helper;

import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionAddForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.ConsumeProcessVO;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.SubscriptionConsumeVO;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.SubscriptionVO;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarSubscription;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopic;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.common.policies.data.ConsumerStats;
import org.apache.pulsar.common.policies.data.PartitionedTopicStats;
import org.apache.pulsar.common.policies.data.SubscriptionStats;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 10:59
 **/
public class SubscriptionHelper {

    /**
     * 数据转换
     * @param form
     * @param topicFullName
     * @return
     */
    public static PulsarSubscription convert(SubscriptionAddForm form, String topicFullName) {
        PulsarSubscription pulsarSubscription = Convert.INSTANCE.convert(form);
        pulsarSubscription.setTopicName(topicFullName);
        pulsarSubscription.setCreator(UserUtils.getUserNameBySession());
        pulsarSubscription.setModifier(UserUtils.getUserNameBySession());
        return pulsarSubscription;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static PulsarSubscription convert(SubscriptionUpdateForm form) {
        PulsarSubscription pulsarSubscription = Convert.INSTANCE.convert(form);
        pulsarSubscription.setModifier(UserUtils.getUserNameBySession());
        return pulsarSubscription;
    }

    /**
     * 数据转换
     * @param pulsarSubscription
     * @return
     */
    public static SubscriptionVO convertToVO(PulsarSubscription pulsarSubscription) {
        return Convert.INSTANCE.convertToVO(pulsarSubscription);
    }

    /**
     * 数据转换
     *
     * @param list
     * @param topicsStats
     * @return
     */
    public static List<SubscriptionVO> convertToVOList(List<PulsarSubscription> list, TopicStats topicsStats) {
        Map<String, ? extends SubscriptionStats> subscriptions = topicsStats.getSubscriptions();
        return list.stream()
                .map(pulsarSubscription -> {
                    SubscriptionVO subscriptionVO = Convert.INSTANCE.convertToVO(pulsarSubscription);
                    SubscriptionStats subscriptionStats = subscriptions.get(pulsarSubscription.getSubscriptionName());
                    if (Objects.nonNull(subscriptionStats)) {
                        long msgBacklog = subscriptionStats.getMsgBacklog();
                        subscriptionVO.setBacklog(msgBacklog);
                        subscriptionVO.setSubscriptionType(subscriptionStats.getType());
                    }
                    return subscriptionVO;
                }).collect(Collectors.toList());
    }

    /**
     * 数据转换
     * @param pulsarSubscription
     * @param pulsarTopic
     * @param subscriptions
     * @return
     */
    public static List<SubscriptionConsumeVO> convertToConsumeVOList(PulsarSubscription pulsarSubscription, PulsarTopic pulsarTopic, Map<String, ? extends SubscriptionStats> subscriptions) {
        SubscriptionStats subscriptionStats = subscriptions.get(pulsarSubscription.getSubscriptionName());
        List<? extends ConsumerStats> consumers = subscriptionStats.getConsumers();
        if (CollectionUtils.isEmpty(consumers)) {
            return Collections.emptyList();
        }
        return consumers.stream().map(
                consumerStats ->
                        SubscriptionConsumeVO.builder()
                                .topicId(pulsarTopic.getId())
                                .subscriptionId(pulsarSubscription.getId())
                                .consumeName(consumerStats.getConsumerName())
                                .clientAddress(consumerStats.getAddress())
                                .clientVersion(consumerStats.getClientVersion())
                                .startTime(DateUtils.parse(parseConnectedSince(consumerStats.getConnectedSince()), DateUtils.DatePattern.PATTERN_SPECIAL2))
                                .build()
        ).collect(Collectors.toList());
    }

    private static String parseConnectedSince(String time) {
        return StringUtils.split(time, ".")[0];
    }

    public static List<ConsumeProcessVO> convert(PulsarTopic pulsarTopic, PulsarSubscription subscription, TopicStats topicsStats, boolean partition) {
        if (partition) {
            PartitionedTopicStats partitionedTopicStats = (PartitionedTopicStats) topicsStats;
            Map<String, ? extends TopicStats> partitions = partitionedTopicStats.getPartitions();
            List<ConsumeProcessVO> consumeProcessVOList = Lists.newArrayList();
            for (Map.Entry<String, ? extends TopicStats> entry : partitions.entrySet()) {
                TopicStats topicStats = entry.getValue();
                String key = entry.getKey();
                ConsumeProcessVO consumeProcessVO = convertToProcess(StringUtils.substring(key, key.length() - 1), key, topicStats, pulsarTopic, subscription);
                if (Objects.nonNull(consumeProcessVO)) {
                    consumeProcessVOList.add(consumeProcessVO);
                }
            }
            return consumeProcessVOList;
        } else {
            return Collections.singletonList(convertToProcess("0", pulsarTopic.getTopicFullName(), topicsStats, pulsarTopic, subscription));
        }
    }

    private static ConsumeProcessVO convertToProcess(String partitionId, String topicName, TopicStats topicStats, PulsarTopic pulsarTopic, PulsarSubscription subscription) {
        Map<String, ? extends SubscriptionStats> subscriptions = topicStats.getSubscriptions();
        SubscriptionStats subscriptionStats = subscriptions.get(subscription.getSubscriptionName());
        return ConsumeProcessVO.builder()
                .topicId(pulsarTopic.getId())
                .subscriptionId(subscription.getId())
                .topicName(topicName)
                .backlog(subscriptionStats.getMsgBacklog())
                .msgRateOut(String.valueOf(subscriptionStats.getMsgRateOut()))
                .msgThroughPutOut(String.valueOf(subscriptionStats.getMsgThroughputOut()))
                .partitionId(partitionId)
                .build();
    }


    @Mapper
    public interface Convert {
        Convert INSTANCE =  Mappers.getMapper(Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        PulsarSubscription convert(SubscriptionAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        PulsarSubscription convert(SubscriptionUpdateForm form);

        /**
         * 数据转换
         * @param pulsarSubscription
         * @return
         */
        SubscriptionVO convertToVO(PulsarSubscription pulsarSubscription);

        /**
         * 数据转换
         * @param pulsarSubscriptionList
         * @return
         */
        List<SubscriptionVO> convertToVOList(List<PulsarSubscription> pulsarSubscriptionList);
    }
}
