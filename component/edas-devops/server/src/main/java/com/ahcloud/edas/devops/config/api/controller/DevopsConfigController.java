package com.ahcloud.edas.devops.config.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.devops.config.biz.application.manager.DevopsConfigManager;
import com.ahcloud.edas.devops.config.biz.domain.config.form.DevopsConfigAddForm;
import com.ahcloud.edas.devops.config.biz.domain.config.form.DevopsConfigUpdateForm;
import com.ahcloud.edas.devops.config.biz.domain.config.vo.DevopsConfigVO;
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
 * @create: 2023/12/5 17:16
 **/
@RestController
@RequestMapping("/devops/config")
public class DevopsConfigController {
    @Resource
    private DevopsConfigManager devopsConfigManager;

    /**
     * 新增配置
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid DevopsConfigAddForm form) {
        devopsConfigManager.addConfig(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新配置
     * @param form
     * @return
     */
    @PostMapping("/updateById")
    public ResponseResult<Void> abortedJob(@RequestBody @Valid DevopsConfigUpdateForm form) {
        devopsConfigManager.updateConfig(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据appId查询配置
     * @param appId
     * @return
     */
    @GetMapping("/findByAppId/{appId}")
    public ResponseResult<DevopsConfigVO> findByAppId(@PathVariable("appId") Long appId) {
        return ResponseResult.ofSuccess(devopsConfigManager.findConfigByAppId(appId));
    }

}
