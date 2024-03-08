package com.ahcloud.edas.pulsar.core.domain.message.vo;

import com.ahcloud.edas.pulsar.core.domain.message.dto.MessageBaseInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 09:42
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageDetailVO {

    /**
     * 基本信息
     */
    private MessageBaseInfoDTO baseInfo;

    /**
     * 消息体
     */
    private String messageBody;

    /**
     * 详情参数
     */
    private Map<String, String> properties;
}
