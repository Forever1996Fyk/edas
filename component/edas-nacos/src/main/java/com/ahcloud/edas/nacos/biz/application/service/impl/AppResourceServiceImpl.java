package com.ahcloud.edas.nacos.biz.application.service.impl;

import com.ahcloud.edas.nacos.biz.application.service.AppResourceService;
import com.ahcloud.edas.nacos.biz.infrastructure.repository.bean.AppResource;
import com.ahcloud.edas.nacos.biz.infrastructure.repository.mapper.AppResourceMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * app资源表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-06
 */
@Service
@DS("edas-admin")
public class AppResourceServiceImpl extends ServiceImpl<AppResourceMapper, AppResource> implements AppResourceService {

}
