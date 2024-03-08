package com.ahcloud.edas.pulsar.core.application.service.impl;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTenant;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.mapper.PulsarTenantMapper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTenantService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * pulsar租户管理 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2024-02-18
 */
@Service
public class PulsarTenantServiceImpl extends ServiceImpl<PulsarTenantMapper, PulsarTenant> implements PulsarTenantService {

    @Override
    public PulsarTenant getById(Serializable id) {
        return baseMapper.selectOne(
                new QueryWrapper<PulsarTenant>().lambda()
                        .eq(PulsarTenant::getId, id)
                        .eq(PulsarTenant::getDeleted, DeletedEnum.NO.value)
        );
    }
}
