package com.ahcloud.edas.devops.config.biz.domain.config.form;

import com.ahcloud.edas.devops.config.biz.domain.config.dto.DevelopmentConfig;
import com.ahcloud.edas.devops.gitlab.biz.domain.dto.GitlabConfig;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.dto.JenkinsConfig;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 17:17
 **/
@Data
public class DevopsConfigAddForm {

    /**
     * appId
     */
    @NotNull(message = "appId不能为空")
    private Long appId;

    /**
     * app编码
     */
    @NotEmpty(message = "app编码不能为空")
    private String appCode;

    /**
     * 环境变量
     */
    @NotEmpty(message = "环境变量不能为空")
    private String env;

    /**
     * devops类型
     * 1: Java, 2: Node, 3: html
     */
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 部署配置
     */
    private DevelopmentConfig developmentConfig;

    /**
     * gitlab配置
     */
    @Valid
    @NotNull(message = "gitlab配置不能为空")
    private GitlabConfig gitlabConfig;

    /**
     * jenkins配置
     */
    @Valid
    @NotNull(message = "jenkins配置不能为空")
    private JenkinsConfig jenkinsConfig;
}
