package com.ahcloud.edas.powerjob.biz.infrastructure.validate.strategy;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/28 15:19
 **/
public abstract class AbstractTimingStrategyHandler implements TimingStrategyHandler{
    @Override
    public void validate(String timeExpression) {
        // do nothing
    }

    @Override
    public Long calculateNextTriggerTime(Long preTriggerTime, String timeExpression, Long startTime, Long endTime) {
        // do nothing
        return null;
    }
}
