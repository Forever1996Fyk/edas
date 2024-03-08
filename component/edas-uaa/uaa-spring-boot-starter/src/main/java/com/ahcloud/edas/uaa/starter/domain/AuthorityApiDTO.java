package com.ahcloud.edas.uaa.starter.domain;

import com.ahcloud.edas.common.enums.YesOrNoEnum;
import com.ahcloud.edas.uaa.starter.core.constant.enums.ApiStatusEnum;
import com.ahcloud.edas.uaa.starter.core.constant.enums.ReadOrWriteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description: 权限api
 * @author: YuKai Fan
 * @create: 2021-12-24 15:28
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityApiDTO implements Serializable {

    /**
     * api编码
     */
    private String apiCode;

    /**
     * 请求路径
     */
    private String uri;

    /**
     * api状态
     */
    private ApiStatusEnum apiStatusEnum;

    /**
     * 是否认证
     */
    private YesOrNoEnum auth;

    /**
     * 是否公开
     */
    private YesOrNoEnum open;

    /**
     * 是否全局
     */
    private YesOrNoEnum global;

    /**
     * 读写类型
     */
    private ReadOrWriteEnum readOrWriteEnum;
}
