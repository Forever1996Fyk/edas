package com.ahcloud.edas.admin.biz.infrastructure.security.loader;

import com.ahcloud.edas.admin.biz.application.manager.SysApiManager;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.uaa.starter.core.constant.UaaConstants;
import com.ahcloud.edas.uaa.starter.core.loader.ResourceLoader;
import com.ahcloud.edas.uaa.starter.domain.AuthorityApiDTO;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/1 11:36
 **/
@Slf4j
@Component
public class RewriteResourceLoader extends ResourceLoader implements InitializingBean {
    @Resource
    private SysApiManager sysApiManager;

    private final static Map<String, String> API_CACHE = Maps.newConcurrentMap();

    public RewriteResourceLoader(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<AuthorityApiDTO> allAuthorityApis = sysApiManager.getAllAuthorityApis();
        Map<String, String> apiMap = CollectionUtils.convertMap(allAuthorityApis, AuthorityApiDTO::getUri, JsonUtils::toJsonString);
        redisTemplate.opsForHash().putAll(UaaConstants.API_CACHE_AUTHORITY, apiMap);
        API_CACHE.putAll(apiMap);
    }


    /**
     * 更新资源缓存
     *
     * @param authorityApiDTO
     */
    public void updateResourceMap(AuthorityApiDTO authorityApiDTO) {
        if (Objects.nonNull(authorityApiDTO) && StringUtils.isNotBlank(authorityApiDTO.getUri())) {
            redisTemplate.opsForHash().put(UaaConstants.API_CACHE_AUTHORITY, authorityApiDTO.getUri(), Objects.requireNonNull(JsonUtils.toJsonString(authorityApiDTO)));
            API_CACHE.put(authorityApiDTO.getUri(), JsonUtils.toJsonString(authorityApiDTO));
        }
    }


    /**
     * 删除资源
     *
     * @param uri
     */
    public void deleteResourceByUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            return;
        }
        redisTemplate.opsForHash().delete(UaaConstants.API_CACHE_AUTHORITY, uri);
        API_CACHE.remove(uri);
    }

    /**
     * 根据uri获取当前权限api缓存信息
     *
     * @param uri
     * @return
     */
    @Override
    public AuthorityApiDTO getCacheResourceByUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            log.error("ResourceLoader[getCacheResourceByUri] get apiCodeKey error, uri is empty");
            return null;
        }
        Map<String, String> uriAndApiCodeMap = API_CACHE;
        if (CollectionUtils.isEmpty(uriAndApiCodeMap)) {
            return null;
        }
        String apiCodeKey = uriAndApiCodeMap
                .keySet()
                .stream()
                .filter(item -> PATH_MATCHER.match(item, uri))
                .findAny()
                .orElse(null);
        if (StringUtils.isBlank(apiCodeKey)) {
            log.error("ResourceLoader[getCacheResourceByUri] get apiCodeKey by uri result is empty, uri is {}, uriAndApiCodeMap is {}", uri, uriAndApiCodeMap);
            return null;
        }
        return JsonUtils.stringToBean(uriAndApiCodeMap.get(apiCodeKey), AuthorityApiDTO.class);
    }


}
