package com.ahcloud.edas.devops.jenkins.biz.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * devops历史任务表
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("devops_job_history")
public class DevopsJobHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * appId
     */
    private Long appId;

    /**
     * devops配置
     */
    private String config;

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
     * 开始时间
     */
    private Date startTime;

    /**
     * 终止时间
     */
    private Date abortTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 执行时长
     */
    private Long duration;

    /**
     * 预估时长
     */
    private Long estimatedDuration;

    /**
     * 原因
     */
    private String reason;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 行记录创建者
     */
    private String creator;

    /**
     * 行记录最近更新人
     */
    private String modifier;

    /**
     * 行记录创建时间
     */
    private Date createdTime;

    /**
     * 行记录最近修改时间
     */
    private Date modifiedTime;

    /**
     * 拓展字段
     */
    private String extension;

    /**
     * 是否删除
     */
    private Long deleted;


}
