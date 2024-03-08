package com.ahcloud.edas.admin.biz.infrastructure.function;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/7 21:53
 **/
@FunctionalInterface
public interface VoidFunction {

    /**
     * 业务处理
     *
     * 这个方法没有返回值，可以直接抛出异常，则表示执行失败
     * 为了防止此方法存在事务回滚，直接抛出异常则无需担心事务问题
     * @return
     */
    void apply();
}
