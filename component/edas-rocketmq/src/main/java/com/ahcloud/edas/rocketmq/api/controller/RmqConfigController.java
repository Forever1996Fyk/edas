package com.ahcloud.edas.rocketmq.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.rocketmq.core.application.manager.RmqConfigManager;
import com.ahcloud.edas.rocketmq.core.domain.acl.form.RmqAppConfigAddForm;
import com.ahcloud.edas.rocketmq.core.domain.acl.vo.RmqAppConfigVO;
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
 * @create: 2024/1/2 11:19
 **/
@RestController
@RequestMapping("/rmqConfig")
public class RmqConfigController {
    @Resource
    private RmqConfigManager rmqConfigManager;

    /**
     * 新增配置
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> addConfig(@RequestBody @Valid RmqAppConfigAddForm form) {
        rmqConfigManager.addRmqConfig(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据appId 获取配置
     * @param appId
     * @return
     */
    @GetMapping("/findConfigByAppId/{appId}")
    public ResponseResult<RmqAppConfigVO> findConfigByAppId(@PathVariable("appId") Long appId) {
        return ResponseResult.ofSuccess(rmqConfigManager.findConfigByAppId(appId));
    }

}
