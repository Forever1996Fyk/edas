package com.ahcloud.edas.pulsar.core.domain.message.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 09:13
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageQuery extends PageQuery {

    /**
     * topicId
     */
    private Long topicId;

    /**
     * 分区id
     */
    private String partitionId;

    /**
     * subscriptionId
     */
    private Long subscriptionId;

    /**
     * 消息id
     */
    private String messageId;

    /**
     * ledgerId
     */
    private Long ledgerId;

    /**
     * entryId
     */
    private Long entryId;
}
