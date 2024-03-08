package com.ahcloud.edas.pulsar.core.domain.namespace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 10:31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {

    /**
     * 时间
     */
    @NotNull(message = "时间不能为空")
    private Integer namespaceMessageTTL;

    /**
     * 时间单位
     * 1：秒
     * 2：小时
     * 3：天
     */
    @NotNull(message = "时间单位不能为空")
    private Integer unit;

    /**
     * 单位时间
     */
    private String unitName;

    /**
     * 消息保留策略
     * 0: 消费即删除
     * -1: 持久化保留
     */
    @NotNull(message = "消息保留策略不能为空")
    private Integer retentionPolicies;

    /**
     * 消息保留策略
     * 0: 消费即删除
     * -1: 持久化保留
     */
    private String retentionPoliciesName;

    /**
     * 是否自动创建订阅
     */
    private Boolean autoCreateSubscription;

    public String getUnitName() {
        switch (unit) {
            case 1:
                return "秒";
            case 2:
                return "小时";
            case 3:
                return "天";
            default:
                return "未知";
        }
    }

    public String getRetentionPoliciesName() {
        return retentionPolicies == 0 ? "消费即删除" : "持久化保留";
    }
}
