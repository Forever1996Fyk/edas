package com.ahcloud.edas.devops.gitlab.config;

import com.ahcloud.edas.devops.gitlab.core.GitlabApiService;
import com.ahcloud.edas.devops.gitlab.core.GitlabConnector;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 09:40
 **/
@Configuration
@EnableConfigurationProperties(GitlabProperties.class)
public class GitlabConfiguration {

    /**
     * 加载gitlab api bean
     * @param properties
     * @return
     */
    @Bean
    public GitlabApiService gitlabApiService(GitlabProperties properties) {
        return new GitlabApiService(new GitlabConnector(properties));
    }

}
