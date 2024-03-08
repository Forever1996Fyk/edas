package com.ahcloud.edas.devops.jenkins.biz.domain.job.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 16:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobHistoryDetailsVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * appId
     */
    private Long appId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务参数
     */
    private String params;

    /**
     * 任务版本
     */
    private Long version;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务状态
     */
    private Integer status;
}
