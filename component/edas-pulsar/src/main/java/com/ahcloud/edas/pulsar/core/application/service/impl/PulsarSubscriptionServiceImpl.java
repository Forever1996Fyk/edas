package com.ahcloud.edas.pulsar.core.application.service.impl;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarSubscription;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.mapper.PulsarSubscriptionMapper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarSubscriptionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * pulsar subscription管理 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2024-02-18
 */
@Service
public class PulsarSubscriptionServiceImpl extends ServiceImpl<PulsarSubscriptionMapper, PulsarSubscription> implements PulsarSubscriptionService {


    @Override
    public PulsarSubscription getById(Serializable id) {
        return baseMapper.selectOne(
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, id)
                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
        );
    }
}
