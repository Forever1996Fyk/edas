package com.ahcloud.edas.pulsar.core.domain.bookie.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 10:21
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookieVO {

    /**
     * 存储节点key
     */
    private String bookie;

    /**
     * 状态
     */
    private String status;

    /**
     * 存储列表
     */
    private List<String> storage;
}
