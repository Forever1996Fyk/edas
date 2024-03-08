package com.ahcloud.edas.devops.jenkins.biz.domain.job.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 16:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobHistoryVO {

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

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 终止时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date abortTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 执行时长
     */
    private String duration;

    /**
     * 预估时长
     */
    private String estimatedDuration;

    /**
     * 原因
     */
    private String reason;

    /**
     * 是否可终止
     */
    private boolean enableAbort;

}
