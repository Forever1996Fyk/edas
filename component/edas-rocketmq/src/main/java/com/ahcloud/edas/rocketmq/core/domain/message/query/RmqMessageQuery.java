package com.ahcloud.edas.rocketmq.core.domain.message.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import com.ahcloud.edas.common.util.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/12 16:41
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RmqMessageQuery extends PageQuery {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * topicId
     */
    private Long topicId;

    /**
     * message key
     */
    private String key;

    /**
     * messageId
     */
    private String messageId;

    /**
     * 标签
     */
    private String tag;

    /**
     * 消费组
     */
    private String consumeGroup;

    /**
     * clientId
     */
    private String clientId;
    public Instant getBeginInstant() {
        return StringUtils.isNotBlank(beginTime) ? DateUtils.str2Instant(beginTime, DateUtils.DatePattern.PATTERN8) : Instant.now().minus(3, ChronoUnit.HOURS);
    }

    public Instant getEndInstant() {
        return StringUtils.isNotBlank(endTime) ? DateUtils.str2Instant(endTime, DateUtils.DatePattern.PATTERN8) : Instant.now();
    }

    public static void main(String[] args) {
    }
}
