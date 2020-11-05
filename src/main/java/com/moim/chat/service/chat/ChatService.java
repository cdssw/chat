package com.moim.chat.service.chat;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ChatService.java
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
public interface ChatService {
	
	void addMessage(ChatDto.ChatReq dto);
	Page<ChatDto.Res> postHistory(ChatDto.ChatHistoryReq dto, final String receiver, Pageable pageable);
	Long getUnread(final long meetId, final String username);
	List<ChatDto.UsersUnreadRes> getUsersUnread(final long meetId, final String username);
}
