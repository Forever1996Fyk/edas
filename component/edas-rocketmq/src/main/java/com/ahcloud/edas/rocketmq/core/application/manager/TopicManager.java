package com.ahcloud.edas.rocketmq.core.application.manager;

import cn.hutool.core.convert.Convert;
import com.ahcloud.edas.common.constant.CommonConstants;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.rocketmq.core.application.helper.RmqTopicHelper;
import com.ahcloud.edas.rocketmq.core.application.service.RmqAppConfigService;
import com.ahcloud.edas.rocketmq.core.application.service.RmqSubscribeAclService;
import com.ahcloud.edas.rocketmq.core.application.service.RmqTopicService;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.vo.SubscribeGroupConsumeDetailVO;
import com.ahcloud.edas.rocketmq.core.domain.topic.form.TopicAddForm;
import com.ahcloud.edas.rocketmq.core.domain.topic.form.TopicUpdateForm;
import com.ahcloud.edas.rocketmq.core.domain.topic.query.TopicQuery;
import com.ahcloud.edas.rocketmq.core.domain.topic.vo.TopicVO;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.AclPermEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.RmqRetCodeEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.TopicPermEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqSubscribeAcl;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqTopic;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.TopicConfigInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.TopicConsumerInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.BrokerService;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.TopicService;
import com.ahcloud.edas.rocketmq.core.infrastructure.util.PageUtils;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 11:02
 **/
@Slf4j
@Component
public class TopicManager {
    @Resource
    private TopicService topicService;
    @Resource
    private BrokerService brokerService;
    @Resource
    private RmqTopicService rmqTopicService;
    @Resource
    private RmqConfigManager rmqConfigManager;
    @Resource
    private RmqSubscribeAclService rmqSubscribeAclService;

    /**
     * 新增topic
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addTopic(TopicAddForm form) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getTopicName, form.getTopicName())
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_EXISTED);
        }
        String topicName = RmqTopicHelper.rebuildTopicName(form.getTopicName(), form.getAppCode(), form.getEnv());
        RmqTopic addTopic = Convert.convert(RmqTopic.class, form);
        List<String> brokerNameList = form.getBrokerNameList();
        String brokers = StringUtils.join(brokerNameList, CommonConstants.COMMA);
        addTopic.setBrokerNames(brokers);
        addTopic.setTopicName(topicName);
        String userNameBySession = UserUtils.getUserNameBySession();
        addTopic.setCreator(userNameBySession);
        addTopic.setModifier(userNameBySession);
        rmqTopicService.save(addTopic);

        TopicConfigInfo topicConfigInfo = new TopicConfigInfo();
        topicConfigInfo.setTopicName(topicName);
        topicConfigInfo.setPerm(form.getPerm());
        topicConfigInfo.setWriteQueueNums(form.getWriteQueueNum());
        topicConfigInfo.setReadQueueNums(form.getReadQueueNum());
        topicConfigInfo.setBrokerNameList(form.getBrokerNameList());
        topicService.createOrUpdate(topicConfigInfo, true);
        // 分配topic权限 对于自己的topic 可以生成和消费
        rmqConfigManager.assignTopicAcl(form.getAppId(), topicName, AclPermEnum.ANY);
    }

    /**
     * 更新topic
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTopic(TopicUpdateForm form) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, form.getId())
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        RmqTopic updateTopic = Convert.convert(RmqTopic.class, form);
        List<String> brokerNameList = form.getBrokerNameList();
        String brokers = StringUtils.join(brokerNameList, CommonConstants.COMMA);
        updateTopic.setBrokerNames(brokers);
        updateTopic.setVersion(rmqTopic.getVersion());
        updateTopic.setModifier(UserUtils.getUserNameBySession());
        rmqTopicService.update(
                updateTopic,
                new UpdateWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, form.getId())
                        .eq(RmqTopic::getVersion, rmqTopic.getVersion())
        );
        TopicConfigInfo topicConfigInfo = new TopicConfigInfo();
        topicConfigInfo.setTopicName(rmqTopic.getTopicName());
        topicConfigInfo.setPerm(form.getPerm());
        topicConfigInfo.setWriteQueueNums(form.getWriteQueueNum());
        topicConfigInfo.setReadQueueNums(form.getReadQueueNum());
        topicConfigInfo.setBrokerNameList(form.getBrokerNameList());
        topicService.createOrUpdate(topicConfigInfo, false);
    }

    /**
     * 根据id删除topic
     * @param id
     */
    public void deleteById(Long id) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, id)
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        RmqTopic deleteTopic = new RmqTopic();
        deleteTopic.setVersion(rmqTopic.getVersion());
        deleteTopic.setDeleted(id);
        rmqTopicService.update(
                deleteTopic,
                new UpdateWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, id)
                        .eq(RmqTopic::getVersion, rmqTopic.getVersion())
        );
        topicService.deleteTopic(rmqTopic.getTopicName());
    }

    /**
     * 根据id查询topic
     * @param id
     * @return
     */
    public TopicVO findById(Long id) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, id)
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        TopicVO topicVO = Convert.convert(TopicVO.class, rmqTopic);
        if (StringUtils.isNotBlank(rmqTopic.getBrokerNames())) {
            List<String> list = Arrays.asList(StringUtils.split(rmqTopic.getBrokerNames(), ","));
            topicVO.setBrokerNameList(list);
        }
        topicVO.setPermDesc(TopicPermEnum.getByPerm(rmqTopic.getPerm()).getDesc());
        return topicVO;
    }

    /**
     * 分页查询topic
     * @param query
     * @return
     */
    public PageResult<TopicVO> pageTopicList(TopicQuery query) {
        String env = query.getEnv();
        if (StringUtils.isBlank(env)) {
            throw new BizException(RmqRetCodeEnum.PARAM_ILLEGAL);
        }
        PageInfo<RmqTopic> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> rmqTopicService.list(
                                new QueryWrapper<RmqTopic>().lambda()
                                        .like(
                                                StringUtils.isNotBlank(query.getTopicName()),
                                                RmqTopic::getTopicName,
                                                query.getTopicName()
                                        )
                                        .eq(
                                                StringUtils.isNotBlank(query.getAppCode()),
                                                RmqTopic::getAppCode,
                                                query.getAppCode()
                                        )
                                        .eq(RmqTopic::getEnv, env)
                                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
                        )
                );
        List<RmqTopic> list = pageInfo.getList();
        List<TopicVO> topicVOList = list.stream().map(
                rmqTopic -> {
                    TopicVO topicVO = Convert.convert(TopicVO.class, rmqTopic);
                    topicVO.setPermDesc(TopicPermEnum.getByPerm(rmqTopic.getPerm()).getDesc());
                    return topicVO;
                }
        ).collect(Collectors.toList());
        return PageUtils.pageInfoConvertToPageResult(pageInfo, topicVOList);
    }

    /**
     * 查询订阅组消费详情
     * @param topicId
     * @param groupName
     * @return
     */
    public SubscribeGroupConsumeDetailVO findSubscribeGroupConsumeDetail(Long topicId, String groupName) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, topicId)
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        TopicConsumerInfo topicConsumerInfo = topicService.queryConsumeStateByTopicNameAndGroup(rmqTopic.getTopicName(), groupName);
        return RmqTopicHelper.convert(groupName, topicConsumerInfo);
    }

    public List<String> getSelectSubscribeList(Long topicId) {
        RmqTopic rmqTopic = rmqTopicService.getOne(
                new QueryWrapper<RmqTopic>().lambda()
                        .eq(RmqTopic::getId, topicId)
                        .eq(RmqTopic::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(rmqTopic)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        List<RmqSubscribeAcl> rmqSubscribeAclList = rmqSubscribeAclService.list(
                new QueryWrapper<RmqSubscribeAcl>().lambda()
                        .eq(RmqSubscribeAcl::getTopicId, topicId)
                        .eq(RmqSubscribeAcl::getDeleted, DeletedEnum.NO.value)
        );
        return CollectionUtils.convertList(rmqSubscribeAclList, RmqSubscribeAcl::getGroupName);
    }

    /**
     * 获取brokerName选择列表
     * @return
     */
    public List<String> getSelectBrokerNameList() {
        return brokerService.getBrokerNameList();
    }
}
