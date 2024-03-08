package com.ahcloud.edas.devops.jenkins.core;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 19:55
 **/
public class JenkinsConnector {

    /**
     * jenkins地址
     */
    private final String jenkinsUrl;

    /**
     * 账号
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    @Getter
    private JenkinsServer jenkinsServer;
    @Getter
    private JenkinsHttpClient jenkinsHttpClient;

    public JenkinsConnector(String jenkinsUrl, String username, String password) {
        this.jenkinsUrl = jenkinsUrl;
        this.username = username;
        this.password = password;
    }

    public JenkinsConnector connector() throws URISyntaxException {
        JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(
                new URI(this.jenkinsUrl),
                this.username,
                this.password
        );
        this.jenkinsHttpClient = jenkinsHttpClient;
        this.jenkinsServer = new JenkinsServer(jenkinsHttpClient);
        return this;
    }

}
