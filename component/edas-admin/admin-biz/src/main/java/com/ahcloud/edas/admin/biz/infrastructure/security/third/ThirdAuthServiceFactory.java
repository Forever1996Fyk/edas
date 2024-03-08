package com.ahcloud.edas.admin.biz.infrastructure.security.third;

import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ThirdSourceEnum;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/3 10:58
 **/
public class ThirdAuthServiceFactory {

    private final static Map<ThirdSourceEnum, ThirdAuthService> FACTORY = Maps.newHashMap();

    /**
     * 注册服务
     * @param sourceEnum
     * @param thirdAuthService
     */
    public static void register(ThirdSourceEnum sourceEnum, ThirdAuthService thirdAuthService) {
        if (Objects.isNull(sourceEnum) || Objects.isNull(thirdAuthService)) {
            throw new RuntimeException("注册第三方认证接口失败");
        }
        if (FACTORY.containsKey(sourceEnum)) {
            throw new RuntimeException(String.format("注册【%s】第三方认证接口已存在", sourceEnum));
        }
        FACTORY.put(sourceEnum, thirdAuthService);
    }

    /**
     * 获取服务
     * @param sourceEnum
     * @return
     */
    public static ThirdAuthService getService(ThirdSourceEnum sourceEnum) {
        ThirdAuthService thirdAuthService = FACTORY.get(sourceEnum);
        if (Objects.isNull(thirdAuthService)) {
            throw new RuntimeException("获取第三方认证接口失败");
        }
        return thirdAuthService;
    }
}
