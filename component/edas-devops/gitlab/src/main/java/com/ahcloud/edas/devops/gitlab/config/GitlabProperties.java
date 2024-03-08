package com.ahcloud.edas.devops.gitlab.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 09:41
 **/
@Data
@ConfigurationProperties(prefix = "devops.gitlab")
public class GitlabProperties {

    /**
     * gitlab地址
     */
    private String host;

    /**
     * 访问token
     */
    private String accessToken;

    /**
     * webhook 密钥
     */
    private String secretToken;
}
