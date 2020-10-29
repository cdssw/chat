package com.moim.chat.service.chat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moim.chat.entity.Chat;
import com.moim.chat.event.Sender;
import com.moim.chat.except.ChatBusinessException;
import com.moim.chat.except.ErrorCode;
import com.moim.chat.repository.ChatRepository;
import com.moim.chat.service.chat.ChatDto.ChatReq;
import com.moim.chat.service.chat.ChatDto.Res;
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
	
	private ModelMapper modelMapper;
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
		chatMessage.setTimeStamp(chat.getTimeStamp());
		
		// kafka 전송
		sender.send(topicChat, chatMessage);		
	}

	@Transactional(readOnly = true) // 성능향상을 위해
	@Override
	public Page<Res> getHistory(ChatDto.ChatHistoryReq dto, String username, Pageable pageable) {
		return chatRepository
				.findHistory(dto, username, pageable)
				.map(m -> modelMapper.map(m, ChatDto.Res.class))
				;
	}

}
