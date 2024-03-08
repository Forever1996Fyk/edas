package com.ahcloud.edas.pulsar.core.infrastructure.pulsar.stats.impl;

import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTopicStatsService;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.stats.PulsarStatsService;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopicStats;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/3/6 17:14
 **/
@Component
public class PulsarStatsServiceImpl implements PulsarStatsService {
    @Resource
    private PulsarTopicStatsService pulsarTopicStatsService;

    @Override
    public Long findMaxTime() {
        PulsarTopicStats pulsarTopicStats = pulsarTopicStatsService.getOne(
                new QueryWrapper<PulsarTopicStats>().lambda()
                        .orderByDesc(PulsarTopicStats::getTimeStamp)
                        .last("limit 1")
        );
        return Objects.nonNull(pulsarTopicStats) ? pulsarTopicStats.getTimeStamp() : 0L;
    }

    @Override
    public Map<Long, PulsarTopicStats> getTopicStatsByTenant(List<Long> tenantIdList, Long timestamp) {
        List<PulsarTopicStats> pulsarTopicStatsList = pulsarTopicStatsService.list(
                new QueryWrapper<PulsarTopicStats>()
                        .select("tenant_id, sum(msg_rate_in) as msgRateIn,sum(msg_rate_out) as msgRateOut,sum(msg_throughput_in) as msgThroughputIn,sum(msg_throughput_out) as msgThroughputOut,sum(average_msg_size) as averageMsgSize,sum(storage_size) as storageSize, " +
                                "sum(msg_in_counter) as msgInCounter,sum(msg_out_counter) as msgOutCounter,sum(bytes_in_counter) as bytesInCounter,sum(bytes_out_counter) as bytesOutCounter")
                        .lambda()
                        .groupBy(PulsarTopicStats::getTenantId, PulsarTopicStats::getTimeStamp)
                        .in(PulsarTopicStats::getTenantId, tenantIdList)
                        .eq(PulsarTopicStats::getTimeStamp, timestamp)
        );
        return CollectionUtils.convertMap(pulsarTopicStatsList, PulsarTopicStats::getTenantId, Function.identity());
    }

    @Override
    public Map<Long, PulsarTopicStats> getTopicStatsByNamespace(List<Long> namespaceIdList, Long timestamp) {
        List<PulsarTopicStats> pulsarTopicStatsList = pulsarTopicStatsService.list(
                new QueryWrapper<PulsarTopicStats>()
                        .select("namespace_id, sum(msg_rate_in) as msgRateIn,sum(msg_rate_out) as msgRateOut,sum(msg_throughput_in) as msgThroughputIn,sum(msg_throughput_out) as msgThroughputOut,sum(average_msg_size) as averageMsgSize,sum(storage_size) as storageSize, " +
                                "sum(msg_in_counter) as msgInCounter,sum(msg_out_counter) as msgOutCounter,sum(bytes_in_counter) as bytesInCounter,sum(bytes_out_counter) as bytesOutCounter")
                        .lambda()
                        .groupBy(PulsarTopicStats::getNamespaceId, PulsarTopicStats::getTimeStamp)
                        .in(PulsarTopicStats::getNamespaceId, namespaceIdList)
                        .eq(PulsarTopicStats::getTimeStamp, timestamp)
        );
        return CollectionUtils.convertMap(pulsarTopicStatsList, PulsarTopicStats::getNamespaceId, Function.identity());
    }

    @Override
    public Map<Long, PulsarTopicStats> getTopicStatsByTenant(List<Long> tenantIdList) {
        Long maxTime = findMaxTime();
        if (Objects.equals(maxTime, 0L)) {
            return Collections.emptyMap();
        }
        return getTopicStatsByTenant(tenantIdList, maxTime);
    }

    @Override
    public Map<Long, PulsarTopicStats> getTopicStatsByNamespace(List<Long> namespaceIdList) {
        Long maxTime = findMaxTime();
        if (Objects.equals(maxTime, 0L)) {
            return Collections.emptyMap();
        }
        return getTopicStatsByNamespace(namespaceIdList, maxTime);
    }

    @Override
    public PulsarTopicStats getTopicStatsByTopicId(Long topicId) {
        Long maxTime = findMaxTime();
        if (Objects.equals(maxTime, 0L)) {
            return null;
        }
        return pulsarTopicStatsService.getOne(
                new QueryWrapper<PulsarTopicStats>()
                        .select("sum(msg_rate_in) as msgRateIn,sum(msg_rate_out) as msgRateOut,sum(msg_throughput_in) as msgThroughputIn,sum(msg_throughput_out) as msgThroughputOut,sum(average_msg_size) as averageMsgSize,sum(storage_size) as storageSize")
                        .lambda()
                        .groupBy(PulsarTopicStats::getTopicId, PulsarTopicStats::getTimeStamp)
                        .eq(PulsarTopicStats::getTopicId, topicId)
                        .eq(PulsarTopicStats::getTimeStamp, maxTime)
        );
    }
}
