package com.ahcloud.edas.pulsar.core.domain.topic.vo;

import com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 16:04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicVO {

    /**
     * id
     */
    private Long id;

    /**
     * appId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long appId;

    /**
     * app编码
     */
    private String appCode;

    /**
     * env
     */
    private String env;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * topic名称
     */
    private String topicName;

    /**
     * topic完整名称
     */
    private String topicFullName;

    /**
     * 是否持久化
     */
    private Integer persistent;

    /**
     * 是否分区
     */
    private Integer partitionValue;

    /**
     * 分区数
     */
    private Integer partitionNum;

    /**
     * 策略
     */
    private PolicyDTO policyDTO;

    /**
     * 说明
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
}
