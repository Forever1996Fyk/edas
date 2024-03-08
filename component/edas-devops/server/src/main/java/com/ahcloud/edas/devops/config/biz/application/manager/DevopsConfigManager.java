package com.ahcloud.edas.devops.config.biz.application.manager;

import com.ahcloud.edas.common.enums.AppTypeEnum;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.devops.config.biz.application.service.DevopsConfigService;
import com.ahcloud.edas.devops.config.biz.domain.config.dto.DevelopmentConfig;
import com.ahcloud.edas.devops.config.biz.domain.config.form.DevopsConfigAddForm;
import com.ahcloud.edas.devops.config.biz.domain.config.form.DevopsConfigUpdateForm;
import com.ahcloud.edas.devops.config.biz.domain.config.vo.DevopsConfigVO;
import com.ahcloud.edas.devops.config.biz.infrastructure.repository.bean.DevopsConfig;
import com.ahcloud.edas.devops.config.biz.infrastructure.constant.enums.DevopsRetCodeEnum;
import com.ahcloud.edas.devops.gitlab.biz.domain.dto.GitlabConfig;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.dto.JenkinsConfig;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 17:16
 **/
@Slf4j
@Component
public class DevopsConfigManager {
    @Resource
    private DevopsConfigService devopsConfigService;

    /**
     * 新增配置
     * @param form
     */
    public void addConfig(DevopsConfigAddForm form) {
        DevopsConfig existedConfig = devopsConfigService.getOne(
                new QueryWrapper<DevopsConfig>().lambda()
                        .eq(DevopsConfig::getAppId, form.getAppId())
                        .eq(DevopsConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedConfig)) {
            throw new BizException(DevopsRetCodeEnum.DATA_EXISTED);
        }
        DevopsConfig devopsConfig = new DevopsConfig();
        devopsConfig.setAppCode(form.getAppCode());
        devopsConfig.setAppId(form.getAppId());
        devopsConfig.setEnv(form.getEnv());
        devopsConfig.setType(form.getType());
        devopsConfig.setGitlabConfig(JsonUtils.toJsonString(form.getGitlabConfig()));
        devopsConfig.setJenkinsConfig(JsonUtils.toJsonString(form.getJenkinsConfig()));

        AppTypeEnum appTypeEnum = AppTypeEnum.getByType(form.getType());
        if (appTypeEnum.isJava()) {
            if (Objects.isNull(form.getDevelopmentConfig())) {
                throw new BizException(DevopsRetCodeEnum.PARAM_MISS, "developmentConfig");
            }
            devopsConfig.setDevelopmentConfig(JsonUtils.toJsonString(form.getDevelopmentConfig()));
        }
        devopsConfig.setCreator(UserUtils.getUserNameBySession());
        devopsConfig.setModifier(UserUtils.getUserNameBySession());
        devopsConfigService.save(devopsConfig);
    }

    /**
     * 修改配置
     * @param form
     */
    public void updateConfig(DevopsConfigUpdateForm form) {
        DevopsConfig existedConfig = devopsConfigService.getOne(
                new QueryWrapper<DevopsConfig>().lambda()
                        .eq(DevopsConfig::getAppId, form.getAppId())
                        .eq(DevopsConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedConfig)) {
            throw new BizException(DevopsRetCodeEnum.DATA_NOT_EXISTED);
        }
        DevopsConfig devopsConfig = new DevopsConfig();
        devopsConfig.setId(existedConfig.getId());
        devopsConfig.setAppCode(form.getAppCode());
        devopsConfig.setAppId(form.getAppId());
        devopsConfig.setEnv(form.getEnv());
        devopsConfig.setGitlabConfig(JsonUtils.toJsonString(form.getGitlabConfig()));
        devopsConfig.setJenkinsConfig(JsonUtils.toJsonString(form.getJenkinsConfig()));
        if (Objects.nonNull(form.getDevelopmentConfig())) {
            devopsConfig.setDevelopmentConfig(JsonUtils.toJsonString(form.getDevelopmentConfig()));
        }
        devopsConfig.setModifier(UserUtils.getUserNameBySession());
        devopsConfigService.updateById(devopsConfig);
    }

    /**
     * 查询配置
     * @param appId
     */
    public DevopsConfigVO findConfigByAppId(Long appId) {
        DevopsConfig existedConfig = devopsConfigService.getOne(
                new QueryWrapper<DevopsConfig>().lambda()
                        .eq(DevopsConfig::getAppId, appId)
                        .eq(DevopsConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedConfig)) {
            return null;
        }
        DevopsConfigVO devopsConfig = new DevopsConfigVO();
        devopsConfig.setId(existedConfig.getId());
        devopsConfig.setAppCode(existedConfig.getAppCode());
        devopsConfig.setAppId(existedConfig.getAppId());
        devopsConfig.setEnv(existedConfig.getEnv());
        devopsConfig.setType(existedConfig.getType());
        devopsConfig.setGitlabConfig(JsonUtils.stringToBean(existedConfig.getGitlabConfig(), GitlabConfig.class));
        devopsConfig.setJenkinsConfig(JsonUtils.stringToBean(existedConfig.getJenkinsConfig(), JenkinsConfig.class));
        if (StringUtils.isNotBlank(existedConfig.getDevelopmentConfig())) {
            devopsConfig.setDevelopmentConfig(JsonUtils.stringToBean(existedConfig.getDevelopmentConfig(), DevelopmentConfig.class));
        }
        return devopsConfig;
    }
}
