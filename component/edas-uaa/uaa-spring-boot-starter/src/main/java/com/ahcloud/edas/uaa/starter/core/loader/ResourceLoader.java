package com.ahcloud.edas.uaa.starter.core.loader;

import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.uaa.starter.core.constant.UaaConstants;
import com.ahcloud.edas.uaa.starter.domain.AuthorityApiDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.AntPathMatcher;

import java.util.Map;

/**
 * @program: permissions-center
 * @description: 资源加载器
 * @author: YuKai Fan
 * @create: 2021-12-24 15:15
 **/
@Slf4j
public class ResourceLoader {

    protected final RedisTemplate<String, String> redisTemplate;
    protected final static AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public ResourceLoader(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取当前apiCode集合
     *
     * @return
     */
    public Map<Object, Object> getUriAndApiCodeMap() {
        return redisTemplate.opsForHash().entries(UaaConstants.API_CACHE_AUTHORITY);
    }

    /**
     * 根据uri获取当前权限api缓存信息
     *
     * @param uri
     * @return
     */
    public AuthorityApiDTO getCacheResourceByUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            log.error("ResourceLoader[getCacheResourceByUri] get apiCodeKey error, uri is empty");
            return null;
        }
        Map<Object, Object> uriAndApiCodeMap = this.getUriAndApiCodeMap();
        if (CollectionUtils.isEmpty(uriAndApiCodeMap)) {
            return null;
        }
        String apiCodeKey = uriAndApiCodeMap
                .keySet()
                .stream()
                .filter(item -> PATH_MATCHER.match(String.valueOf(item), uri))
                .findAny()
                .map(String::valueOf)
                .orElse(null);
        if (StringUtils.isBlank(apiCodeKey)) {
            log.error("ResourceLoader[getCacheResourceByUri] get apiCodeKey by uri result is empty, uri is {}, uriAndApiCodeMap is {}", uri, uriAndApiCodeMap);
            return null;
        }
        return JsonUtils.stringToBean(String.valueOf(uriAndApiCodeMap.get(apiCodeKey)), AuthorityApiDTO.class);
    }

}
