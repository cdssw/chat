package com.moim.chat.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.moim.kafka.ChatMessage;

/**
 * Sender.java
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
@Component
public class Sender {

	@Autowired
	private KafkaTemplate<String, ChatMessage> kafkaTemplate;
	
	public void send(String topic, ChatMessage payload) {
		kafkaTemplate.send(topic, payload);
	}
}
