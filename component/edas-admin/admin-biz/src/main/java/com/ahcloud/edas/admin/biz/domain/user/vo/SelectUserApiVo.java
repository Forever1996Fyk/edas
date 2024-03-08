package com.ahcloud.edas.admin.biz.domain.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-12 13:56
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectUserApiVo {

    /**
     * 已分配接口集合
     */
    private Set<ApiInfoVo> allocatedApiSet;


    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiInfoVo {

        /**
         * api id
         */
        private Long id;

        /**
         * api编码
         */
        private String apiCode;

        /**
         * api名称
         */
        private String apiName;
    }
}
