package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.YesOrNoEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.pulsar.core.application.helper.MessageHelper;
import com.ahcloud.edas.pulsar.core.application.helper.TopicHelper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarSubscriptionService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTopicService;
import com.ahcloud.edas.pulsar.core.domain.message.query.MessageQuery;
import com.ahcloud.edas.pulsar.core.domain.message.vo.MessageDetailVO;
import com.ahcloud.edas.pulsar.core.domain.message.vo.MessageTrackVO;
import com.ahcloud.edas.pulsar.core.domain.message.vo.MessageVO;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PulsarRetCodeEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarSubscription;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.client.api.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/25 16:38
 **/
@Slf4j
@Component
public class MessageManager {
    @Resource
    private PulsarAdminService pulsarAdminService;
    @Resource
    private PulsarTopicService pulsarTopicService;
    @Resource
    private PulsarSubscriptionService pulsarSubscriptionService;

    private final static int DEFAULT_PEEK_NUM = 10000;

    /**
     * 分页查询消息
     * 这里是逻辑分页，默认查询最新10000条消息
     *
     * 注意：这里目前只能查询堆积消息，如果需要查询持久化的消息，参考方法
     * @see MessageManager#pageMessageList(MessageQuery)
     * @param query
     * @return
     * @throws PulsarAdminException
     */
    public PageResult<MessageVO> peekMessageList(MessageQuery query) throws PulsarAdminException {
        PulsarTopic pulsarTopic = pulsarTopicService.getById(query.getTopicId());
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        PulsarSubscription pulsarSubscription = pulsarSubscriptionService.getById(query.getSubscriptionId());
        if (Objects.isNull(pulsarSubscription)) {
            throw new BizException(PulsarRetCodeEnum.SUBSCRIPTION_NOT_EXISTED);
        }
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(pulsarTopic.getPartitionValue());
        String topicFullName = pulsarTopic.getTopicFullName();
        if (yesOrNoEnum.isYes()) {
            // 如果是分区topic，必须要传入具体的分区id
            topicFullName = TopicHelper.buildPartitionTopic(topicFullName, query.getPartitionId());
        }
        if (StringUtils.isBlank(topicFullName)) {
            throw new BizException(PulsarRetCodeEnum.PARAM_MISS, "topicName");
        }
        Topics topics = pulsarAdminService.topics();
        List<Message<byte[]>> messages = topics.peekMessages(topicFullName, pulsarSubscription.getSubscriptionName(), 1);
        int size = messages.size();
        PageResult<MessageVO> pageResult = new PageResult<>();
        pageResult.setTotal(size);
        pageResult.setPageNum(query.getPageNo());
        pageResult.setPageSize(query.getPageSize());
        int startIndex = (query.getPageNo() - 1) * query.getPageSize();
        if (size < startIndex) {
            pageResult.setRows(MessageHelper.convertToVOList(messages));
        } else {
            int allTotal = query.getPageNo() * query.getPageSize();
            int curPartSize = (size < allTotal) ? (size - startIndex) : query.getPageSize();
            int endIndex = startIndex + curPartSize;
            pageResult.setRows(MessageHelper.convertToVOList(messages.subList(startIndex, endIndex)));
        }
        return pageResult;
    }

    /**
     * Pulsar的存储系统使用的是BookKeeper，也就是Bookie，需要单独部署
     * 需要通过Pulsar SQL查询 官方文档 <a href="https://pulsar.apache.org/docs/2.11.x/sql-overview/">...</a>
     * Pulsar SQL通过JDBC代码区查询 <a href="https://github.com/trinodb/trino/blob/master/plugin/trino-example-jdbc/pom.xml">...</a>
     * @param query
     * @return
     */
    public  PageResult<MessageVO> pageMessageList(MessageQuery query) {
        return null;
    }

    /**
     * 查询消息详情
     * @param query
     * @return
     * @throws PulsarAdminException
     */
    public MessageDetailVO findMessageDetail(MessageQuery query) throws PulsarAdminException {
        PulsarTopic pulsarTopic = pulsarTopicService.getById(query.getTopicId());
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(pulsarTopic.getPartitionValue());
        Message<byte[]> message = null;
        Topics topics = pulsarAdminService.topics();
        if (yesOrNoEnum.isNo()) {
            message = topics.getMessageById(pulsarTopic.getTopicFullName(), query.getLedgerId(), query.getEntryId());
        } else {
            message = topics.getMessageById(TopicHelper.buildPartitionTopic(pulsarTopic.getTopicFullName(), query.getPartitionId()), query.getLedgerId(), query.getEntryId());
        }
        return MessageHelper.convertToDetail(message);
    }

    /**
     * 暂不需要查询消息轨迹，因为会配置策略，消息消费即删除
     * 如果能查到说明消息肯定未消费
     *
     * @param query
     * @return
     */
    @Deprecated
    public MessageTrackVO findMessageTrack(MessageQuery query) {
        return null;
    }
}
