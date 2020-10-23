package com.moim.chat.service.chat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moim.chat.entity.Chat;
import com.moim.chat.event.Sender;
import com.moim.chat.except.ChatBusinessException;
import com.moim.chat.except.ErrorCode;
import com.moim.chat.repository.ChatRepository;
import com.moim.chat.service.chat.ChatDto.ChatReq;
import com.moim.kafka.ChatMessage;

import lombok.AllArgsConstructor;
/**
 * ChatServiceImpl.java
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
@AllArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {
	
	private ChatRepository chatRepository;
	private Sender sender;
	
	private static String topicChat;
	
	@Value("${spring.kafka.topic.chat}")
	private void setTopicChat(String topic) {
		topicChat = topic;
	}

	@Override
	public void addMessage(ChatReq dto) {
		if(dto.getMeetId() == null) {
			throw new ChatBusinessException(ErrorCode.INPUT_VALUE_INVALID);
		}
		
		if("".equals(dto.getLeaderName()) || "".equals(dto.getUsername())) {
			throw new ChatBusinessException(ErrorCode.INPUT_VALUE_INVALID);
		}
		
		// DB에 저장
		Chat chat = chatRepository.save(dto.toEntity());
		
		// 발송시간 설정
		ChatMessage chatMessage = dto.toMessage();
		chatMessage.setTimeStamp(chat.getInputDt());
		
		// kafka 전송
		sender.send(topicChat, chatMessage);		
	}

}
