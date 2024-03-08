package com.ahcloud.edas.admin.biz.domain.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 16:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppSelectVO {

    /**
     * appId
     */
    private Long appId;

    /**
     * app编码
     */
    private String appCodeAndEnv;
}
