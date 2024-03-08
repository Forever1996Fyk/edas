package com.ahcloud.edas.admin.biz.domain.user.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/20 14:19
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppUserInfo {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String name;
}
