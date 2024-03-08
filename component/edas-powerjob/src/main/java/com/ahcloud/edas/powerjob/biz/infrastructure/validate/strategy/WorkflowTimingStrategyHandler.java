package com.ahcloud.edas.powerjob.biz.infrastructure.validate.strategy;

import org.springframework.stereotype.Component;
import tech.powerjob.common.enums.TimeExpressionType;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/28 15:28
 **/
@Component
public class WorkflowTimingStrategyHandler extends AbstractTimingStrategyHandler{
    @Override
    public TimeExpressionType supportType() {
        return TimeExpressionType.WORKFLOW;
    }
}
