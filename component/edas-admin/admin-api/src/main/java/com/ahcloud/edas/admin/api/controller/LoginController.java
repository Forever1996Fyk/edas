package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.api.assembler.assembler.ValidateCodeAssembler;
import com.ahcloud.edas.admin.biz.application.manager.ValidateCodeManager;
import com.ahcloud.edas.admin.biz.application.manager.login.LoginProvider;
import com.ahcloud.edas.admin.biz.domain.login.ThirdCodeLoginForm;
import com.ahcloud.edas.admin.biz.domain.login.UsernamePasswordLoginForm;
import com.ahcloud.edas.admin.biz.domain.login.ValidateCodeLoginForm;
import com.ahcloud.edas.uaa.starter.domain.token.Token;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 登录controller
 * @author: YuKai Fan
 * @create: 2021-12-17 17:56
 **/
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginProvider loginProvider;
    @Resource
    private ValidateCodeAssembler assembler;
    @Resource
    private ValidateCodeManager validateCodeManager;

    /**
     * 账号密码登录
     * @param form
     * @return
     */
    @PostMapping("/usernamePasswordLogin")
    public ResponseResult<Token> usernamePasswordLogin(@RequestBody @Valid UsernamePasswordLoginForm form) {
        return ResponseResult.ofSuccess(loginProvider.getAccessToken(form));
    }

    /**
     * 验证码登录
     * @param form
     * @return
     */
    @PostMapping("/validateCodeLogin")
    public ResponseResult<Token> validateCodeLogin(@RequestBody @Valid ValidateCodeLoginForm form) {
        validateCodeManager.validateCode(assembler.convert(form.getSender()), form.getValidateCode());
        return ResponseResult.ofSuccess(loginProvider.getAccessToken(form));
    }

    /**
     * 第三方登录
     * @param form
     * @return
     */
    @PostMapping("/thirdLogin")
    public ResponseResult<Token> thirdLogin(@RequestBody @Valid ThirdCodeLoginForm form) {
        return ResponseResult.ofSuccess(loginProvider.getAccessToken(form));
    }
}
