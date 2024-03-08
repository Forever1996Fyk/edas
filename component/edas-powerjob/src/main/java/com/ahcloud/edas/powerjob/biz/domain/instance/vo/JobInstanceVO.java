package com.ahcloud.edas.powerjob.biz.domain.instance.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.powerjob.common.enums.InstanceStatus;

import java.time.LocalDateTime;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/22 10:02
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JobInstanceVO {

    /**
     * 任务ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long jobId;

    /**
     * 任务所属应用的ID，冗余提高查询效率
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long powerJobAppId;

    /**
     * 任务实例ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long instanceId;

    /**
     * 工作流实例ID
     */
    private Long wfInstanceId;

    /**
     * 任务参数
     */
    private String jobParams;

    /**
     * 任务实例参数
     */
    private String instanceParams;

    /**
     * 任务状态 {@link InstanceStatus}
     */
    private Integer status;

    /**
     * 任务状态 {@link InstanceStatus}
     */
    private String statusName;

    /**
     *  该任务实例的类型，普通/工作流（InstanceType）
     */
    private Integer type;

    /**
     * 执行结果
     */
    private String result;

    /**
     * 预计触发时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime expectedTriggerTime;

    /**
     * 实际触发时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime actualTriggerTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime finishedTime;

    /**
     * TaskTracker地址
     */
    private String taskTrackerAddress;

    /**
     * 总共执行的次数（用于重试判断）
     */
    private Long runningTimes;
}
