package com.ahcloud.edas.pulsar.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerAccessMode;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.client.impl.MessageIdImpl;
import org.apache.pulsar.client.impl.TopicMessageIdImpl;

import java.nio.charset.StandardCharsets;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 15:44
 **/
@Slf4j
public class ProducerConsumerTest {

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .authentication(
                        AuthenticationFactory.token("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiJ9.qPdd0pZxwJVtZ720TT4UFt-cUk1cQQcSTowa591D2aUX27HlLJtKUfy2VtEcLP7X884bINsQefbqmB1Yc4X--5-CY6XGyVmsu6u25oO0eO08QLf559-9YDTj724dmfgErSVf4EZAJ7OsUlkix4GY9gCQ8BFQw1hnjlGGz-wYmccQKMxrt7r3Nkh1uaZ-SPflnKYj0q3dR52wW5z8ucafDz_4e_S2CjB5kxOOWNfTRov_BWpD6-j7FQftv6nL3i_JWM7zBesF2E7xeF7sdfWSDRv77ti-LPqrDdLAcigqa9s_TXeZLsqLOdBMc4Jn5EMeROS0xNhhFYBEZ3_PxH2JoQ")
                )
                .build();
//        Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
//                .accessMode(ProducerAccessMode.Shared)
//                .topic("persistent://biz/dev/ahcloud-admin-api-2")
//                .create();
//        producer.send("first pulsar message");
//

        Producer<String> producer2 = pulsarClient.newProducer(Schema.STRING)
                .accessMode(ProducerAccessMode.Shared)
                .topic("persistent://biz/dev/ahcloud-admin-api-2")
                .create();
        for (int i = 0; i < 100; i++) {
            producer2.newMessage()
                    .key(String.valueOf(i))
                    .value("send pulsar message")
                    .send();
        }
//
////        MessageListener messageListener = (con, msg) -> {
////            try {
////                Message message = con.receive();
////                log.info("message is {}", JsonUtils.toJsonString(message));
////            } catch (PulsarClientException e) {
////                throw new RuntimeException(e);
////            }
////        };
        
        Consumer<byte[]> consumer = pulsarClient.newConsumer()
                .topic("persistent://biz/dev/ahcloud-admin-api-2")
                .subscriptionName("ahcloud-admin-api")
                .consumerName("consume1")
                .subscriptionType(SubscriptionType.Shared)
                .messageListener((MessageListener<byte[]>) (consumer1, message) -> {
                    log.info("message is {}, content is {}, key is {}",message.getProperties(), new String(message.getValue(), StandardCharsets.UTF_8), message.getKey());
                    try {
                        consumer1.acknowledge(message);
                    } catch (PulsarClientException e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();
//        MessageIdImpl messageId = new MessageIdImpl(39,9, 0);
//        consumer1.acknowledge(messageId);
//        consumer2.acknowledge(messageId);
    }
}
