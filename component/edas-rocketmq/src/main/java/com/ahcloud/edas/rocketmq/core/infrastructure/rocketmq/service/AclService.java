package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service;

import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.AclRequest;
import org.apache.rocketmq.common.AclConfig;
import org.apache.rocketmq.common.PlainAccessConfig;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 16:49
 **/
public interface AclService {

    /**
     * 查询acl配置
     * @param excludeSecretKey
     * @return
     */
    AclConfig getAclConfig(boolean excludeSecretKey);

    /**
     * 获取 PlainAccessConfig
     * @param excludeSecretKey
     * @param accessKey
     * @param secretKey
     * @return
     */
    PlainAccessConfig getPlainAclConfig(boolean excludeSecretKey, String accessKey, String secretKey);

    /**
     * 新增acl配置
     * @param config
     */
    void addAclConfig(PlainAccessConfig config);

    /**
     * 删除acl配置
     * @param config
     */
    void deleteAclConfig(PlainAccessConfig config);

    /**
     * 更新acl配置
     * @param config
     */
    void updateAclConfig(PlainAccessConfig config);

    /**
     * 新增或更新topic acl配置
     * @param request
     */
    void addOrUpdateAclTopicConfig(AclRequest request);

    /**
     * 新增或更新group acl配置
     * @param request
     */
    void addOrUpdateAclGroupConfig(AclRequest request);

    /**
     * 删除perm配置
     * @param request
     */
    void deletePermConfig(AclRequest request);

    /**
     * 同步数据
     * @param config
     */
    void syncData(PlainAccessConfig config);

    /**
     * 新增白名单
     * @param whiteList
     */
    void addWhiteList(List<String> whiteList);

    /**
     * 删除白名单地址
     * @param addr
     */
    void deleteWhiteAddr(String addr);

    /**
     * 同步白名单
     * @param whiteList
     */
    void synchronizeWhiteList(List<String> whiteList);
}
