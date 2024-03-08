package com.ahcloud.edas.rocketmq.core.application.helper;

import com.ahcloud.edas.rocketmq.core.infrastructure.constant.AclPermEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqAppConfig;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.AclRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.PlainAccessConfig;

import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 17:18
 **/
public class RmqAclHelper {

    /**
     * 构建默认配置
     * @param rmqAppConfig
     * @return
     */
    public static PlainAccessConfig buildDefaultAccessConfig(RmqAppConfig rmqAppConfig) {
        return buildAccessConfig(rmqAppConfig, AclPermEnum.DENY, AclPermEnum.SUB, false);
    }

    /**
     * 构建topic acl配置
     * @param accessConfig
     * @param topic
     * @param topicPerm
     * @param group
     * @param groupPerm
     * @return
     */
    public static AclRequest buildAclConfig(PlainAccessConfig accessConfig, String topic, AclPermEnum topicPerm, String group, AclPermEnum groupPerm) {
        AclRequest aclRequest = new AclRequest();
        if (Objects.nonNull(accessConfig)) {
            aclRequest.setConfig(accessConfig);
        }
        if (StringUtils.isNotBlank(topic)) {
            aclRequest.setTopicPerm(generatePerm(topic, topicPerm));
        }
        if (StringUtils.isNotBlank(group)) {
            aclRequest.setGroupPerm(generatePerm(group, groupPerm));
        }
        return aclRequest;
    }

    /**
     * 构建topic acl配置
     * @param accessKey 对于构建topic级别的权限，只需要提供accessKey即可判断
     * @param topic
     * @param perm
     * @return
     */
    public static AclRequest buildTopicAclConfig(String accessKey, String topic, AclPermEnum perm) {
        PlainAccessConfig plainAccessConfig = new PlainAccessConfig();
        plainAccessConfig.setAccessKey(accessKey);
        return buildAclConfig(plainAccessConfig, topic, perm, null, null);
    }

    /**
     * 构建group acl配置
     * @param accessKey 对于构建topic级别的权限，只需要提供accessKey即可判断
     * @param groupName
     * @param perm
     * @return
     */
    public static AclRequest buildGroupAclConfig(String accessKey, String groupName, AclPermEnum perm) {
        PlainAccessConfig plainAccessConfig = new PlainAccessConfig();
        plainAccessConfig.setAccessKey(accessKey);
        return buildAclConfig(plainAccessConfig, null, null, groupName, perm);
    }

    /**
     * 构建topic acl配置
     * @param topic
     * @param perm
     * @return
     */
    public static AclRequest buildAclRequest(String topic, AclPermEnum perm) {
        AclRequest aclRequest = new AclRequest();
        aclRequest.setTopicPerm(generatePerm(topic, perm));
        return aclRequest;
    }


    /**
     * 构建group acl配置
     * @param group
     * @param perm
     * @return
     */
    public static AclRequest buildGroupAclConfig(String group, AclPermEnum perm) {
        AclRequest aclRequest = new AclRequest();
        aclRequest.setGroupPerm(generatePerm(group, perm));
        return aclRequest;
    }

    /**
     * 构建默认配置
     * @param rmqAppConfig
     * @return
     */
    public static PlainAccessConfig buildAccessConfig(RmqAppConfig rmqAppConfig, AclPermEnum defaultTopicPerm, AclPermEnum defaultGroupPerm, boolean admin) {
        PlainAccessConfig plainAccessConfig = new PlainAccessConfig();
        plainAccessConfig.setAccessKey(rmqAppConfig.getAccessKey());
        plainAccessConfig.setSecretKey(rmqAppConfig.getSecretKey());
        plainAccessConfig.setAdmin(admin);
        plainAccessConfig.setDefaultTopicPerm(defaultTopicPerm.getPerm());
        plainAccessConfig.setDefaultGroupPerm(defaultGroupPerm.getPerm());
        return plainAccessConfig;
    }

    public static String generatePerm(String source, AclPermEnum perm) {
        return source + "=" + perm.getPerm();
    }
}
