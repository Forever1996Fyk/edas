package com.ahcloud.edas.rocketmq.core.application.manager;

import cn.hutool.core.convert.Convert;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.rocketmq.core.application.helper.RmqAclHelper;
import com.ahcloud.edas.rocketmq.core.application.helper.RmqConfigHelper;
import com.ahcloud.edas.rocketmq.core.application.service.RmqAppConfigService;
import com.ahcloud.edas.rocketmq.core.domain.acl.form.RmqAppConfigAddForm;
import com.ahcloud.edas.rocketmq.core.domain.acl.vo.RmqAppConfigVO;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.AclPermEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.RmqRetCodeEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqAppConfig;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.AclService;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.rocketmq.common.AclConfig;
import org.apache.rocketmq.common.PlainAccessConfig;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 09:42
 **/
@Slf4j
@Component
public class RmqConfigManager {
    @Resource
    private AclService aclService;
    @Resource
    private RmqAppConfigService rmqAppConfigService;

    /**
     * 新增rmq app配置
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRmqConfig(RmqAppConfigAddForm form) {
        RmqAppConfig existedRmqAppConfig = rmqAppConfigService.getOne(
                new QueryWrapper<RmqAppConfig>().lambda()
                        .eq(RmqAppConfig::getAppId, form.getAppId())
                        .eq(RmqAppConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedRmqAppConfig)) {
            throw new BizException(RmqRetCodeEnum.DATA_EXISTED);
        }
        RmqAppConfig rmqAppConfig = new RmqAppConfig();
        rmqAppConfig.setAppCode(form.getAppCode());
        rmqAppConfig.setAppId(form.getAppId());
        rmqAppConfig.setEnv(form.getEnv());
        rmqAppConfig.setCreator(UserUtils.getUserNameBySession());
        rmqAppConfig.setModifier(UserUtils.getUserNameBySession());
        Pair<String, String> keyPair = RmqConfigHelper.generateAccessPair();
        rmqAppConfig.setAccessKey(keyPair.getLeft());
        rmqAppConfig.setSecretKey(keyPair.getRight());
        rmqAppConfigService.save(rmqAppConfig);
        // 新增rmq acl配置
        aclService.addAclConfig(RmqAclHelper.buildDefaultAccessConfig(rmqAppConfig));
    }

    /**
     * 根据appId获取配置
     * @param appId
     * @return
     */
    public RmqAppConfigVO findConfigByAppId(Long appId) {
        RmqAppConfig existedRmqAppConfig = rmqAppConfigService.getOne(
                new QueryWrapper<RmqAppConfig>().lambda()
                        .eq(RmqAppConfig::getAppId, appId)
                        .eq(RmqAppConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedRmqAppConfig)) {
           return null;
        }
        return Convert.convert(RmqAppConfigVO.class, existedRmqAppConfig);
    }

    /**
     * 根据appId获取acl配置
     * @param appId
     * @return
     */
    public PlainAccessConfig findAclConfigByAppId(Long appId) {
        RmqAppConfig existedRmqAppConfig = rmqAppConfigService.getOne(
                new QueryWrapper<RmqAppConfig>().lambda()
                        .eq(RmqAppConfig::getAppId, appId)
                        .eq(RmqAppConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedRmqAppConfig)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        PlainAccessConfig plainAclConfig = aclService.getPlainAclConfig(false, existedRmqAppConfig.getAccessKey(), existedRmqAppConfig.getSecretKey());
        if (Objects.isNull(plainAclConfig)) {
            throw new BizException(RmqRetCodeEnum.ACL_CONFIG_NOT_EXISTED);
        }
        return plainAclConfig;
    }

    /**
     * 分配Topic acl
     * @param appId
     * @param topicName
     * @param topicPerm
     */
    public void assignTopicAcl(Long appId, String topicName, AclPermEnum topicPerm) {
        RmqAppConfig existedRmqAppConfig = rmqAppConfigService.getOne(
                new QueryWrapper<RmqAppConfig>().lambda()
                        .eq(RmqAppConfig::getAppId, appId)
                        .eq(RmqAppConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedRmqAppConfig)) {
            throw new BizException(RmqRetCodeEnum.DATA_NOT_EXISTED);
        }
        aclService.addOrUpdateAclTopicConfig(RmqAclHelper.buildTopicAclConfig(existedRmqAppConfig.getAccessKey(), topicName, topicPerm));
    }
}
