package com.ahcloud.edas.admin.biz.domain.user.vo;

import com.ahcloud.edas.admin.biz.domain.user.intro.vo.SysUserIntroVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description: 系统用户个人信息
 * @author: YuKai Fan
 * @create: 2022-08-21 14:03
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysUserPersonalInfoVo {

    /**
     * 用户基本信息
     */
    private BaseUserInfoVo baseUserInfo;

    /**
     * 用户简介
     */
    private SysUserIntroVo sysUserIntro;
}
