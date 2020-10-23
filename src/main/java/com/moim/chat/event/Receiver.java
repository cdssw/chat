package com.moim.chat.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moim.kafka.ChatMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * Receiver.java
 * 
 * @author cdssw
 * @since 2020. 10. 22.
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 2020. 10. 22.    cdssw            최초 생성
 * </pre>
 */
@Slf4j
@Component
public class Receiver {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@KafkaListener(topics = "${spring.kafka.topic.chat}", groupId = "${spring.kafka.consumer.group-id}")
	public void userModified(ChatMessage payload) throws JsonProcessingException {
		log.info("message='{}'", payload);
		
		// 해당 컨텐츠에 작성자와 문의자가 topic으로 연결됨
		String topic = String.format("/topic/%s/%s-%s", payload.getMeetId(), payload.getLeaderName(), payload.getUsername()); 
		this.template.convertAndSend(topic, payload);		
	}

}
