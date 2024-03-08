package com.ahcloud.edas.nacos.test;

import com.ahcloud.edas.nacos.biz.infrastructure.repository.bean.AppResource;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 10:15
 **/
public class NacosTest {

    public static void main(String[] args) {
        String content = "spring:\n" +
                "  datasource:\n" +
                "    url: jdbc:mysql://127.0.0.1:3306/edit_test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&allowMultiQueries=true\n" +
                "    username: root\n" +
                "    password: root\n" +
                "    druid:\n" +
                "      webStatFilter:\n" +
                "        enabled: true\n" +
                "      stat-view-servlet:\n" +
                "        enabled: true\n" +
                "        # 设置白名单，不填则允许所有访问\n" +
                "        allow:\n" +
                "        url-pattern: /monitor/druid/*\n" +
                "      filter:\n" +
                "        stat:\n" +
                "          enabled: true\n" +
                "          # 慢SQL记录\n" +
                "          log-slow-sql: true\n" +
                "          slow-sql-millis: 1000\n" +
                "          merge-sql: true\n" +
                "        wall:\n" +
                "          config:\n" +
                "            multi-statement-allow: true\n" +
                "      # 初始连接数\n" +
                "      initialSize: 5\n" +
                "      # 最小连接池数量\n" +
                "      minIdle: 10\n" +
                "      # 最大连接池数量\n" +
                "      maxActive: 100\n" +
                "      # 配置获取连接等待超时的时间\n" +
                "      maxWait: 60000\n" +
                "      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒\n" +
                "      timeBetweenEvictionRunsMillis: 60000\n" +
                "      # 配置一个连接在池中最小生存的时间，单位是毫秒\n" +
                "      minEvictableIdleTimeMillis: 300000\n" +
                "      # 配置一个连接在池中最大生存的时间，单位是毫秒\n" +
                "      maxEvictableIdleTimeMillis: 900000\n" +
                "      # 配置检测连接是否有效\n" +
                "      validationQuery: SELECT 1 FROM DUAL\n" +
                "      testWhileIdle: true\n" +
                "      testOnBorrow: false\n" +
                "      testOnReturn: false\n" +
                "  redis:\n" +
                "    database: 0\n" +
                "    host: 127.0.0.1\n" +
                "    port: 6379\n" +
                "    password: root   # 密码（默认为空）\n" +
                "    timeout: 60000ms  # 连接超时时长（毫秒）\n" +
                "    lettuce:\n" +
                "      pool:\n" +
                "        max-active: 1000  # 连接池最大连接数（使用负值表示没有限制）\n" +
                "        max-wait: -1ms      # 连接池最大阻塞等待时间（使用负值表示没有限制）\n" +
                "        max-idle: 10      # 连接池中的最大空闲连接\n" +
                "        min-idle: 5       # 连接池中的最小空闲连接\n" +
                "  jackson:\n" +
                "    time-zone: GMT+8\n" +
                "    date-format: yyyy-MM-dd HH:mm:ss";
        System.out.println(reverseReplaceConfigSensitiveInfo(content));
    }

    /**
     * 掩码转为敏感信息
     * @param content
     * @return
     */
    private static String replaceConfigSensitiveInfo(String content) {
        Map<String, String> maskedMap = Maps.newHashMap();
        maskedMap.put("1111", "jdbc:mysql://127.0.0.1");
        maskedMap.put("2222", "3306");
        maskedMap.put("3333", "root");
        maskedMap.put("4444", "root");
        maskedMap.put("5555", "127.0.0.1");
        maskedMap.put("6666", "6379");
        maskedMap.put("7777", "root");
        Set<String> maskedSet = maskedMap.keySet();
        Collection<String> values = maskedMap.values();
        // 一次性替换所有掩码
        String[] maskedArray = maskedSet.stream()
                .map(masked -> "$" + masked).toArray(String[]::new);
        return StringUtils.replaceEach(content, maskedArray, values.toArray(String[]::new));
    }

    /**
     * 敏感信息转为掩码
     * @param content
     * @return
     */
    private static String reverseReplaceConfigSensitiveInfo(String content) {
        Map<String, String> maskedMap = Maps.newHashMap();
        maskedMap.put("1111", "jdbc:mysql://127.0.0.1");
        maskedMap.put("2222", "3306");
        maskedMap.put("3333", "root");
        maskedMap.put("4444", "root");
        maskedMap.put("5555", "127.0.0.1");
        maskedMap.put("6666", "6379");
        maskedMap.put("7777", "root");
        Set<String> masks = maskedMap.keySet();
        Collection<String> values = maskedMap.values();
        // 一次性替换所有敏感信息
        // 一次性替换所有掩码
        String[] maskedArray = masks.stream()
                .map(masked -> "$" + masked).toArray(String[]::new);
        return StringUtils.replaceEach(content, values.toArray(String[]::new), maskedArray);
    }

    private Map<String, String> maskMapToOriginal(List<AppResource> appResourceList) {
        Map<String, String> result = Maps.newHashMap();
        for (AppResource appResource : appResourceList) {
            if (StringUtils.isNotBlank(appResource.getUrlMask())) {
                result.put(appResource.getUrlMask(), appResource.getUrl());
            }
            if (StringUtils.isNotBlank(appResource.getPortMask()) && Objects.nonNull(appResource.getPort())) {
                result.put(appResource.getPortMask(), appResource.getPort().toString());
            }
            if (StringUtils.isNotBlank(appResource.getUsernameMask())) {
                result.put(appResource.getUsernameMask(), appResource.getUsername());
            }
            if (StringUtils.isNotBlank(appResource.getPasswordMask())) {
                result.put(appResource.getPasswordMask(), appResource.getPassword());
            }
        }
        return result;
    }

    private Map<String, String> originalMapToMask(List<AppResource> appResourceList) {
        Map<String, String> result = Maps.newHashMap();
        for (AppResource appResource : appResourceList) {
            if (StringUtils.isNotBlank(appResource.getUrl())) {
                result.put(appResource.getUrl(), appResource.getUrlMask());
            }
            if (StringUtils.isNotBlank(appResource.getPortMask()) && Objects.nonNull(appResource.getPort())) {
                result.put(appResource.getPort().toString(), appResource.getPortMask());
            }
            if (StringUtils.isNotBlank(appResource.getUsername())) {
                result.put(appResource.getUsername(), appResource.getUsernameMask());
            }
            if (StringUtils.isNotBlank(appResource.getPassword())) {
                result.put(appResource.getPassword(), appResource.getPasswordMask());
            }
        }
        return result;
    }
}
