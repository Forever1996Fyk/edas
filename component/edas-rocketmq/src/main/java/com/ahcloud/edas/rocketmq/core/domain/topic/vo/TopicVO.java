package com.ahcloud.edas.rocketmq.core.domain.topic.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 11:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicVO {

    /**
     * topic id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * appId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long appId;

    /**
     * appCode
     */
    private String appCode;

    /**
     * 环境变量
     */
    private String env;

    /**
     * topic名称
     */
    private String topicName;

    /**
     * brokers
     */
    private List<String> brokerNameList;

    /**
     * 写队列数
     */
    private Integer writeQueueNum;

    /**
     * 读队列数
     */
    private Integer readQueueNum;

    /**
     * topic权限
     */
    private Integer perm;

    /**
     * topic权限
     */
    private String permDesc;
}
