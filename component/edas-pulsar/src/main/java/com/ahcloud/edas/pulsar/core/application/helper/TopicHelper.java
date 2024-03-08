package com.ahcloud.edas.pulsar.core.application.helper;

import com.ahcloud.edas.common.enums.YesOrNoEnum;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.pulsar.core.domain.common.StatsDTO;
import com.ahcloud.edas.pulsar.core.domain.topic.form.TopicAddForm;
import com.ahcloud.edas.pulsar.core.domain.topic.form.TopicUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.topic.vo.TopicProduceVO;
import com.ahcloud.edas.pulsar.core.domain.topic.vo.TopicVO;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.TopicConstants;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PersistentEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.TopicSourceEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopic;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopicStats;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.google.common.collect.Lists;
import org.apache.pulsar.common.policies.data.PublisherStats;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 16:54
 **/
public class TopicHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static PulsarTopic convert(TopicAddForm form, String tenantName, String namespaceName) {
        PulsarTopic pulsarTopic = Convert.INSTANCE.convert(form);
        pulsarTopic.setSource(TopicSourceEnum.USER.getType());
        pulsarTopic.setTenantName(tenantName);
        pulsarTopic.setPoliciesJson(JsonUtils.toJsonString(form.getPolicyDTO()));
        pulsarTopic.setTopicFullName(buildTopicName(form.getTopicName(), tenantName, namespaceName, form.getPersistent()));
        pulsarTopic.setCreator(UserUtils.getUserNameBySession());
        pulsarTopic.setModifier(UserUtils.getUserNameBySession());
        return pulsarTopic;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static PulsarTopic convert(TopicUpdateForm form) {
        PulsarTopic pulsarTopic = Convert.INSTANCE.convert(form);
        pulsarTopic.setPoliciesJson(JsonUtils.toJsonString(form.getPolicyDTO()));
        pulsarTopic.setModifier(UserUtils.getUserNameBySession());
        return pulsarTopic;
    }

    /**
     * 构建完整topic名称
     * @param topic
     * @param tenant
     * @param namespace
     * @param persistent
     * @return
     */
    public static String buildTopicName(String topic, String tenant, String namespace, Integer persistent) {
        PersistentEnum persistentEnum = PersistentEnum.getByType(persistent);
        return String.format("%s%s/%s/%s", persistentEnum.getValue(), tenant, namespace, topic);
    }

    /**
     * 数据转换
     * @param pulsarTopic
     * @return
     */
    public static TopicVO convertToVO(PulsarTopic pulsarTopic) {
        return Convert.INSTANCE.convertToVO(pulsarTopic);
    }

    /**
     * 数据转换
     * @param pulsarTopicList
     * @return
     */
    public static List<TopicVO> convertToVOList(List<PulsarTopic> pulsarTopicList) {
        return Convert.INSTANCE.convertToVOList(pulsarTopicList);
    }

    /**
     * 数据转换
     * @param pulsarTopic
     * @param publishers
     * @return
     */
    public static List<TopicProduceVO> convertToProduceList(PulsarTopic pulsarTopic, List<? extends PublisherStats> publishers) {
        List<TopicProduceVO> list = Lists.newArrayList();
        for (PublisherStats publisher : publishers) {
            list.add(
                    TopicProduceVO.builder()
                            .produceId(publisher.getProducerId())
                            .produceName(publisher.getProducerName())
                            .averageMsgSize(String.valueOf(publisher.getAverageMsgSize()))
                            .produceAddress(publisher.getAddress())
                            .msgRateIn(String.format("%.2f", publisher.getMsgRateIn()))
                            .msgThroughputIn(String.format("%.2f", publisher.getMsgThroughputIn()))
                            .clientVersion(publisher.getClientVersion())
                            .build()
            );
        }
        return list;
    }

    /**
     * 数据转换
     * @param topicsStats
     * @return
     */
    public static StatsDTO convert(TopicStats topicsStats) {
        return StatsDTO.builder()
                .msgThroughPutIn(String.format("%.2f", topicsStats.getMsgThroughputIn()))
                .msgThroughPutOut(String.format("%.2f", topicsStats.getMsgThroughputIn()))
                .storageSize(topicsStats.getStorageSize())
                .msgRateOut(String.format("%.2f", topicsStats.getMsgRateOut()))
                .msgRateIn(String.format("%.2f", topicsStats.getMsgRateIn()))
                .msgInCounter(topicsStats.getMsgInCounter())
                .bytesInCounter(topicsStats.getBytesInCounter())
                .bytesOutCounter(topicsStats.getBytesOutCounter())
                .msgOutCounter(topicsStats.getMsgOutCounter())
                .build();
    }

    public static String buildPartitionTopic(String topicFullName, String partitionId) {
        return String.format("%s-partition-%s", topicFullName, partitionId);
    }

    public static StatsDTO convert(PulsarTopicStats pulsarTopicStats) {
        return StatsDTO.builder()
                .msgThroughPutIn(String.valueOf(pulsarTopicStats.getMsgThroughputIn()))
                .msgThroughPutOut(String.valueOf(pulsarTopicStats.getMsgThroughputIn()))
                .storageSize(pulsarTopicStats.getStorageSize())
                .msgRateOut(String.valueOf(pulsarTopicStats.getMsgRateOut()))
                .msgRateIn(String.valueOf(pulsarTopicStats.getMsgRateIn()))
                .msgInCounter(pulsarTopicStats.getMsgInCounter())
                .bytesInCounter(pulsarTopicStats.getBytesInCounter())
                .bytesOutCounter(pulsarTopicStats.getBytesOutCounter())
                .msgOutCounter(pulsarTopicStats.getMsgOutCounter())
                .build();
    }

    @Mapper(uses = TopicSourceEnum.class)
    public interface Convert {
       Convert INSTANCE =  Mappers.getMapper(Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
       PulsarTopic convert(TopicAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        PulsarTopic convert(TopicUpdateForm form);

        /**
         * 数据转换
         * @param pulsarTopic
         * @return
         */
        @Mappings({
                @Mapping(target = "policyDTO", expression = "java(com.ahcloud.edas.common.util.JsonUtils.stringToBean(pulsarTopic.getPoliciesJson(), com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO.class))"),
        })
        TopicVO convertToVO(PulsarTopic pulsarTopic);

        /**
         * 数据转换
         * @param pulsarTopicList
         * @return
         */
        List<TopicVO> convertToVOList(List<PulsarTopic> pulsarTopicList);
    }
}
