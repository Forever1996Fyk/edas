package com.ahcloud.edas.pulsar.core.infrastructure.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 11:32
 **/
@Data
@ConfigurationProperties(prefix = "edas.backend")
public class BackendProperties {

    /**
     * 直接请求地址
     */
    private String directRequestHost;
}
