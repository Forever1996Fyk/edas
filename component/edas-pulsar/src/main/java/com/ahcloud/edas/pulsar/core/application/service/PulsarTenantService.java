package com.ahcloud.edas.pulsar.core.application.service;

import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTenant;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 * pulsar租户管理 服务类
 * </p>
 *
 * @author auto_generation
 * @since 2024-02-18
 */
public interface PulsarTenantService extends IService<PulsarTenant> {

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Override
    PulsarTenant getById(Serializable id);

}
