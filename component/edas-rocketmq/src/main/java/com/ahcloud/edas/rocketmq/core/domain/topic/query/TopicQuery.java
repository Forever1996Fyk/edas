package com.ahcloud.edas.rocketmq.core.domain.topic.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 11:31
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class TopicQuery extends PageQuery {

    /**
     * topic名称
     */
    private String topicName;

    /**
     * 环境
     */
    private String env;

    /**
     * appCode
     */
    private String appCode;
}
