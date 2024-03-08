package com.ahcloud.edas.powerjob.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.powerjob.biz.infrastructure.validate.TimingStrategyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.powerjob.common.enums.TimeExpressionType;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/28 15:15
 **/
@RestController
@RequestMapping("/validate")
public class ValidateController {

    @Resource
    private TimingStrategyService timingStrategyService;

    /**
     * 表达式校验
     * @param type
     * @param timeExpression
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/timeExpression")
    public ResponseResult<List<String>> checkTimeExpression(
            @RequestParam Integer type,
            @RequestParam String timeExpression,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime
    ) {
        TimeExpressionType timeExpressionType = TimeExpressionType.of(type);
        timingStrategyService.validate(timeExpressionType, timeExpression, startTime, endTime);
        return ResponseResult.ofSuccess(timingStrategyService.calculateNextTriggerTimes(timeExpressionType, timeExpression, startTime, endTime));
    }
}
