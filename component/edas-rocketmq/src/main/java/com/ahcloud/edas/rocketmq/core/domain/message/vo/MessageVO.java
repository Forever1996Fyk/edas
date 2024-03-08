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
 * @create: 2024/1/12 16:59
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {

    /**
     * 消息id
     */
    private String messageId;

    /**
     * tag
     */
    private String tag;

    /**
     * 消息key
     */
    private String messageKey;

    /**
     * 存储时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date storeTime;
}
