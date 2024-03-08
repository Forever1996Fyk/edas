package com.ahcloud.edas.powerjob.biz.domain.job.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/22 09:25
 **/
@Data
public class OperateJobForm {

    /**
     * appId
     */
    @NotNull(message = "appId不能为空")
    private Long powerJobAppId;

    /**
     * 任务id
     */
    @NotNull(message = "任务id不能为空")
    private Long jobId;

    /**
     * 实例参数
     */
    private String instanceParams;

    /**
     * 延迟毫秒
     */
    private Long delayMS;
}
