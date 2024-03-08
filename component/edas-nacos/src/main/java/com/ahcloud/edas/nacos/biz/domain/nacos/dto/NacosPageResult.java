package com.ahcloud.edas.nacos.biz.domain.nacos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 22:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NacosPageResult<T> {

    /**
     * 总数
     */
    private Integer totalCount;

    /**
     * 当前页
     */
    private Integer pageNumber;

    /**
     * 总页数
     */
    private Integer pagesAvailable;

    /**
     * 分页数据
     */
    private List<T> pageItems;

}
