package com.ahcloud.edas.powerjob.biz.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.powerjob.client.PowerJobClient;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 15:52
 **/
@Configuration
@EnableConfigurationProperties(PowerJobProperties.class)
public class PowerJobConfiguration {

    @Bean
    public PowerJobClient powerJobClient(PowerJobProperties properties) {
        return new PowerJobClient(properties.getUrlList());
    }
}
