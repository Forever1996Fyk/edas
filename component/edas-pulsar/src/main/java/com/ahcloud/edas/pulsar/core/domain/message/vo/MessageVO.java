package com.ahcloud.edas.pulsar.core.domain.message.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/25 16:42
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {

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
     * 创建时间
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
