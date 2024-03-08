package com.ahcloud.edas.powerjob.biz.application.manager;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.powerjob.biz.application.helper.PowerJobAppInfoHelper;
import com.ahcloud.edas.powerjob.biz.application.service.PowerjobAppInfoService;
import com.ahcloud.edas.powerjob.biz.domain.appinfo.form.PowerJobAppInfoAddForm;
import com.ahcloud.edas.powerjob.biz.infrastructure.config.PowerJobProperties;
import com.ahcloud.edas.powerjob.biz.infrastructure.constant.enums.PowerJobRetCodeEnum;
import com.ahcloud.edas.powerjob.biz.infrastructure.repository.bean.PowerjobAppInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.powerjob.client.PowerJobClient;
import tech.powerjob.common.request.http.ModifyAppInfoRequest;
import tech.powerjob.common.response.ResultDTO;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 17:57
 **/
@Slf4j
@Component
public class PowerJobAppInfoManager {
    @Resource
    private PowerJobClient powerJobClient;
    @Resource
    private PowerJobProperties properties;
    @Resource
    private PowerjobAppInfoService powerjobAppInfoService;

    /**
     * 新增app信息
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addAppInfo(PowerJobAppInfoAddForm form) {
        PowerjobAppInfo powerjobAppInfo = powerjobAppInfoService.getOne(
                new QueryWrapper<PowerjobAppInfo>().lambda()
                        .eq(PowerjobAppInfo::getAppId, form.getAppId())
                        .eq(PowerjobAppInfo::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(powerjobAppInfo)) {
            throw new BizException(PowerJobRetCodeEnum.DATA_EXISTED);
        }
        powerjobAppInfo = PowerJobAppInfoHelper.convert(form);
        String powerJobAppName = PowerJobAppInfoHelper.getPowerJobAppName(form.getAppId());
        String powerJobAppPassword = properties.getPassword();
        ModifyAppInfoRequest request = new ModifyAppInfoRequest();
        request.setAppName(powerJobAppName);
        request.setPassword(powerJobAppPassword);
        ResultDTO<Long> resultDTO = powerJobClient.saveOrUpdateApp(request);
        if (!resultDTO.isSuccess()) {
            throw new BizException(PowerJobRetCodeEnum.APP_INFO_SAVE_FAILED);
        }
        Long powerJobAppId = resultDTO.getData();
        if (powerJobAppId == null) {
            throw new BizException(PowerJobRetCodeEnum.APP_INFO_SAVE_FAILED);
        }
        powerjobAppInfo.setPowerjobAppId(powerJobAppId);
        powerjobAppInfo.setPowerjobAppName(powerJobAppName);
        powerjobAppInfo.setPowerjobAppPassword(powerJobAppPassword);
        powerjobAppInfoService.save(powerjobAppInfo);
    }

    /**
     * 校验appInfo是否存在
     * @param appId
     * @return
     */
    public boolean assertAppInfo(Long appId) {
        PowerjobAppInfo powerjobAppInfo = powerjobAppInfoService.getOne(
                new QueryWrapper<PowerjobAppInfo>().lambda()
                        .eq(PowerjobAppInfo::getAppId, appId)
                        .eq(PowerjobAppInfo::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(powerjobAppInfo)) {
            return false;
        }
        Long powerJobAppId = powerJobClient.assertApp(powerjobAppInfo.getPowerjobAppName(), powerjobAppInfo.getPowerjobAppPassword());
        return powerJobAppId != null;
    }

    /**
     * 获取powerJob appId
     * @param appId
     * @return
     */
    public Long getPowerJobAppId(Long appId) {
        PowerjobAppInfo powerjobAppInfo = powerjobAppInfoService.getOne(
                new QueryWrapper<PowerjobAppInfo>().lambda()
                        .eq(PowerjobAppInfo::getAppId, appId)
                        .eq(PowerjobAppInfo::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(powerjobAppInfo)) {
            throw new BizException(PowerJobRetCodeEnum.DATA_NOT_EXISTED);
        }
        return powerjobAppInfo.getPowerjobAppId();
    }

}
