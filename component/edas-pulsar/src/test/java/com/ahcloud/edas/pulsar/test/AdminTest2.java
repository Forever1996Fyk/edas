package com.ahcloud.edas.pulsar.test;

import com.google.common.collect.Sets;
import org.apache.pulsar.client.admin.BrokerStats;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Tenants;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.policies.data.PartitionedTopicStats;
import org.apache.pulsar.common.policies.data.PublisherStats;
import org.apache.pulsar.common.policies.data.TenantInfo;
import org.apache.pulsar.common.policies.data.TopicStats;

import java.util.List;
import java.util.Map;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 15:48
 **/
public class AdminTest2 {

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

        BrokerStats brokerStats = pulsarAdmin.brokerStats();
        String topics = brokerStats.getTopics();
        System.out.println("brokerTopics: " + topics);

//        Topics topics = pulsarAdmin.topics();
//        PartitionedTopicStats partitionedStats = topics.getPartitionedStats("persistent://public/default/first-test-topic", true);
//        Map<String, ? extends TopicStats> partitions = partitionedStats.getPartitions();

//        TopicStats topicsStats = topics.getStats("persistent://public/default/first-test-topic");
//        List<? extends PublisherStats> publishers = topicsStats.getPublishers();
    }
}
