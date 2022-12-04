package com.seal.reactive.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {

	@Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
	public static final String TOPIC = "account";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, "reactive");
        configs.put(AdminClientConfig.RETRIES_CONFIG, "3");
        return new KafkaAdmin(configs);
    }

    //KafkaAdmin client will automatically add this topic
    //for more topics, add more bean of type NewTopic
    @Bean
    public NewTopic topicNotification() {
        return new NewTopic(TOPIC, 2, (short) 1);
    }
}
