package com.moim.chat.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * KafkaTopicConfig.java
 * 
 * @author cdssw
 * @since 10.22, 2020
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 10.30, 2020    cdssw            최초 생성
 * </pre>
 */
@Profile("!test")
@Configuration
public class KafkaTopicConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> config = new HashMap<>();
		config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		return new KafkaAdmin(config);
	}
	
	@Bean
	@Value("${spring.kafka.topic.chat}")
	public NewTopic topicChat(String topic) {
		return new NewTopic(topic, 1, (short) 1);
	}
	
	// user-modified 토픽 생성
	@Bean
	@Value("${spring.kafka.topic.user-modified}")
	public NewTopic topicUserModified(String topic) {
		return new NewTopic(topic, 1, (short) 1);
	}
	
	// user-created 토픽 생성
	@Bean
	@Value("${spring.kafka.topic.user-created}")
	public NewTopic topicUserCreated(String topic) {
		return new NewTopic(topic, 1, (short) 1);
	}	
}
