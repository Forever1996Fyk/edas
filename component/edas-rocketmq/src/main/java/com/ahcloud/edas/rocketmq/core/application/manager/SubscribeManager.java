package com.ahcloud.edas.rocketmq.core.application.manager;

import cn.hutool.core.convert.Convert;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.rocketmq.core.application.helper.RmqAclHelper;
import com.ahcloud.edas.rocketmq.core.application.helper.RmqConfigHelper;
import com.ahcloud.edas.rocketmq.core.application.helper.RmqSubscribeHelper;
import com.ahcloud.edas.rocketmq.core.application.service.RmqAppConfigService;
import com.ahcloud.edas.rocketmq.core.application.service.RmqSubscribeAclService;
import com.ahcloud.edas.rocketmq.core.application.service.RmqTopicService;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.ResetOffsetForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscribeAddForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscribeDeleteForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscribeUpdateForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscriptionGroupConfigDetail;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.query.SubscribeQuery;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.vo.SubscribeGroupConsumeDetailVO;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.vo.SubscribeVO;
import com.ahcloud.edas.rocketmq.core.infrastructure.config.RMQProperties;
import com.ahcloud.edas.rocketmq.core.infrastructure.config.RocketMqConfiguration;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.AclPermEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.RmqRetCodeEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqAppConfig;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqSubscribeAcl;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqTopic;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.ConsumerGroupRollBackStat;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.GroupConsumeInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.AclRequest;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.ConsumerConfigInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.DeleteSubGroupRequest;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.ResetOffsetRequest;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.AclService;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.ConsumerService;
import com.ahcloud.edas.rocketmq.core.infrastructure.util.PageUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.AclConfig;
import org.apache.rocketmq.common.PlainAccessConfig;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 11:26
 **/
@Slf4j
@Component
public class SubscribeManager {
    @Resource
    private AclService aclService;
    @Resource
    private RMQProperties rmqProperties;
    @Resource
    private ConsumerService consumerService;
    @Resource
    private RmqTopicService rmqTopicService;
    @Resource
    private RmqAppConfigService rmqAppConfigService;
    @Resource
    private RmqSubscribeAclService rmqSubscribeAclService;

    /**
     * 新增订阅和权限
     *
     * 此操作的流程为：
     *      A应用需要订阅B应用的topic，
     *      那么，
     *      1. A应用的负责人需要提供其负责人的UID给B应用的负责人，
     *      2. 然后B应用的负责人，根据其用户id查询对应的应用列表，选择需要订阅的应用
     *      3. 保存即可
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addSubscribe(SubscribeAddForm form) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, form.getTopicId())
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        // 选择的应用配置
        RmqAppConfig rmqAppConfig = rmqAppConfigService.getOne(
                new QueryWrapper<RmqAppConfig>().lambda()
                        .eq(RmqAppConfig::getAppId, form.getAppId())
                        .eq(RmqAppConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqAppConfig)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        String newGroupName = RmqSubscribeHelper.rebuildGroupName(form.getGroupConfigDetail().getGroupName(), form.getAppCode(), form.getEnv());
        RmqSubscribeAcl rmqSubscribeAcl = RmqSubscribeHelper.convert(form, AclPermEnum.SUB, AclPermEnum.SUB);
        rmqSubscribeAcl.setGroupName(newGroupName);
        rmqSubscribeAclService.save(rmqSubscribeAcl);

        form.getGroupConfigDetail().setGroupName(newGroupName);
        // 新增订阅组
        ConsumerConfigInfo consumerConfigInfo = RmqSubscribeHelper.convert(rmqProperties.getClusterNameList(), form.getBrokerNameList(), form.getGroupConfigDetail());
        consumerService.createAndUpdateSubscriptionGroupConfig(consumerConfigInfo);
        //需要手动创建重试队列 topic

        // rmq topic配置 权限，即允许有订阅权限
        aclService.addOrUpdateAclTopicConfig(RmqAclHelper.buildTopicAclConfig(rmqAppConfig.getAccessKey(), rmqTopic.getTopicName(), AclPermEnum.SUB));
    }

    /**
     * 更新订阅
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateSubscribe(SubscribeUpdateForm form) {
        RmqSubscribeAcl existedAcl = rmqSubscribeAclService.getOne(
                new QueryWrapper<RmqSubscribeAcl>().lambda()
                        .eq(RmqSubscribeAcl::getId, form.getId())
                        .eq(RmqSubscribeAcl::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedAcl)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, existedAcl.getTopicId())
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        // 选择的应用配置
        RmqAppConfig rmqAppConfig = rmqAppConfigService.getOne(
                new QueryWrapper<RmqAppConfig>().lambda()
                        .eq(RmqAppConfig::getAppId, existedAcl.getAppId())
                        .eq(RmqAppConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqAppConfig)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        RmqSubscribeAcl rmqSubscribeAcl = RmqSubscribeHelper.convert(form, AclPermEnum.SUB, AclPermEnum.ANY);
        rmqSubscribeAcl.setBrokerNames(StringUtils.join(form.getBrokerNameList(), ","));
        rmqSubscribeAcl.setVersion(existedAcl.getVersion());
        boolean updateResult = rmqSubscribeAclService.update(
                rmqSubscribeAcl,
                new UpdateWrapper<RmqSubscribeAcl>().lambda()
                        .eq(RmqSubscribeAcl::getId, form.getId())
                        .eq(RmqSubscribeAcl::getVersion, existedAcl.getVersion())
        );
        if (!updateResult) {
            throw new BizException(RmqRetCodeEnum.VERSION_ERROR);
        }
        // 更新订阅组
        form.getGroupConfigDetail().setGroupName(existedAcl.getGroupName());
        ConsumerConfigInfo consumerConfigInfo = RmqSubscribeHelper.convert(rmqProperties.getClusterNameList(), form.getBrokerNameList(), form.getGroupConfigDetail());
        consumerService.createAndUpdateSubscriptionGroupConfig(consumerConfigInfo);
    }

    /**
     * 根据id查询订阅详情
     * @param id
     * @return
     */
    public SubscribeVO findSubscribeById(Long id) {
        RmqSubscribeAcl existedAcl = rmqSubscribeAclService.getOne(
                new QueryWrapper<RmqSubscribeAcl>().lambda()
                        .eq(RmqSubscribeAcl::getId, id)
                        .eq(RmqSubscribeAcl::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedAcl)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        ConsumerConfigInfo consumerConfigInfo = consumerService.examineSubscriptionGroupConfig(existedAcl.getGroupName());
        SubscriptionGroupConfigDetail groupConfigDetail = RmqSubscribeHelper.convert(consumerConfigInfo);
        SubscribeVO subscribeVO = Convert.convert(SubscribeVO.class, existedAcl);
        subscribeVO.setBrokerNameList(consumerConfigInfo.getBrokerNameList());
        subscribeVO.setGroupConfigDetail(groupConfigDetail);
        return subscribeVO;
    }

    /**
     * 删除订阅
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteSubscribeById(Long id) {
        RmqSubscribeAcl existedAcl = rmqSubscribeAclService.getOne(
                new QueryWrapper<RmqSubscribeAcl>().lambda()
                        .eq(RmqSubscribeAcl::getId, id)
                        .eq(RmqSubscribeAcl::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedAcl)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        // 选择的应用配置
        RmqAppConfig rmqAppConfig = rmqAppConfigService.getOne(
                new QueryWrapper<RmqAppConfig>().lambda()
                        .eq(RmqAppConfig::getAppId, existedAcl.getAppId())
                        .eq(RmqAppConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqAppConfig)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, existedAcl.getTopicId())
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        RmqSubscribeAcl deleteRmqSubscribeAcl = new RmqSubscribeAcl();
        deleteRmqSubscribeAcl.setDeleted(id);
        deleteRmqSubscribeAcl.setVersion(existedAcl.getVersion());
        boolean deleteResult = rmqSubscribeAclService.update(
                deleteRmqSubscribeAcl,
                new UpdateWrapper<RmqSubscribeAcl>().lambda()
                        .eq(RmqSubscribeAcl::getId, id)
                        .eq(RmqSubscribeAcl::getVersion, existedAcl.getVersion())
        );
        if (!deleteResult) {
            throw new BizException(RmqRetCodeEnum.VERSION_ERROR);
        }
        // 删除指定broker中的订阅组
        String brokerNames = existedAcl.getBrokerNames();
        List<String> brokerNameList = Arrays.asList(StringUtils.split(brokerNames, ","));
        DeleteSubGroupRequest deleteSubGroupRequest = new DeleteSubGroupRequest();
        deleteSubGroupRequest.setGroupName(existedAcl.getGroupName());
        deleteSubGroupRequest.setBrokerNameList(brokerNameList);
        consumerService.deleteSubGroup(brokerNameList, deleteSubGroupRequest);

        // 删除权限配置
        PlainAccessConfig plainAccessConfig = aclService.getPlainAclConfig(false, rmqAppConfig.getAccessKey(), rmqAppConfig.getSecretKey());
        AclRequest aclRequest = RmqAclHelper.buildGroupAclConfig(rmqAppConfig.getAccessKey(), existedAcl.getGroupName(), AclPermEnum.getByPerm(existedAcl.getGroupPerm()));
        aclRequest.setConfig(plainAccessConfig);
        aclService.deletePermConfig(aclRequest);
    }

    /**
     * 分页查询订阅列表
     * @param query
     * @return
     */
    public PageResult<SubscribeVO> pageSubscribeList(SubscribeQuery query) {
        Long topicId = query.getTopicId();
        if (Objects.isNull(topicId)) {
            throw new BizException(RmqRetCodeEnum.PARAM_ILLEGAL);
        }
        PageInfo<RmqSubscribeAcl> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> rmqSubscribeAclService.list(
                                new QueryWrapper<RmqSubscribeAcl>().lambda()
                                        .eq(RmqSubscribeAcl::getTopicId, query.getTopicId())
                                        .eq(
                                                StringUtils.isNotBlank(query.getAppCode()),
                                                RmqSubscribeAcl::getAppCode,
                                                query.getAppCode()
                                        )
                                        .eq(RmqSubscribeAcl::getDeleted, DeletedEnum.NO.value)
                        )
                );
        List<RmqSubscribeAcl> subscribeAclList = pageInfo.getList();
        List<SubscribeVO> subscribeVOList = Convert.toList(SubscribeVO.class, subscribeAclList);
        Map<String, SubscribeVO> subscribeVOMap = CollectionUtils.convertMap(subscribeVOList, SubscribeVO::getGroupName, Function.identity(), (k1, k2) -> k1);
        List<GroupConsumeInfo> groupConsumeInfos = consumerService.queryGroupList(subscribeVOMap.keySet());
        if (CollectionUtils.isNotEmpty(groupConsumeInfos)) {
            for (GroupConsumeInfo groupConsumeInfo : groupConsumeInfos) {
                SubscribeVO subscribeVO = subscribeVOMap.get(groupConsumeInfo.getGroup());
                subscribeVO.setDiffTotal(groupConsumeInfo.getDiffTotal());
                subscribeVO.setConsumerTps(groupConsumeInfo.getConsumeTps());
            }
        }
        return PageUtils.pageInfoConvertToPageResult(pageInfo, subscribeVOList);
    }

    /**
     * 重置位点
     * @param form
     */
    public void resetOffset(ResetOffsetForm form) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, form.getTopicId())
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        ResetOffsetRequest resetOffsetRequest = RmqSubscribeHelper.convert(rmqTopic.getTopicName(), form);
        consumerService.resetOffset(resetOffsetRequest);
    }

    public List<String> getSubscriptionBrokerList(Long id) {
        return null;
    }
}
