package com.ahcloud.edas.powerjob.biz.domain.job.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 18:36
 **/
@Data
public class JobUpdateForm {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 任务名称
     */
    @NotEmpty(message = "任务名称不能为空")
    private String jobName;

    /**
     * 任务描述
     */
    @NotEmpty(message = "任务描述不能为空")
    private String jobDescription;

    /**
     * appId
     */
    @NotNull(message = "appId不能为空")
    private Long appId;

    /**
     * 任务参数
     */
    private String jobParams;

    /* ************************** Timing param. ************************** */
    /**
     * 时间表达式类型 默认cron
     * @see tech.powerjob.common.enums.TimeExpressionType
     */
    @NotNull(message = "表达式类型不能为空")
    private Integer timeExpressionType;

    /**
     * 时间表达式 默认cron
     */
    @NotEmpty(message = "表达式不能为空")
    private String timeExpression;

    /**
     * 执行类型 默认单机执行
     * @see tech.powerjob.common.enums.ExecuteType
     */
    @NotNull(message = "执行类型不能为空")
    private Integer executeType;

    /**
     * 执行器类型, {@code Java}, {@code Python} or {@code Shell}.默认内建处理器
     * @see tech.powerjob.common.enums.ProcessorType
     */
    @NotNull(message = "执行器类型不能为空")
    private Integer processorType;

    /**
     * 执行器内容
     */
    @NotEmpty(message = "执行器内容不能为空")
    private String processorInfo;
}
