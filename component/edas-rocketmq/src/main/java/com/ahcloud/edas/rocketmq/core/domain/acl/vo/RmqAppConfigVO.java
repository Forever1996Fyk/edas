package com.ahcloud.edas.rocketmq.core.domain.acl.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/2 11:26
 **/
@Data
public class RmqAppConfigVO {

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
     * env
     */
    private String env;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;
}
