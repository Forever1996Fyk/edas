package com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.config;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 22:23
 **/
public interface NamespaceNacosConfigService extends ConfigService {

    /**
     * namespace Publish config.
     * @param namespace  namespace
     * @param dataId  dataId
     * @param group   group
     * @param content content
     * @return
     * @throws NacosException
     */
    boolean publishConfigWithNamespace(String namespace, String dataId, String group, String content) throws NacosException;


    /**
     * namespace Publish config.
     *
     * @param namespace  namespace
     * @param dataId  dataId
     * @param group   group
     * @param content content
     * @param type    config type {@link ConfigType}
     * @return Whether publish
     * @throws NacosException NacosException
     */
    boolean publishConfigWithNamespace(String namespace, String dataId, String group, String content, String type) throws NacosException;
}
