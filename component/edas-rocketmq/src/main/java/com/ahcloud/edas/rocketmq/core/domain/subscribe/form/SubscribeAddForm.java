package com.ahcloud.edas.rocketmq.core.domain.subscribe.form;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 10:30
 **/
@Data
public class SubscribeAddForm {

    /**
     * broker列表
     */
    @Size(min = 1, max = 4, message = "最少选一个，最多选4个")
    private List<String> brokerNameList;

    /**
     * appId
     */
    @NotNull(message = "appId不能为空")
    private Long appId;

    /**
     * app编码
     */
    @NotEmpty(message = "app编码不能为空")
    private String appCode;

    /**
     * 环境变量
     */
    @NotEmpty(message = "环境变量不能为空")
    private String env;

    /**
     * topicId
     */
    @NotNull(message = "topicId不能为空")
    private Long topicId;

    /**
     * 订阅配置详情
     */
    @Valid
    @NotNull(message = "订阅配置详情不能为空")
    private SubscriptionGroupConfigDetail groupConfigDetail;
}
