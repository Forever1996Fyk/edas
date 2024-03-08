package com.ahcloud.edas.rocketmq.core.domain.message.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 16:31
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDataVO {

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 消息内容
     */
    private String messageBody;

    /**
     * 消息key
     */
    private String key;

    /**
     * 消息标签
     */
    private String tag;

    /**
     * 存储时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date storeTime;
}
