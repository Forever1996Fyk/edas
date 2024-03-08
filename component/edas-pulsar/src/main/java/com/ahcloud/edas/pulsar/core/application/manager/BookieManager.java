package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.pulsar.core.domain.bookie.query.BookieQuery;
import com.ahcloud.edas.pulsar.core.domain.bookie.vo.BookieVO;
import com.ahcloud.edas.pulsar.core.infrastructure.configuration.BackendProperties;
import com.ahcloud.edas.pulsar.core.infrastructure.configuration.PulsarProperties;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 10:03
 **/
@Slf4j
@Component
public class BookieManager {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private BackendProperties properties;

    private final String serviceUrl;
    private final String pulsarJwtToken;

    private final static String RW_BOOKIE_URL = "/api/v1/bookie/list_bookies?type=rw&print_hostnames=true";
    private final static String RO_BOOKIE_URL = "/api/v1/bookie/list_bookies?type=ro&print_hostnames=true";
    private final static String BOOKIE_INFO_URL = "/api/v1/bookie/list_bookie_info";

    private final Pattern pattern = Pattern.compile(" \\d+");;

    public BookieManager(PulsarProperties properties) {
        this.pulsarJwtToken = properties.getToken();
        this.serviceUrl = properties.getServiceHttpUrl();
    }

    /**
     * 查询bookie列表
     * @return
     */
    public List<BookieVO> listBookies() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", pulsarJwtToken));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String rwBookieResult = restTemplate.getForObject(buildUrl(RW_BOOKIE_URL), String.class, httpEntity);
        Map<String, String> rwBookies = JsonUtils.stringToMap2(rwBookieResult);
        String roBookieResult = restTemplate.getForObject(buildUrl(RO_BOOKIE_URL), String.class, httpEntity);
        Map<String, String> roBookies = JsonUtils.stringToMap2(roBookieResult);
        String bookieInfoResult = restTemplate.getForObject(buildUrl(BOOKIE_INFO_URL), String.class, httpEntity);
        Map<String, String> listBookies = JsonUtils.stringToMap2(bookieInfoResult);
        List<BookieVO> bookieVOList = Lists.newArrayList();
        for (String key : listBookies.keySet()) {
            boolean rwBookie = bookieKey(rwBookies, key);
            boolean roBookie = bookieKey(roBookies, key);
            BookieVO.BookieVOBuilder builder = BookieVO.builder();
            if (rwBookie) {
                builder.bookie(key)
                        .status("rw");
            } else if (roBookie){
                builder.bookie(key)
                        .status("ro");
            }
            if ((rwBookie) || (roBookie)) {
                Matcher matcher = pattern.matcher(listBookies.get(key));
                List<String> storageSizeList = Lists.newArrayList();
                while (matcher.find()) {
                    String group = matcher.group();
                    storageSizeList.add(group.trim());
                }
                builder.storage(storageSizeList);
                bookieVOList.add(builder.build());
            }
        }
        return bookieVOList;
    }

    /**
     * 跳转bookie 心跳链接
     * @param bookie
     * @return
     */
    public String forwardBookiesHeartbeat(String bookie) {
        bookie = checkBookie(bookie);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", pulsarJwtToken));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return restTemplate.getForObject(bookie, String.class, httpEntity);
    }

    private String checkBookie(String bookie) {
        if (bookie == null || bookie.length() <= 0) {
            bookie = properties.getDirectRequestHost();
        }

        if (!bookie.startsWith("http")) {
            bookie = "http://" + bookie;
        }
        return bookie;
    }

    private boolean bookieKey(Map<String, String> bookies, String key) {
        return CollectionUtils.isNotEmpty(bookies) && bookies.containsKey(key);
    }

    private String buildUrl(String url) {
        return serviceUrl + url;
    }

}
