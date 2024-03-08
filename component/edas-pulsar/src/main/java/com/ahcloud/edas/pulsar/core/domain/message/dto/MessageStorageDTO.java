package com.ahcloud.edas.pulsar.core.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 10:36
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageStorageDTO {

    /**
     * 存储时间
     */
    private Date storageTime;

    /**
     * 存储耗时
     */
    private Long storageMills;

    /**
     * 存储状态
     */
    private Integer storageStatus;
}
