package com.ahcloud.edas.rocketmq.core.domain.topic.form;

import com.ahcloud.edas.common.annotation.EnumValid;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.TopicPermEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 11:08
 **/
@Data
public class TopicUpdateForm {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * brokers列表
     */
    @Size(min = 1, max = 6, message = "broker至少选一个")
    private List<String> brokerNameList;

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
