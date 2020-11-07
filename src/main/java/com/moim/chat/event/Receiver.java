package com.moim.chat.event;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moim.chat.entity.User;
import com.moim.chat.repository.UserRepository;
import com.moim.kafka.EventMessage;
import com.moim.kafka.EventUser;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Slf4j
@Component
public class Receiver {
	
	private UserRepository userRepository;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@KafkaListener(topics = "${spring.kafka.topic.chat-message}", groupId = "${spring.kafka.consumer.group-id}")
	public void userModified(EventMessage payload) throws JsonProcessingException {
		log.info("message='{}'", payload);
		
		// 해당 컨텐츠에 작성자와 문의자가 topic으로 연결됨
		String topic = String.format("/topic/%s/%s-%s"
				, payload.getMeetId()
				, payload.getLeaderName()
				, payload.getLeaderName().equals(payload.getReceiver()) ? payload.getSender() : payload.getReceiver()); 
		this.template.convertAndSend(topic, payload);		
	}

	// user-modified가 발생하면 수신
	@Transactional
	@KafkaListener(topics = "${spring.kafka.topic.user-modified}", groupId = "${spring.kafka.consumer.group-id}")
	public void userModified(EventUser payload) {
		log.info(payload.getPhone());
		final User user = userRepository.findByUsername(payload.getUsername());
		user.editUser(payload);
	}
	
	// user-created가 발생하면 수신
	@Transactional
	@KafkaListener(topics = "${spring.kafka.topic.user-created}", groupId = "${spring.kafka.consumer.group-id}")
	public void userCreated(EventUser payload) {
		User user = userRepository.findByUsername(payload.getUsername());
		if(user == null) { // 테이블에 존재하지 않으면 추가 
			user = User.builder()
					.id(payload.getId())
					.username(payload.getUsername())
					.userNm(payload.getUserNm())
					.userNickNm(payload.getUserNickNm())
					.phone(payload.getPhone())
					.avatarPath(payload.getAvatarPath())
					.build();
			userRepository.save(user);
		}
	}
}
