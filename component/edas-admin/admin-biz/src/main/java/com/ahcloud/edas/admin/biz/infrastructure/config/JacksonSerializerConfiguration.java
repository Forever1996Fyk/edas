package com.ahcloud.edas.admin.biz.infrastructure.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Maps;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/23 14:14
 **/
@Configuration
public class JacksonSerializerConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Map<Class<?>, JsonSerializer<?>> map = Maps.newHashMap();
        // Long结果返回统一转为String序列化，防止前端处理Long型数据精度丢失问题
        map.put(Long.class, ToStringSerializer.instance);
        return builder -> builder.serializersByType(map);
    }
}
