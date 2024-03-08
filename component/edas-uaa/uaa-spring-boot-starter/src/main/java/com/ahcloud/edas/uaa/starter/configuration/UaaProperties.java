package com.ahcloud.edas.uaa.starter.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-24 14:27
 **/
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "uaa")
public class UaaProperties {

    /**
     * 认证放行
     */
    private Set<String> permitAll;
}
