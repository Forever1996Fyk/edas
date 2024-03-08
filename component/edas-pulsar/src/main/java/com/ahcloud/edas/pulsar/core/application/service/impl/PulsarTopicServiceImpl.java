package com.ahcloud.edas.pulsar.core.application.service.impl;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopic;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.mapper.PulsarTopicMapper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTopicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * pulsar topic管理 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2024-02-18
 */
@Service
public class PulsarTopicServiceImpl extends ServiceImpl<PulsarTopicMapper, PulsarTopic> implements PulsarTopicService {

    @Override
    public PulsarTopic getById(Serializable id) {
        return baseMapper.selectOne(
                new QueryWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getId, id)
                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
        );
    }
}

