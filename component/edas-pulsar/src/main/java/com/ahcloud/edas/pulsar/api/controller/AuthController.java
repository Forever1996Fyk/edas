package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.AuthManager;
import com.ahcloud.edas.pulsar.core.domain.auth.form.RoleTokenAddForm;
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
 * @create: 2024/2/20 14:54
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthManager authManager;

    /**
     * 初始化token
     * @param form
     * @return
     */
    @PostMapping("/initToken")
    public ResponseResult<Void> initBrokerToken(@RequestBody @Valid RoleTokenAddForm form) {
        authManager.initRoleToke(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 获取token
     * @param appId
     * @return
     */
    @GetMapping("/getBrokerToken/{appId}")
    public ResponseResult<String> getBrokerToken(@PathVariable("appId") Long appId) {
        return ResponseResult.ofSuccess(authManager.getToken(appId));
    }
}
