package com.ahcloud.edas.devops.jenkins.biz.domain.job.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 23:15
 **/
@Data
public class JenkinsJobParams {

    /**
     * gitlab分支
     */
    @NotEmpty(message = "分支不能为空")
    private String branch;
}
