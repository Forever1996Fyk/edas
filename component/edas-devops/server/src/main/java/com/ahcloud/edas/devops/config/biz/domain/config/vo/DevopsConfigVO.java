package com.ahcloud.edas.devops.config.biz.domain.config.vo;

import com.ahcloud.edas.devops.config.biz.domain.config.dto.DevelopmentConfig;
import com.ahcloud.edas.devops.gitlab.biz.domain.dto.GitlabConfig;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.dto.JenkinsConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 17:19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevopsConfigVO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * appId
     */
    private Long appId;

    /**
     * app编码
     */
    private String appCode;

    /**
     * 环境变量
     */
    private String env;

    /**
     * 类型
     */
    private Integer type;

    /**
     * gitlab配置
     */
    private GitlabConfig gitlabConfig;

    /**
     * jenkins配置
     */
    private JenkinsConfig jenkinsConfig;

    /**
     * 部署配置
     */
    private DevelopmentConfig developmentConfig;
}
