package com.ahcloud.edas.rocketmq.core.application.manager;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.rocketmq.core.application.checker.RmqMessageChecker;
import com.ahcloud.edas.rocketmq.core.application.helper.RmqMessageHelper;
import com.ahcloud.edas.rocketmq.core.application.service.RmqTopicService;
import com.ahcloud.edas.rocketmq.core.domain.message.query.RmqMessageQuery;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.ConsumeResult;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageDataVO;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageDetailVO;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageTrackVO;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageVO;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.RmqMessageVOPage;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.RmqRetCodeEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqTopic;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.MessagePage;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.MessageView;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.MessageQuery;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.MessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.Pair;
import org.apache.rocketmq.remoting.protocol.body.ConsumeMessageDirectlyResult;
import org.apache.rocketmq.tools.admin.api.MessageTrack;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/12 16:16
 **/
@Slf4j
@Component
public class MessageManager {
    @Resource
    private MessageService messageService;
    @Resource
    private RmqTopicService rmqTopicService;

    /**
     * 分页查询消息列表
     * @param query
     * @return
     */
    public RmqMessageVOPage pageMessageList(RmqMessageQuery query) {
        RmqMessageChecker.checkQueryParam(query);
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, query.getTopicId())
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        MessageQuery messageQuery = RmqMessageHelper.convert(query, rmqTopic.getTopicName());
        MessagePage messagePage = messageService.queryMessageByPage(messageQuery);
        PageResult<MessageVO> pageResult = RmqMessageHelper.convertResult(messagePage, query);
        return new RmqMessageVOPage(messagePage.getTaskId(), pageResult);
    }

    /**
     * 查询消息详情
     * @param topicId
     * @param messageId
     * @return
     */
    public MessageDetailVO findMessageDetailById(Long topicId, String messageId) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, topicId)
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        Pair<MessageView, List<MessageTrack>> pair = messageService.viewMessage(rmqTopic.getTopicName(), messageId);
        MessageDataVO data = RmqMessageHelper.convert(pair.getObject1());
        List<MessageTrackVO> messageTrackVOList = RmqMessageHelper.convertToTrackVOList(pair.getObject2());
        return new MessageDetailVO(data, messageTrackVOList);
    }

    /**
     * 查询消息消费结果
     * @param query
     * @return
     */
    public ConsumeResult consumeMessageDirectly(RmqMessageQuery query) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, query.getTopicId())
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        ConsumeMessageDirectlyResult result = messageService.consumeMessageDirectly(rmqTopic.getTopicName(), query.getMessageId(), query.getConsumeGroup(), query.getClientId());
        return RmqMessageHelper.convert(result);
    }
}
