package com.ahcloud.edas.rocketmq.core.domain.topic.form;

import com.ahcloud.edas.common.annotation.EnumValid;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.TopicPermEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 11:08
 **/
@Data
public class TopicAddForm {

    /**
     * 应用id
     */
    @NotNull(message = "appId不能为空")
    private Long appId;

    /**
     * 应用code
     */
    @NotEmpty(message = "appCode不能为空")
    private String appCode;

    /**
     * 环境变量
     */
    @NotEmpty(message = "环境不能为空")
    private String env;

    /**
     * brokers列表
     */
    @Size(min = 1, max = 6, message = "broker至少选一个")
    private List<String> brokerNameList;

    /**
     * topic名称
     */
    @NotEmpty(message = "topic名称不能为空")
    @Pattern(regexp = "^[%|a-zA-Z0-9_-]+$", message = "topic名称格式错误")
    private String topicName;

    /**
     * 写队列数
     */
    @Max(value = 16, message = "队列数最大为16")
    @Min(value = 1, message = "队列数最小为1")
    private Integer writeQueueNum;

    /**
     * 读队列数
     */
    @Max(value = 16, message = "队列数最大为16")
    @Min(value = 1, message = "队列数最小为1")
    private Integer readQueueNum;

    /**
     * topic权限
     */
    @EnumValid(enumClass = TopicPermEnum.class, enumMethod = "isValid")
    private Integer perm;
}
