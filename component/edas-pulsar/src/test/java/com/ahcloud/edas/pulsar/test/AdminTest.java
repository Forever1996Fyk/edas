package com.ahcloud.edas.pulsar.test;

import com.google.common.collect.Sets;
import org.apache.pulsar.client.admin.Brokers;
import org.apache.pulsar.client.admin.Clusters;
import org.apache.pulsar.client.admin.Namespaces;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Tenants;
import org.apache.pulsar.client.admin.TopicPolicies;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.BatchMessageIdImpl;
import org.apache.pulsar.client.impl.MessageIdImpl;
import org.apache.pulsar.common.policies.data.AuthAction;
import org.apache.pulsar.common.policies.data.PartitionedTopicStats;
import org.apache.pulsar.common.policies.data.Policies;
import org.apache.pulsar.common.policies.data.TenantInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/4 11:14
 **/
public class AdminTest {

    public static void main(String[] args) throws PulsarClientException, PulsarAdminException {
        PulsarAdmin pulsarAdmin = PulsarAdmin.builder()
                // 服务地址，多个逗号隔开
                .serviceHttpUrl("http://localhost:8091")
                //身份认证
                .authentication(
                        AuthenticationFactory.token("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiJ9.qPdd0pZxwJVtZ720TT4UFt-cUk1cQQcSTowa591D2aUX27HlLJtKUfy2VtEcLP7X884bINsQefbqmB1Yc4X--5-CY6XGyVmsu6u25oO0eO08QLf559-9YDTj724dmfgErSVf4EZAJ7OsUlkix4GY9gCQ8BFQw1hnjlGGz-wYmccQKMxrt7r3Nkh1uaZ-SPflnKYj0q3dR52wW5z8ucafDz_4e_S2CjB5kxOOWNfTRov_BWpD6-j7FQftv6nL3i_JWM7zBesF2E7xeF7sdfWSDRv77ti-LPqrDdLAcigqa9s_TXeZLsqLOdBMc4Jn5EMeROS0xNhhFYBEZ3_PxH2JoQ")
                )
//                //是否在群集中强制执行 TLS 验证。
                .useKeyStoreTls(false)
//                //从客户端接受不受信任的 TLS 证书。
                .allowTlsInsecureConnection(false)
                .enableTlsHostnameVerification(false)
                .build();
        // 查询集群列表
        Clusters clusters = pulsarAdmin.clusters();
        List<String> clustersList = clusters.getClusters();

        // 查询broker角色和token列表(分页)
        // 根据角色查询token
        // 删除token

        Tenants tenants = pulsarAdmin.tenants();
        //查询租户列表(分页) 自定义存储
        List<String> tenantList = tenants.getTenants();
        // 查询租户详情
        TenantInfo tenantInfo = tenants.getTenantInfo("");
        // 删除租户
        tenants.deleteTenant("");
        // 新增租户 分配角色
        tenants.createTenant("", TenantInfo.builder().build());
        // 修改租户
        tenants.updateTenant("", TenantInfo.builder().build());

        Namespaces namespaces = pulsarAdmin.namespaces();
        // 查询命名空间(分页) 自定义存储
        List<String> namespacesList = namespaces.getNamespaces("");
        // 查询命名空间详情 自定义存储
        /*
        新增命名空间  tenant/namespace, 命名空间策略
        策略包括：
            1、消息保留策略 RetentionPolicies
                1.1、如果当消息消费并返回ack时，消息删除，则都设置为0，
                1.2、如果永久保留则设置为-1，无论是否消费，按照最大保留时间和最大存储空间进行持久化存储，到达限制后从后向前删除。另外，保留策略作用于已经 ack 的消息，不影响未 ack 的消息，未 ack 在 TTL 过期才会进入到清理的队列。
            2、消息持久化策略 PersistencePolicies
            3、 消息过期时间：未消费消息的过期时间，超时未 ACK 则跳过对该消息的处理，范围：60秒 ~ 15天。 namespaceMessageTTL
            4、是否自动创建订阅 autoSubscriptionCreationOverride
         */
        namespaces.createNamespace("", new Policies());
        // 删除命名空间
        namespaces.deleteNamespace("");
        namespaces.grantPermissionOnNamespace("", "", Sets.newHashSet(AuthAction.consume, AuthAction.produce, AuthAction.functions, AuthAction.packages, AuthAction.sinks, AuthAction.sinks));

        Topics topics = pulsarAdmin.topics();
        // 查询topic列表(分页) 自定义存储 todo
        List<String> topicsList = topics.getList("");
        // 查询topic详情 自定义存储 todo
        // 新增topic, 分片数默认为1，最大为32
        topics.createPartitionedTopic("", 1);
        // 删除topic
        topics.delete("");
        // 修改topic
        topics.updatePartitionedTopic("", 1);
        // 分配权限
        topics.grantPermission("", "", Sets.newHashSet(AuthAction.consume, AuthAction.produce));
        // 查询权限
        Map<String, Set<AuthAction>> permissions = topics.getPermissions("");
        // topic策略
        TopicPolicies topicPolicies = pulsarAdmin.topicPolicies();
        // 未消费消息的过期时间，超时未 ACK 则跳过对该消息的处理，范围：60秒 ~ 15天。
        topicPolicies.setMessageTTL("", 100);

        // 查询订阅列表 自定义存储
        List<String> subscriptions = topics.getSubscriptions("");
        // 查询订阅详情
        Map<String, String> subscriptionProperties = topics.getSubscriptionProperties("", "");
        // 新增订阅
        topics.createSubscription("", "", null);
        // 修改订阅
        topics.updateSubscriptionProperties("", "", null);
        // 删除订阅
        topics.deleteSubscription("", "");

        // 目前pulsar只支持这种方式查询消息列表，只能实现查询10000条以内的消息假分页
        List<Message<byte[]>> messages = topics.peekMessages("", "", 1);
        for (Message<byte[]> msg : messages) {
            if (msg.getMessageId() instanceof BatchMessageIdImpl) {

            } else {
                MessageIdImpl msgId = (MessageIdImpl) msg.getMessageId();
                long ledgerId = msgId.getLedgerId();
                long entryId = msgId.getEntryId();
                // 查询消息详情
                Message<byte[]> message = topics.getMessageById("", ledgerId, entryId);
            }
        }

        MessageId messageId = topics.getMessageIdByTimestamp("", System.currentTimeMillis());
        if (messageId instanceof BatchMessageIdImpl) {
            BatchMessageIdImpl batchMessageId = (BatchMessageIdImpl) messageId;
        }

        // 查询订阅主题状态，消息消费状态
        PartitionedTopicStats partitionedStats = topics.getPartitionedStats("", true);

        // 跳过所有消息
        topics.skipAllMessages("", "");

        Brokers brokers = pulsarAdmin.brokers();
        // broker列表查询（正常状态）
        List<String> activeBrokers = brokers.getActiveBrokers();
    }
}
