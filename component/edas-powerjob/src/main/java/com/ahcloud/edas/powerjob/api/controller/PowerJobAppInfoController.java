package com.ahcloud.edas.powerjob.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.powerjob.biz.application.manager.PowerJobAppInfoManager;
import com.ahcloud.edas.powerjob.biz.domain.appinfo.form.PowerJobAppInfoAddForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 17:56
 **/
@RestController
@RequestMapping("/powerjob/appInfo")
public class PowerJobAppInfoController {

    @Resource
    private PowerJobAppInfoManager powerJobAppInfoManager;

    /**
     * 新增appInfo
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> addAppInfo(@RequestBody @Valid PowerJobAppInfoAddForm form) {
        powerJobAppInfoManager.addAppInfo(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 校验appInfo是否存在
     * @param appId
     * @return
     */
    @GetMapping("/assert/{appId}")
    public ResponseResult<Boolean> assertAppInfo(@PathVariable("appId") Long appId) {
        return ResponseResult.ofSuccess(powerJobAppInfoManager.assertAppInfo(appId));
    }

}
