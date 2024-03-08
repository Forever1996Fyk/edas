package com.ahcloud.edas.devops.jenkins.biz.domain.job.form;

import com.ahcloud.edas.devops.jenkins.biz.domain.job.dto.JenkinsJobParams;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 23:13
 **/
@Data
public class DevopsAddJobForm {

    /**
     * appId
     */
    @NotNull(message = "appId不能为空")
    private Long appId;

    /**
     * 任务参数
     */
    @Valid
    @NotNull(message = "任务参数不能为空")
    private JenkinsJobParams params;

    /**
     * 任务描述
     */
    private String description;
}
