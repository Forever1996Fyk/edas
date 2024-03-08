package com.ahcloud.edas.gateway.core.infrastructure.constant;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/4/14 10:27
 **/
public class EnvConstants {

    /**
     * 本地环境
     */
    public final static String LOCAL = "local";

    /**
     * 开发环境
     */
    public final static String DEV = "dev";

    /**
     * 联调环境
     */
    public final static String TEST = "test";

    /**
     * 测试环境
     */
    public final static String SIT = "sit";

    /**
     * 预发环境
     */
    public final static String PRE = "pre";

    /**
     * 生产环境
     */
    public final static String PROD = "prod";

    /**
     * 所有环境列表
     */
    public final static List<String> ALL_ENV_LIST = Lists.newArrayList(
            LOCAL,
            DEV,
            TEST,
            SIT,
            PRE,
            PROD
    );

    /**
     * 是否开发环境
     * @param env
     * @return
     */
    public static boolean isDev(String env) {
        return DEV.equals(env);
    }

    /**
     * 是否开发环境
     * @param env
     * @return
     */
    public static boolean isTest(String env) {
        return TEST.equals(env);
    }

    /**
     * 是否开发环境
     * @param env
     * @return
     */
    public static boolean isSit(String env) {
        return SIT.equals(env);
    }

    /**
     * 是否开发环境
     * @param env
     * @return
     */
    public static boolean isPre(String env) {
        return PRE.equals(env);
    }

    /**
     * 是否开发环境
     * @param env
     * @return
     */
    public static boolean isProd(String env) {
        return PROD.equals(env);
    }
}
