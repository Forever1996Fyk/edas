package com.ahcloud.edas.admin.biz.domain.user.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/13 16:04
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppLoginUserPageViewInfoVo {

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String userAvatar;
}
