package com.ahcloud.edas.pulsar.core.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 10:09
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageBaseInfoDTO {

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 消息key
     */
    private String key;

    /**
     * 生产者
     */
    private String producer;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    /**
     * ledgerId
     */
    private Long ledgerId;

    /**
     * entryId
     */
    private Long entryId;

    /**
     * 批量索引
     */
    private Integer batchIndex;

    /**
     * 是否为批量消息
     */
    private Boolean batch;
}
