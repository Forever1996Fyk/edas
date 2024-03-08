package com.ahcloud.edas.pulsar.core.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 10:35
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageProduceDTO {

    /**
     * 生产地址
     */
    private String address;

    /**
     * 生产时间
     */
    private Date produceTime;

    /**
     * 生产状态
     */
    private Integer produceStatus;
}
