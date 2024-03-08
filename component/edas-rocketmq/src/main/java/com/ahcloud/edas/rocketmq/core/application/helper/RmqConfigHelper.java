package com.ahcloud.edas.rocketmq.core.application.helper;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 09:44
 **/
public class RmqConfigHelper {

    /**
     * 生成随机密钥对
     * @return
     */
    public static Pair<String, String> generateAccessPair() {
        String accessKey = RandomUtil.randomString(8);
        String secretKey = RandomUtil.randomString(16);
        return ImmutablePair.of(accessKey, secretKey);
    }
}
