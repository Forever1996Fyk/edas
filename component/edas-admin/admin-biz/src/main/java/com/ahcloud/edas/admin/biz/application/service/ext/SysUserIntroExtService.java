package com.ahcloud.edas.admin.biz.application.service.ext;

import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserIntro;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-19 22:13
 **/
public interface SysUserIntroExtService extends IService<SysUserIntro> {

    /**
     * 根据用户id获取用户简介
     * @param userId
     * @return
     */
    SysUserIntro getOneByUserId(Long userId);
}
