package com.ahcloud.edas.rocketmq.core.application.checker;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.rocketmq.core.domain.message.query.RmqMessageQuery;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.RmqRetCodeEnum;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/12 17:08
 **/
public class RmqMessageChecker {

    public static void checkQueryParam(RmqMessageQuery query) {
        if (Objects.isNull(query.getTopicId())) {
            throw new BizException(RmqRetCodeEnum.PARAM_ILLEGAL);
        }
    }
}
