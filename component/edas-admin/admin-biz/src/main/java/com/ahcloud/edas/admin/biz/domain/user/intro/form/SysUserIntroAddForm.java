package com.ahcloud.edas.admin.biz.domain.user.intro.form;

import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-19 18:59
 **/
@Data
public class SysUserIntroAddForm {

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 标签
     */
    private String tags;

    /**
     * 位置
     */
    private String location;

    /**
     * 生日
     */
    private String birthDay;

    /**
     * 技能
     */
    private String skills;

    /**
     * 爱好
     */
    private String hobby;
}
