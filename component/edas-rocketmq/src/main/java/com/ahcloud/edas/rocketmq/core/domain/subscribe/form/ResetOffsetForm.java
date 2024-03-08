package com.ahcloud.edas.rocketmq.core.domain.subscribe.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/11 17:04
 **/
@Data
public class ResetOffsetForm {

    /**
     * topicId
     */
    @NotNull(message = "topic不能为空")
    private Long topicId;

    /**
     * 订阅组
     */
    @NotEmpty(message = "订阅组不能为空")
    private String groupName;

    /**
     * 重置时间
     */
    private Date resetTime;
}
