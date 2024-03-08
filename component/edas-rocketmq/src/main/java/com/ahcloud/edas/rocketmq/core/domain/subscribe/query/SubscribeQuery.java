package com.ahcloud.edas.rocketmq.core.domain.subscribe.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/5 15:46
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SubscribeQuery extends PageQuery {

    /**
     * topicId
     */
    private Long topicId;

    /**
     * appCode查询
     */
    private String appCode;
}
