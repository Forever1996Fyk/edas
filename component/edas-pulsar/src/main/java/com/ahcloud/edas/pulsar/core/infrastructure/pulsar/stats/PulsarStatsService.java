package com.ahcloud.edas.pulsar.core.infrastructure.pulsar.stats;

import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopicStats;

import java.util.List;
import java.util.Map;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/3/6 17:13
 **/
public interface PulsarStatsService {

    /**
     * 获取最新时间戳
     * @return
     */
    Long findMaxTime();

    /**
     * 根据租户id查询统计信息
     * @param tenantIdList
     * @param timestamp
     * @return key: tenantId, value: PulsarTopicStats
     */
    Map<Long, PulsarTopicStats> getTopicStatsByTenant(List<Long> tenantIdList, Long timestamp);


    /**
     * 根据namespaceId查询统计信息
     * @param namespaceIdList
     * @param timestamp
     * @return key: namespaceId, value: PulsarTopicStats
     */
    Map<Long, PulsarTopicStats> getTopicStatsByNamespace(List<Long> namespaceIdList, Long timestamp);

    /**
     * 根据租户id查询统计信息
     * @param tenantIdList
     * @return key: tenantId, value: PulsarTopicStats
     */
    Map<Long, PulsarTopicStats> getTopicStatsByTenant(List<Long> tenantIdList);

    /**
     * 根据namespaceId查询统计信息
     * @param namespaceIdList
     * @return key: namespaceId, value: PulsarTopicStats
     */
    Map<Long, PulsarTopicStats> getTopicStatsByNamespace(List<Long> namespaceIdList);

    /**
     * 根据topicId查询统计信息
     * @param topicId
     * @return
     */
    PulsarTopicStats getTopicStatsByTopicId(Long topicId);
}
