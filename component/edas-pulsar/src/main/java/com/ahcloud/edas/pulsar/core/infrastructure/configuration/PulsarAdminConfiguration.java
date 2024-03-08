package com.ahcloud.edas.pulsar.core.infrastructure.configuration;

import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.impl.PulsarAdminServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/18 15:50
 **/
@Configuration
@EnableConfigurationProperties({PulsarProperties.class, BackendProperties.class})
public class PulsarAdminConfiguration {

    @Bean
    public PulsarAdminService pulsarAdminService(PulsarProperties properties) {
        return new PulsarAdminServiceImpl(properties);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
