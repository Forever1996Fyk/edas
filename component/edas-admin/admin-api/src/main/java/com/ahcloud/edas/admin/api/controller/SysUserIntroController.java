package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.SysUserIntroManager;
import com.ahcloud.edas.admin.biz.domain.user.intro.form.SysUserIntroUpdateForm;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 系统用户简介controller
 * @author: YuKai Fan
 * @create: 2022-08-19 18:57
 **/
@RestController
@RequestMapping("/intro")
public class SysUserIntroController {
    @Resource
    private SysUserIntroManager sysUserIntroManager;

    /**
     * 更新用户个人简介
     * @param form
     * @return
     */
    @PostMapping("/updateSysUserIntro")
    public ResponseResult<Void> updateSysUserIntro(@Valid @RequestBody SysUserIntroUpdateForm form) {
        sysUserIntroManager.updateIntro(form);
        return ResponseResult.ofSuccess();
    }


}
