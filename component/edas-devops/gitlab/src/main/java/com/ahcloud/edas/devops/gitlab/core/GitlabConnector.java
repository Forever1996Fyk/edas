package com.ahcloud.edas.devops.gitlab.core;

import com.ahcloud.edas.devops.gitlab.config.GitlabProperties;
import org.gitlab4j.api.GitLabApi;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 09:53
 **/
public class GitlabConnector {

    private final GitlabProperties properties;

    public GitlabConnector(GitlabProperties properties) {
        this.properties = properties;
    }

    /**
     * 构建gitlab api
     * @return GitLabApi
     */
    public GitLabApi build() {
        return new GitLabApi(properties.getHost(), properties.getAccessToken(), properties.getSecretToken());
    }
}
