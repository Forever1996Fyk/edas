package com.ahcloud.edas.common.util;
/**
 * @program: zbgx_system
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-22 13:35
 **/
public class SnowIdUtils {

    /**
     * 获取递增全局唯一id
     * @return
     */
    public static String randomStrId() {
        return String.valueOf(IdWorker.idWorker().createId());
    }

    /**
     * 获取递增全局唯一id
     * @return
     */
    public static Long randomLongId() {
        return IdWorker.idWorker().createId();
    }
}
