package com.ahcloud.edas.pulsar.core.application.checker;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PulsarRetCodeEnum;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/28 21:29
 **/
public class TopicChecker {

    /**
     * 校验分区数
     * @param partitionNum
     */
    public static void checkPartitionNum(Integer partitionNum) {
        if (partitionNum < 1 || partitionNum > 32) {
            throw new BizException(PulsarRetCodeEnum.PARTITION_NUM_ERROR);
        }
    }

    /**
     * 校验分区数
     * @param partitionNum
     * @param oldPartitionNum
     */
    public static void checkPartitionNum(Integer partitionNum, Integer oldPartitionNum) {
        checkPartitionNum(partitionNum);
        if (partitionNum < oldPartitionNum) {
            throw new BizException(PulsarRetCodeEnum.PARTITION_NUM_MUST_MORE_THAN, oldPartitionNum.toString());
        }
    }
}
