package com.ahcloud.edas.rocketmq.core.application.helper;

import com.ahcloud.edas.common.domain.common.PageQuery;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.rocketmq.core.domain.message.dto.MessageProperties;
import com.ahcloud.edas.rocketmq.core.domain.message.query.RmqMessageQuery;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.ConsumeResult;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageDataVO;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageTrackVO;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageVO;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.CMResultEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.MessageTrackTypeEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.MessagePage;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.MessageView;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.MessageQuery;
import org.apache.rocketmq.remoting.protocol.body.ConsumeMessageDirectlyResult;
import org.apache.rocketmq.tools.admin.api.MessageTrack;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 11:20
 **/
public class RmqMessageHelper {

    /**
     * 数据转换
     * @param query
     * @param topic
     * @return
     */
    public static MessageQuery convert(RmqMessageQuery query, String topic) {
        MessageQuery messageQuery = new MessageQuery();
        // 时间查询需要获取 unix时间戳
        messageQuery.setBegin(query.getBeginInstant().getEpochSecond() * 1000);
        messageQuery.setEnd(query.getEndInstant().getEpochSecond() * 1000);
        messageQuery.setTopic(topic);
        messageQuery.setTaskId(query.getTaskId());
        messageQuery.setPageNum(query.getPageNo());
        messageQuery.setPageSize(query.getPageSize());
        return messageQuery;
    }

    /**
     * 数据转换
     * @param messagePage
     * @param query
     * @return
     */
    public static PageResult<MessageVO> convertResult(MessagePage messagePage, PageQuery query) {
        PageResult<MessageVO> pageResult = new PageResult<>();
        Page<MessageView> page = messagePage.getPage();
        pageResult.setTotal(page.getTotalElements());
        pageResult.setPageNum(query.getPageNo());
        pageResult.setPageSize(query.getPageSize());
        pageResult.setPages(page.getTotalPages());
        pageResult.setRows(convertToVOList(page.getContent()));
        return pageResult;
    }

    /**
     * 数据转换
     * @param messageView
     * @return
     */
    private static MessageVO convertToVO(MessageView messageView) {
        Map<String, String> properties = messageView.getProperties();
        MessageProperties messageProperties = extractProperties(properties);
        return MessageVO.builder()
                .messageId(messageView.getMsgId())
                .messageKey(messageProperties.getKey())
                .tag(messageProperties.getTag())
                .storeTime(new Date(messageView.getStoreTimestamp()))
                .build();
    }

    /**
     * 数据转换
     * @param messageViewList
     * @return
     */
    private static List<MessageVO> convertToVOList(List<MessageView> messageViewList) {
        return messageViewList.stream()
                .map(RmqMessageHelper::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 数据转换
     * @param messageView
     * @return
     */
    public static MessageDataVO convert(MessageView messageView) {
        Map<String, String> properties = messageView.getProperties();
        MessageProperties messageProperties = extractProperties(properties);
        return MessageDataVO.builder()
                .messageId(messageView.getMsgId())
                .messageBody(messageView.getMessageBody())
                .key(messageProperties.getKey())
                .tag(messageProperties.getTag())
                .storeTime(new Date(messageView.getStoreTimestamp()))
                .build();
    }

    /**
     * 数据转换
     * @param messageTrack
     * @return
     */
    public static MessageTrackVO convert(MessageTrack messageTrack) {
        MessageTrackTypeEnum messageTrackTypeEnum = MessageTrackTypeEnum.getByType(messageTrack.getTrackType());
        return MessageTrackVO.builder()
                .consumerGroup(messageTrack.getConsumerGroup())
                .exceptionDesc(messageTrack.getExceptionDesc())
                .trackType(messageTrackTypeEnum.getType())
                .trackTypeName(messageTrackTypeEnum.getDesc())
                .build();
    }

    /**
     * 数据转换
     * @param messageTracks
     * @return
     */
    public static List<MessageTrackVO> convertToTrackVOList(List<MessageTrack> messageTracks) {
        return CollectionUtils.convertList(messageTracks, RmqMessageHelper::convert);
    }

    /**
     * 数据转换
     * @param result
     * @return
     */
    public static ConsumeResult convert(ConsumeMessageDirectlyResult result) {
        CMResultEnum resultEnum = CMResultEnum.getByResult(result.getConsumeResult());
        return ConsumeResult.builder()
                .autoCommit(result.isAutoCommit())
                .order(result.isOrder())
                .spentTimeMills(result.getSpentTimeMills())
                .remark(result.getRemark())
                .consumeResult(resultEnum.getType())
                .consumeResultName(resultEnum.getDesc())
                .build();
    }

    /**
     * 提取属性
     * @param properties
     * @return
     */
    private static MessageProperties extractProperties(Map<String, String> properties) {
        String tags = properties.get("TAGS");
        String keys = properties.get("KEYS");
        return new MessageProperties(tags, keys);
    }

}
