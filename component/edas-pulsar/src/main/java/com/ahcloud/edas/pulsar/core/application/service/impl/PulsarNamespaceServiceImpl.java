package com.ahcloud.edas.pulsar.core.application.service.impl;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarNamespace;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.mapper.PulsarNamespaceMapper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarNamespaceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * pulsar命名空间管理 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2024-02-18
 */
@Service
public class PulsarNamespaceServiceImpl extends ServiceImpl<PulsarNamespaceMapper, PulsarNamespace> implements PulsarNamespaceService {

    @Override
    public PulsarNamespace getById(Serializable id) {
        return baseMapper.selectOne(
                new QueryWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getId, id)
                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
        );
    }
}
