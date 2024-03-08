package com.ahcloud.edas.pulsar.core.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 10:44
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageConsumeDTO {

    /**
     * 订阅名称
     */
    private String subscriptionName;

    /**
     * 订阅模式
     */
    private Integer subscriptionMode;

    /**
     * 推送次数
     */
    private Integer pushCount;

    /**
     * 最后推送时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date lastPushTime;

    /**
     * 消费状态
     */
    private Integer consumeStatus;

    /**
     * 消费者列表
     */
    private List<ConsumeInfo> consumeInfoList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsumeInfo {

        /**
         * 消费者名称
         */
        private String consumeName;

        /**
         * 消费地址
         */
        private String address;

        /**
         * 最后推送时间
         */
        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
        private Date lastPushTime;

        /**
         * 消费状态
         */
        private Integer consumeStatus;
    }
}
