package com.ahcloud.edas.uaa.starter.configuration;

import com.ahcloud.edas.uaa.starter.core.access.AccessExecutor;
import com.ahcloud.edas.uaa.starter.core.loader.ResourceLoader;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.Serializable;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/1 10:51
 **/
@Configuration
@EnableConfigurationProperties(UaaProperties.class)
public class UaaAutoConfiguration {

    /**
     * 当缺少ResourceLoader Bean时才加载
     * @param redisTemplate
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ResourceLoader resourceLoader(RedisTemplate<String, String> redisTemplate) {
        return new ResourceLoader(redisTemplate);
    }

    @Bean
    public AccessExecutor accessExecutor(UaaProperties uaaProperties) {
        return new AccessExecutor(uaaProperties);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowCredentials(false);
        configuration.setAllowedMethods(Lists.newArrayList("GET", "POST"));
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
