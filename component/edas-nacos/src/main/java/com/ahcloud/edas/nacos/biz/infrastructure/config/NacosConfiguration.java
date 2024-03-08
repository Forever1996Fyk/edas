package com.ahcloud.edas.nacos.biz.infrastructure.config;

import com.ahcloud.edas.nacos.biz.infrastructure.nacos.NacosConnector;
import com.ahcloud.edas.nacos.biz.infrastructure.nacos.NacosOpenApiClient;
import com.alibaba.nacos.common.http.client.NacosRestTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:46
 **/
@Configuration
@EnableConfigurationProperties(NacosProperties.class)
public class NacosConfiguration {

    @Bean
    public NacosConnector nacosConnector(NacosProperties nacosProperties) {
        return new NacosConnector(nacosProperties);
    }

    @Bean
    public NacosOpenApiClient nacosOpenApiClient(NacosConnector nacosConnector) {
        return new NacosOpenApiClient(nacosConnector);
    }

}
