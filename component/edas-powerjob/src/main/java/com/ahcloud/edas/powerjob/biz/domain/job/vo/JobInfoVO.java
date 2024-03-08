package com.ahcloud.edas.powerjob.biz.domain.job.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class JobInfoVO {

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

    /* ************************** Timing param. ************************** */
    /**
     * 时间表达式类型 默认cron
     * @see tech.powerjob.common.enums.TimeExpressionType
     */
    private Integer timeExpressionType;

    /**
     * 时间表达式 默认cron
     */
    private String timeExpression;

    /**
     * 执行类型 默认单机执行
     * @see tech.powerjob.common.enums.ExecuteType
     */
    private Integer executeType;

    /**
     * 执行器类型, {@code Java}, {@code Python} or {@code Shell}.默认内建处理器
     * @see tech.powerjob.common.enums.ProcessorType
     */
    private Integer processorType;

    /**
     * 执行器内容
     */
    private String processorInfo;

    /**
     * 任务参数
     */
    private String jobParams;

    /**
     * 任务描述
     */
    private String jobDescription;
}
