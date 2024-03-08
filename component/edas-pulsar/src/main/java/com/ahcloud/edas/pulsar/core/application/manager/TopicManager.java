package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.enums.YesOrNoEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.pulsar.core.application.checker.TopicChecker;
import com.ahcloud.edas.pulsar.core.application.helper.PoliciesHelper;
import com.ahcloud.edas.pulsar.core.application.helper.TopicHelper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarNamespaceService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTenantService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTopicService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTopicStatsService;
import com.ahcloud.edas.pulsar.core.domain.common.StatsDTO;
import com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionAssignForm;
import com.ahcloud.edas.pulsar.core.domain.topic.form.TopicAddForm;
import com.ahcloud.edas.pulsar.core.domain.topic.form.TopicUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.topic.query.TopicQuery;
import com.ahcloud.edas.pulsar.core.domain.topic.vo.TopicDetailVO;
import com.ahcloud.edas.pulsar.core.domain.topic.vo.TopicProduceVO;
import com.ahcloud.edas.pulsar.core.domain.topic.vo.TopicVO;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.TopicConstants;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PulsarRetCodeEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.stats.PulsarStatsService;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarNamespace;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTenant;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopic;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopicStats;
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
import org.apache.pulsar.client.admin.TopicPolicies;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.common.policies.data.AuthAction;
import org.apache.pulsar.common.policies.data.NonPersistentPublisherStats;
import org.apache.pulsar.common.policies.data.NonPersistentTopicStats;
import org.apache.pulsar.common.policies.data.PartitionedTopicStats;
import org.apache.pulsar.common.policies.data.PublisherStats;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 16:27
 **/
@Slf4j
@Component
public class TopicManager {
    @Resource
    private AuthManager authManager;
    @Resource
    private PulsarStatsService pulsarStatsService;
    @Resource
    private PulsarAdminService pulsarAdminService;
    @Resource
    private PulsarTopicService pulsarTopicService;
    @Resource
    private PulsarTenantService pulsarTenantService;
    @Resource
    private PulsarNamespaceService pulsarNamespaceService;

    private final static Integer DEFAULT_QUEUE_SIZE = Integer.MAX_VALUE;

    private final static ThreadPoolExecutor GRANT_PERMISSION_POOL = new ThreadPoolExecutor(
            50,
            50,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "GRANT_PERMISSION_POOL-" + r.hashCode()));

    private final static ThreadPoolExecutor SET_TOPIC_POLICY_POOL = new ThreadPoolExecutor(
            50,
            50,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "SET_TOPIC_POLICY_POOL-" + r.hashCode()));

    /**
     * 新增topic
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addTopic(TopicAddForm form) throws PulsarAdminException {
        PulsarTenant pulsarTenant = pulsarTenantService.getById(form.getTenantId());
        if (Objects.isNull(pulsarTenant)) {
            throw new BizException(PulsarRetCodeEnum.TENANT_NOT_EXISTED);
        }
        // 这里直接把环境变量作为命名空间有点写死的意思，但是目前为了防止业务开发者随意选择命名空间，这里只要暂时写死
        PulsarNamespace pulsarNamespace = pulsarNamespaceService.getOne(
                new QueryWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getTenantId, form.getTenantId())
                        .eq(PulsarNamespace::getNamespaceName, form.getEnv())
                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(pulsarNamespace)) {
            throw new BizException(PulsarRetCodeEnum.NAMESPACE_NOT_EXISTED);
        }
        PulsarTopic existedPulsarTopic = pulsarTopicService.getOne(
                new QueryWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getTenantId, form.getTenantId())
                        .eq(PulsarTopic::getNamespaceId, pulsarNamespace.getId())
                        .eq(PulsarTopic::getTopicName, form.getTopicName())
                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedPulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.DATA_EXISTED);
        }
        PulsarTopic pulsarTopic = TopicHelper.convert(form, pulsarTenant.getTenantName(), pulsarNamespace.getNamespaceName());
        pulsarTopic.setNamespaceId(pulsarNamespace.getId());
        pulsarTopicService.save(pulsarTopic);
        Topics topics = pulsarAdminService.topics();
        YesOrNoEnum partition = YesOrNoEnum.getByType(form.getPartitionValue());
        String topicFullName = pulsarTopic.getTopicFullName();
        if (partition.isYes()) {
            TopicChecker.checkPartitionNum(form.getPartitionNum());
            topics.createPartitionedTopic(topicFullName, form.getPartitionNum());
        } else {
            topics.createNonPartitionedTopic(topicFullName);
        }

        // 设置topic策略
        this.asyncSetTopicPolicy(topicFullName, form.getPolicyDTO());

        // 分配权限
        this.asyncGrantPermission(topicFullName, form.getAppCode());
    }

    /**
     * 异步设置topic策略
     *
     * @param topicFullName
     * @param policyDTO
     */
    public void asyncSetTopicPolicy(String topicFullName, PolicyDTO policyDTO) {
        // 设置消息ttl
        TopicPolicies topicPolicies = pulsarAdminService.topicPolicies(false);
        SET_TOPIC_POLICY_POOL.execute(() -> {
            int errorCount = 0;
            boolean endFlag = true;
            while (endFlag || errorCount == 5) {
                try {
                    topicPolicies.setMessageTTL(topicFullName, PoliciesHelper.parseMessageTtlSeconds(policyDTO));
                    topicPolicies.setRetention(topicFullName, PoliciesHelper.buildRetention(policyDTO));
                    endFlag = false;
                } catch (PulsarAdminException e) {
                    // 最大错误数为5次
                    errorCount++;
                }
            }
        });
    }

    /**
     * 异步分配权限
     *
     * @param topicFullName
     * @param appCode
     */
    public void asyncGrantPermission(String topicFullName, String appCode) {
        Topics topics = pulsarAdminService.topics();
        GRANT_PERMISSION_POOL.execute(() -> {
            int errorCount = 0;
            boolean endFlag = true;
            while (endFlag || errorCount == 5) {
                try {
                    topics.grantPermission(topicFullName, appCode, TopicConstants.PRODUCE_CONSUME_AUTH_ACTIONS);
                    endFlag = false;
                } catch (PulsarAdminException e) {
                    // 最大错误数为5次
                    errorCount++;
                }
            }
        });
    }

    /**
     * 更新topic
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTopic(TopicUpdateForm form) throws PulsarAdminException {
        PulsarTopic existedPulsarTopic = pulsarTopicService.getOne(
                new QueryWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getId, form.getId())
                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.DATA_EXISTED);
        }
        PulsarTopic pulsarTopic = TopicHelper.convert(form);
        pulsarTopic.setVersion(existedPulsarTopic.getVersion());
        boolean update = pulsarTopicService.update(
                pulsarTopic,
                new UpdateWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getId, form.getId())
                        .eq(PulsarTopic::getVersion, existedPulsarTopic.getVersion())
        );
        if (!update) {
            throw new BizException(PulsarRetCodeEnum.VERSION_ERROR);
        }
        Topics topics = pulsarAdminService.topics();
        YesOrNoEnum partition = YesOrNoEnum.getByType(existedPulsarTopic.getPartitionValue());
        String topicFullName = existedPulsarTopic.getTopicFullName();
        // 当前topic是分区，且分区数大于原来分区数时，触发更新
        if (partition.isYes()) {
            TopicChecker.checkPartitionNum(form.getPartitionNum(), existedPulsarTopic.getPartitionNum());
            topics.updatePartitionedTopic(topicFullName, form.getPartitionNum());
        }
        PolicyDTO policyDTO = JsonUtils.stringToBean(existedPulsarTopic.getPoliciesJson(), PolicyDTO.class);
        if (Objects.isNull(policyDTO)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        Integer newSeconds = PoliciesHelper.parseMessageTtlSeconds(form.getPolicyDTO());
        Integer oldSeconds = PoliciesHelper.parseMessageTtlSeconds(policyDTO);
        if (!Objects.equals(newSeconds, oldSeconds)) {
            // 设置消息ttl
            TopicPolicies topicPolicies = pulsarAdminService.topicPolicies(false);
            topicPolicies.setMessageTTL(topicFullName, PoliciesHelper.parseMessageTtlSeconds(form.getPolicyDTO()));
        }
    }

    /**
     * 删除topic
     *
     * @param id
     * @param force
     * @throws PulsarAdminException
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTopic(Long id, boolean force) throws PulsarAdminException {
        PulsarTopic existedPulsarTopic = pulsarTopicService.getOne(
                new QueryWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getId, id)
                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.DATA_EXISTED);
        }
        PulsarTopic deletePulsarTopic = new PulsarTopic();
        deletePulsarTopic.setDeleted(id);
        deletePulsarTopic.setVersion(existedPulsarTopic.getVersion());
        boolean delete = pulsarTopicService.update(
                deletePulsarTopic,
                new UpdateWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getId, id)
                        .eq(PulsarTopic::getVersion, existedPulsarTopic.getVersion())
        );
        if (!delete) {
            throw new BizException(PulsarRetCodeEnum.VERSION_ERROR);
        }
        Topics topics = pulsarAdminService.topics();
        topics.delete(existedPulsarTopic.getTopicFullName(), force);
    }

    /**
     * 获取topic详情
     *
     * @param id
     * @return
     */
    public TopicVO findTopicById(Long id) {
        PulsarTopic existedPulsarTopic = pulsarTopicService.getOne(
                new QueryWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getId, id)
                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.DATA_EXISTED);
        }
        return TopicHelper.convertToVO(existedPulsarTopic);
    }

    /**
     * 分页查询topic
     *
     * @param query
     * @return
     */
    public PageResult<TopicVO> pageTopicList(TopicQuery query) {
        PageInfo<PulsarTopic> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> pulsarTopicService.list(
                                new QueryWrapper<PulsarTopic>().lambda()
                                        .like(
                                                StringUtils.isNotBlank(query.getTopicName()),
                                                PulsarTopic::getTopicName,
                                                query.getTopicName()
                                        )
                                        .eq(PulsarTopic::getEnv, query.getEnv())
                                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return PageUtils.pageInfoConvertToPageResult(pageInfo, TopicHelper.convertToVOList(pageInfo.getList()));
    }

    /**
     * 查询topic详情
     *
     * @param topicId
     * @return
     * @throws PulsarAdminException
     */
    public TopicDetailVO findTopicDetail(Long topicId) throws PulsarAdminException {
        PulsarTopic pulsarTopic = pulsarTopicService.getById(topicId);
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        Topics topics = pulsarAdminService.topics();
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(pulsarTopic.getPartitionValue());
        StatsDTO statsDTO;
        TopicStats topicsStats;
        TopicDetailVO.TopicDetailVOBuilder builder = TopicDetailVO.builder()
                .topicId(pulsarTopic.getId())
                .env(pulsarTopic.getEnv())
                .persistent(pulsarTopic.getPersistent())
                .partitions(pulsarTopic.getPartitionNum());
        if (yesOrNoEnum.isNo()) {
            topicsStats = topics.getStats(pulsarTopic.getTopicFullName());
            statsDTO = TopicHelper.convert(topicsStats);
            builder.producers(topicsStats.getPublishers().size());
            builder.subscriptions(topicsStats.getSubscriptions().size());
        } else {
            topicsStats = topics.getPartitionedStats(pulsarTopic.getTopicFullName(), true);
            statsDTO = TopicHelper.convert(topicsStats);
        }
        return builder
                .statsDTO(statsDTO)
                .subscriptions(topicsStats.getSubscriptions().size())
                .producers(topicsStats.getPublishers().size())
                .build();
    }


    /**
     * 分配订阅权限
     *
     * @param form
     * @throws PulsarAdminException
     */
    public void assignSubscriptionPermission(SubscriptionAssignForm form) throws PulsarAdminException {
        String role = authManager.getRole(form.getAppId());
        if (StringUtils.isBlank(role)) {
            throw new BizException(PulsarRetCodeEnum.ROLE_NOT_EXISTED);
        }
        PulsarTopic pulsarTopic = pulsarTopicService.getOne(
                new QueryWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getId, form.getTopicId())
                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        Topics topics = pulsarAdminService.topics();
        topics.grantPermission(pulsarTopic.getTopicFullName(), role, Sets.newHashSet(AuthAction.consume));
    }

    /**
     * 查询主题生产者列表
     *
     * @param topicId
     * @return
     */
    public List<TopicProduceVO> listTopicProduces(Long topicId) throws PulsarAdminException {
        PulsarTopic pulsarTopic = pulsarTopicService.getOne(
                new QueryWrapper<PulsarTopic>().lambda()
                        .eq(PulsarTopic::getId, topicId)
                        .eq(PulsarTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(pulsarTopic)) {
            throw new BizException(PulsarRetCodeEnum.TOPIC_NOT_EXISTED);
        }
        Topics topics = pulsarAdminService.topics();
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(pulsarTopic.getPartitionValue());
        List<? extends PublisherStats> publishers;
        if (yesOrNoEnum.isNo()) {
            TopicStats topicsStats = topics.getStats(pulsarTopic.getTopicFullName());
            publishers = topicsStats.getPublishers();
        } else {
            PartitionedTopicStats partitionedStats = topics.getPartitionedStats(pulsarTopic.getTopicFullName(), true);
            Map<String, ? extends TopicStats> partitions = partitionedStats.getPartitions();
            publishers = Lists.newArrayList(partitions.values()).get(0).getPublishers();
        }
        if (CollectionUtils.isEmpty(publishers)) {
            return Collections.emptyList();
        }
        return TopicHelper.convertToProduceList(pulsarTopic, publishers);
    }
}
