package com.ahcloud.edas.pulsar.core.application.service.impl;

import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarProducerStats;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.mapper.PulsarProducerStatsMapper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarProducerStatsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * pulsar生产者状态统计 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2024-03-05
 */
@Service
public class PulsarProducerStatsServiceImpl extends ServiceImpl<PulsarProducerStatsMapper, PulsarProducerStats> implements PulsarProducerStatsService {

}
