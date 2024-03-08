package com.ahcloud.edas.powerjob.biz.domain.job.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 18:45
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JobVO {

    /**
     * 任务id
     */
    private Long id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * appId
     */
    private Long appId;

    /**
     * 表达式类型
     */
    private Integer timeExpressionType;

    /**
     * 表达式类型
     */
    private String timeExpressionTypeName;

    /**
     * 时间表达式，CRON/NULL/LONG/LONG
     */
    private String timeExpression;

    /**
     * 执行类型，单机/广播/MR
     */
    private Integer executeType;

    /**
     * 执行类型，单机/广播/MR
     */
    private String executeTypeName;

    /**
     * 执行器类型，Java/Shell
     */
    private Integer processorType;

    /**
     * 执行器类型，Java/Shell
     */
    private String processorTypeName;

    /**
     * 1 正常运行，2 停止（不再调度）
     */
    private Integer status;

    /**
     * 下一次调度时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime nextTriggerTime;

    /**
     * 任务整体超时时间
     */
    private Long instanceTimeLimit;
}
