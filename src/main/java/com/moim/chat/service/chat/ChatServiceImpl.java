package com.moim.chat.service.chat;

import java.util.List;

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
import com.moim.chat.service.chat.ChatDto.UsersUnreadRes;
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
		
		if("".equals(dto.getLeaderName()) || "".equals(dto.getReceiver())) {
			throw new ChatBusinessException(ErrorCode.INPUT_VALUE_INVALID);
		}
		
		// DB에 저장
		Chat chat = chatRepository.save(dto.toEntity());
		
		// 발송시간 설정
		ChatMessage chatMessage = dto.toMessage();
		chatMessage.setId(chat.getId());
		chatMessage.setTimeStamp(chat.getTimeStamp());
		
		// kafka 전송
		sender.send(topicChat, chatMessage);		
	}

	@Transactional
	@Override
	public Page<Res> postHistory(ChatDto.ChatHistoryReq dto, String receiver, Pageable pageable) {
		Page<Res> res = chatRepository
				.findHistory(dto, receiver, pageable)
				.map(m -> modelMapper.map(m, ChatDto.Res.class))
				;

		// 데이터가 있을경우 최종 id를 가져온다.
		if(res.getContent().size() > 0) {
			long id = res.getContent().get(res.getContent().size() - 1).getId();
			
			// 해당 id보다 작으며 read가 false인 데이터를 가져와서 읽음 처리
			List<Chat> list = chatRepository.findUnread(id, dto.getMeetId(), dto.getSender(), receiver);
			for(Chat chat : list) {
				chat.read();
			}
		}
				
		return res;
	}

	@Override
	public Long getUnread(long meetId, String username) {
		return chatRepository.countByMeetIdAndReadAndReceiver(meetId, false, username);
	}

	@Override
	public List<UsersUnreadRes> getUsersUnread(long meetId, String username) {
		Chat chat = chatRepository.findTop1ByMeetId(meetId);
		boolean leader = username.equals(chat.getLeaderName()) ? true : false;
		List<ChatDto.UsersUnreadRes> list = chatRepository.countUsersUnread(meetId, username, leader);
		return list;
	}

	@Override
	public int getCount(long meetId) {
		List<String> chatCount = chatRepository.findChatCount(meetId);
		return chatCount.size();
	}

	@Override
	public List<Res> getContectList(String username) {
		return chatRepository.findDistinctBySender(username);
	}

}
