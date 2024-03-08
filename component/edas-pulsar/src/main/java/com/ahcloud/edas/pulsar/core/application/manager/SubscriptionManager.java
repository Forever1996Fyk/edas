package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.enums.YesOrNoEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.pulsar.core.application.helper.SubscriptionHelper;
import com.ahcloud.edas.pulsar.core.application.helper.TopicHelper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarSubscriptionService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTopicService;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionAddForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionAssignForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionOffsetForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.query.SubscriptionQuery;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.ConsumeProcessVO;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.SubscriptionConsumeVO;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.SubscriptionVO;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PulsarRetCodeEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarSubscription;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopic;
import com.ahcloud.edas.pulsar.core.infrastructure.util.PageUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.common.policies.data.AuthAction;
import org.apache.pulsar.common.policies.data.PartitionedTopicStats;
import org.apache.pulsar.common.policies.data.SubscriptionStats;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 10:55
 **/
@Slf4j
@Component
public class SubscriptionManager {
    @Resource
    private AuthManager authManager;
    @Resource
    private PulsarAdminService pulsarAdminService;
    @Resource
    private PulsarTopicService pulsarTopicService;
    @Resource
    private PulsarSubscriptionService pulsarSubscriptionService;

    /**
     * 新增订阅
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addSubscription(SubscriptionAddForm form) throws PulsarAdminException {
        String role = authManager.getRole(form.getAppId());
        if (StringUtils.isBlank(role)) {
            throw new BizException(PulsarRetCodeEnum.APP_ROLE_NOT_EXISTED);
        }
        PulsarSubscription exitedPulsarSubscription = pulsarSubscriptionService.getOne(
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getTopicId, form.getTopicId())
                        .eq(PulsarSubscription::getSubscriptionName, form.getSubscriptionName())
                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(exitedPulsarSubscription)) {
            throw new BizException(PulsarRetCodeEnum.DATA_EXISTED);
        }
        PulsarTopic pulsarTopic = pulsarTopicService.getById(form.getTopicId());
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        PulsarSubscription pulsarSubscription = SubscriptionHelper.convert(form, pulsarTopic.getTopicFullName());
        pulsarSubscriptionService.save(pulsarSubscription);
        Topics topics = pulsarAdminService.topics();
        topics.createSubscription(pulsarSubscription.getTopicName(), pulsarSubscription.getSubscriptionName(), MessageId.latest);
        // 分配权限
        topics.grantPermission(pulsarTopic.getTopicFullName(), role, Sets.newHashSet(AuthAction.consume));
    }

    /**
     * 更新订阅
     * @param form
     */
    public void updateSubscription(SubscriptionUpdateForm form) {
        PulsarSubscription exitedPulsarSubscription = pulsarSubscriptionService.getOne(
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, form.getId())
                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(exitedPulsarSubscription)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarSubscription pulsarSubscription = new PulsarSubscription();
        pulsarSubscription.setDescription(form.getDescription());
        pulsarSubscription.setVersion(exitedPulsarSubscription.getVersion());
        boolean update = pulsarSubscriptionService.update(
                pulsarSubscription,
                new UpdateWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, form.getId())
                        .eq(PulsarSubscription::getVersion, exitedPulsarSubscription.getVersion())
        );
        if (!update) {
            throw new BizException(PulsarRetCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 删除订阅
     * @param id
     * @param force
     */
    public void deleteSubscription(Long id, boolean force) throws PulsarAdminException {
        PulsarSubscription exitedPulsarSubscription = pulsarSubscriptionService.getOne(
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, id)
                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(exitedPulsarSubscription)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarSubscription deletePulsarSubscription = new PulsarSubscription();
        deletePulsarSubscription.setDeleted(id);
        deletePulsarSubscription.setVersion(exitedPulsarSubscription.getVersion());
        boolean delete = pulsarSubscriptionService.update(
                deletePulsarSubscription,
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, id)
                        .eq(PulsarSubscription::getVersion, exitedPulsarSubscription.getVersion())
        );
        if (!delete) {
            throw new BizException(PulsarRetCodeEnum.VERSION_ERROR);
        }
        Topics topics = pulsarAdminService.topics();
        topics.deleteSubscription(exitedPulsarSubscription.getTopicName(), exitedPulsarSubscription.getSubscriptionName(), force);
    }

    /**
     * 根据id查询订阅
     * @param id
     * @return
     */
    public SubscriptionVO findById(Long id) {
        PulsarSubscription exitedPulsarSubscription = pulsarSubscriptionService.getOne(
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, id)
                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(exitedPulsarSubscription)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        return SubscriptionHelper.convertToVO(exitedPulsarSubscription);
    }

    /**
     * 分页查询订阅列表
     * @param query
     * @return
     */
    public PageResult<SubscriptionVO> pageSubscriptionList(SubscriptionQuery query) throws PulsarAdminException {
        PageInfo<PulsarSubscription> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> pulsarSubscriptionService.list(
                                new QueryWrapper<PulsarSubscription>().lambda()
                                        .eq(PulsarSubscription::getTopicId, query.getTopicId())
                                        .like(
                                                StringUtils.isNotBlank(query.getName()),
                                                PulsarSubscription::getSubscriptionName,
                                                query.getName()
                                        )
                                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
                        )
                );
        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return PageResult.emptyPageResult(query);
        }
        PulsarTopic pulsarTopic = pulsarTopicService.getById(query.getTopicId());
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        Topics topics = pulsarAdminService.topics();
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(pulsarTopic.getPartitionValue());
        TopicStats topicsStats;
        if (yesOrNoEnum.isNo()) {
            topicsStats = topics.getStats(pulsarTopic.getTopicFullName());
        } else {
            topicsStats = topics.getStats(TopicHelper.buildPartitionTopic(pulsarTopic.getTopicFullName(), "0"));
        }
        return PageUtils.pageInfoConvertToPageResult(pageInfo, SubscriptionHelper.convertToVOList(pageInfo.getList(), topicsStats));
    }

    /**
     * 查询订阅消费者实例列表
     * @param subscriptionId
     * @return
     * @throws PulsarAdminException
     */
    public List<SubscriptionConsumeVO> listSubscriptionConsumes(Long subscriptionId) throws PulsarAdminException {
        PulsarSubscription exitedPulsarSubscription = pulsarSubscriptionService.getOne(
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, subscriptionId)
                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(exitedPulsarSubscription)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarTopic pulsarTopic = pulsarTopicService.getById(exitedPulsarSubscription.getTopicId());
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        Topics topics = pulsarAdminService.topics();
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(pulsarTopic.getPartitionValue());
        TopicStats topicsStats;
        if (yesOrNoEnum.isNo()) {
            topicsStats = topics.getStats(pulsarTopic.getTopicFullName());
        } else {
            PartitionedTopicStats partitionedStats = topics.getPartitionedStats(pulsarTopic.getTopicFullName(), true);
            Map<String, ? extends TopicStats> partitions = partitionedStats.getPartitions();
            topicsStats = Lists.newArrayList(partitions.values()).get(0);
        }
        Map<String, ? extends SubscriptionStats> subscriptions = topicsStats.getSubscriptions();
        if (CollectionUtils.isEmpty(subscriptions)) {
            return Collections.emptyList();
        }
        return SubscriptionHelper.convertToConsumeVOList(exitedPulsarSubscription, pulsarTopic, subscriptions);
    }

    /**
     * 消费进度
     * @param subscriptionId
     * @return
     * @throws PulsarAdminException
     */
    public List<ConsumeProcessVO> listConsumeProcess(Long subscriptionId) throws PulsarAdminException {
        PulsarSubscription subscription = pulsarSubscriptionService.getOne(
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, subscriptionId)
                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(subscription)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarTopic pulsarTopic = pulsarTopicService.getById(subscription.getTopicId());
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        TopicStats topicsStats;
        Topics topics = pulsarAdminService.topics();
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(pulsarTopic.getPartitionValue());
        if (yesOrNoEnum.isNo()) {
            topicsStats = topics.getStats(pulsarTopic.getTopicFullName());
            return SubscriptionHelper.convert(pulsarTopic, subscription, topicsStats, false);
        } else {
            topicsStats = topics.getPartitionedStats(pulsarTopic.getTopicFullName(), true);
            return SubscriptionHelper.convert(pulsarTopic, subscription, topicsStats, true);
        }
    }

    /**
     * 重置消费位置
     * @param form
     */
    public void offset(SubscriptionOffsetForm form) throws PulsarAdminException {
        PulsarSubscription exitedPulsarSubscription = pulsarSubscriptionService.getOne(
                new QueryWrapper<PulsarSubscription>().lambda()
                        .eq(PulsarSubscription::getId, form.getSubscriptionId())
                        .eq(PulsarSubscription::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(exitedPulsarSubscription)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarTopic pulsarTopic = pulsarTopicService.getById(exitedPulsarSubscription.getTopicId());
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        Topics topics = pulsarAdminService.topics();
        topics.resetCursor(pulsarTopic.getTopicFullName(), exitedPulsarSubscription.getSubscriptionName(), form.getOffsetTime().getTime());
    }
}
