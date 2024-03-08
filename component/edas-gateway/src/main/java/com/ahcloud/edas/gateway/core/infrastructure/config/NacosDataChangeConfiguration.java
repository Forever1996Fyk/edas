package com.ahcloud.edas.gateway.core.infrastructure.config;

import com.ahcloud.edas.gateway.core.infrastructure.gateway.listener.DataChangeListener;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.listener.nacos.NacosDataChangeListener;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.config.NamespaceConfigFactory;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.naming.NamespaceNamingFactory;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.google.common.collect.Maps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Properties;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/6 22:52
 **/
@Configuration
@EnableConfigurationProperties(NacosProperties.class)
public class NacosDataChangeConfiguration {

    /**
     * Data changed listener data changed listener.
     * @param env
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(NacosDataChangeListener.class)
    public DataChangeListener nacosDataChangeListener(final NacosProperties nacosProperties) throws NacosException {
        Properties properties = createProperties(nacosProperties);
        return new NacosDataChangeListener(NamespaceConfigFactory.createConfigService(properties), properties);
    }

    /**
     * 构建namingService
     * @param nacosProperties
     * @return
     * @throws NacosException
     */
    @Bean
    public Map<String, NamingService> namingServiceMap(final NacosProperties nacosProperties) throws NacosException {
        Properties properties = createProperties(nacosProperties);
        Map<String, NamingService> result = Maps.newHashMap();
        Map<String, String> namespaceMap = nacosProperties.getNamespaceMap();
        for (Map.Entry<String, String> entry : namespaceMap.entrySet()) {
            result.put(entry.getKey(), NamespaceNamingFactory.createNamingService(entry.getValue(), properties));
        }
        return result;
    }

    private static Properties createProperties(NacosProperties nacosProperties) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosProperties.getServerAddr());
        properties.setProperty(PropertyKeyConst.USERNAME, nacosProperties.getUsername());
        properties.setProperty(PropertyKeyConst.PASSWORD, nacosProperties.getPassword());
        properties.setProperty(PropertyKeyConst.CONFIG_LONG_POLL_TIMEOUT, String.valueOf(nacosProperties.getConfigLongPollTimeout()));
        properties.setProperty(PropertyKeyConst.CONFIG_RETRY_TIME, String.valueOf(nacosProperties.getConfigRetryTime()));
        properties.setProperty(PropertyKeyConst.MAX_RETRY, String.valueOf(nacosProperties.getMaxRetry()));

        // 设置多环境 namespace
        properties.putAll(nacosProperties.getNamespaceMap());
        return properties;
    }
}
