package com.ahcloud.edas.powerjob.biz.infrastructure.validate.strategy;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.powerjob.biz.infrastructure.constant.enums.PowerJobRetCodeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.powerjob.common.PowerJobDKey;
import tech.powerjob.common.enums.TimeExpressionType;
import tech.powerjob.common.exception.PowerJobException;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/28 15:29
 **/
@Component
public class FixedRateTimingStrategyHandler extends AbstractTimingStrategyHandler {

    @Value("${powerjob.server.frequency-job.max-interval:120000}")
    private Integer FREQUENCY_JOB_MAX_INTERVAL;

    @Override
    public void validate(String timeExpression) {
        long delay;
        try {
            delay = Long.parseLong(timeExpression);
        } catch (Exception e) {
            throw new BizException(PowerJobRetCodeEnum.INVALID_TIME_EXPRESSION);
        }
        // 默认 120s ，超过这个限制应该使用考虑使用其他类型以减少资源占用
        int maxInterval = FREQUENCY_JOB_MAX_INTERVAL;
        if (delay > maxInterval) {
            throw new BizException(PowerJobRetCodeEnum.DELAY_TIME_EXCEPTION);
        }
        if (delay <= 0) {
            throw new BizException(PowerJobRetCodeEnum.DELAY_TIME_EXCEPTION);
        }
    }

    @Override
    public Long calculateNextTriggerTime(Long preTriggerTime, String timeExpression, Long startTime, Long endTime) {
        long r = startTime != null && startTime > preTriggerTime
                ? startTime : preTriggerTime + Long.parseLong(timeExpression);
        return endTime != null && endTime < r ? null : r;
    }

    @Override
    public TimeExpressionType supportType() {
        return TimeExpressionType.FIXED_RATE;
    }
}
