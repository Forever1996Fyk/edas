package com.ahcloud.edas.devops.gitlab.biz.domain.dto;

import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 17:22
 **/
@Data
public class GitlabConfig {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目id
     */
    private Integer projectId;
}
