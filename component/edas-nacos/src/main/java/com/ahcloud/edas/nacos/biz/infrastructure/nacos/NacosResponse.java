package com.ahcloud.edas.nacos.biz.infrastructure.nacos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 17:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NacosResponse {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private String data;
}
