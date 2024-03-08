package com.ahcloud.edas.gateway.core.infrastructure.gateway.listener.nacos;

import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.gateway.core.infrastructure.constant.NacosPathConstants;
import com.ahcloud.edas.gateway.core.infrastructure.exception.GatewayException;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.listener.AbstractListDataChangedListener;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.config.NamespaceNacosConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/6 16:22
 **/
@Slf4j
public class NacosDataChangeListener extends AbstractListDataChangedListener {

    private final Properties properties;
    private final NamespaceNacosConfigService configService;

    public NacosDataChangeListener(final NamespaceNacosConfigService configService, Properties properties) {
        super(new ChangeData(NacosPathConstants.ROUTE_DATA_ID, NacosPathConstants.API_DATA_ID));
        this.configService = configService;
        this.properties = properties;
    }

    @Override
    protected void publishConfig(String dataId, Object data) {
        try {
            configService.publishConfig(
                    dataId,
                    NacosPathConstants.GROUP,
                    JsonUtils.toJsonString(data),
                    ConfigType.JSON.getType()
            );
        } catch (NacosException e) {
            log.error("Publish data to nacos error, reason is {}", Throwables.getStackTraceAsString(e));
            throw new GatewayException(e.getMessage());
        }
    }

    @Override
    protected void publishConfig(String env, String dataId, Object data) {
        try {
            String namespace = properties.getProperty(env);
            configService.publishConfigWithNamespace(
                    namespace,
                    dataId,
                    NacosPathConstants.GROUP,
                    JsonUtils.toJsonString(data),
                    ConfigType.JSON.getType()
            );
        } catch (NacosException e) {
            log.error("Publish data to nacos error, reason is {}", Throwables.getStackTraceAsString(e));
            throw new GatewayException(e.getMessage());
        }
    }
}
