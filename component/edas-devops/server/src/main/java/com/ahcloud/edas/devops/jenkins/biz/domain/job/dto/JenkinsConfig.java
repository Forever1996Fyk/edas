package com.ahcloud.edas.devops.jenkins.biz.domain.job.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 23:24
 **/
@Data
public class JenkinsConfig {

    /**
     * jenkins链接
     */
    @NotEmpty(message = "jenkins链接不能为空")
    private String jenkinsUrl;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 任务名称
     */
    @NotEmpty(message = "任务名称不能为空")
    private String jobName;
}
