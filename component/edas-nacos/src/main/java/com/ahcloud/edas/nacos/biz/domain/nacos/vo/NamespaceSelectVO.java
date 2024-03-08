package com.ahcloud.edas.nacos.biz.domain.nacos.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 00:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NamespaceSelectVO {

    /**
     * 名称
     */
    private String name;

    /**
     * id
     */
    private String id;
}
