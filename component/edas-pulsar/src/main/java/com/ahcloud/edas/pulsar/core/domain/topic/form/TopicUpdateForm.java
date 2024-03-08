package com.ahcloud.edas.pulsar.core.domain.topic.form;

import com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 15:48
 **/
@Data
public class TopicUpdateForm {

    /**
     * id
     */
    @NotNull(message = "参数错误")
    private Long id;

    /**
     * 是否分区
     */
    @NotNull(message = "请选择是否分区")
    private Integer partitionValue;

    /**
     * 分区数
     */
    private Integer partitionNum;

    /**
     * 说明
     */
    private String description;

    /**
     * 策略
     */
    @Valid
    @NotNull(message = "策略不能为空")
    private PolicyDTO policyDTO;
}
