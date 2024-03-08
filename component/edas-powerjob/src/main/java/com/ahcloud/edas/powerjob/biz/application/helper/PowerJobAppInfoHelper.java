package com.ahcloud.edas.powerjob.biz.application.helper;

import com.ahcloud.edas.powerjob.biz.domain.appinfo.form.PowerJobAppInfoAddForm;
import com.ahcloud.edas.powerjob.biz.infrastructure.repository.bean.PowerjobAppInfo;
import com.ahcloud.edas.uaa.client.UserUtils;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 18:00
 **/
public class PowerJobAppInfoHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static PowerjobAppInfo convert(PowerJobAppInfoAddForm form) {
        PowerjobAppInfo powerjobAppInfo = new PowerjobAppInfo();
        powerjobAppInfo.setAppId(form.getAppId());
        powerjobAppInfo.setAppCode(form.getAppCode());
        powerjobAppInfo.setEnv(form.getEnv());
        String userNameBySession = UserUtils.getUserNameBySession();
        powerjobAppInfo.setCreator(userNameBySession);
        powerjobAppInfo.setModifier(userNameBySession);
        return powerjobAppInfo;
    }

    /**
     * 获取appName
     * @param appId
     * @return
     */
    public static String getPowerJobAppName(Long appId) {
        return appId.toString();
    }
}
