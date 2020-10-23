package com.moim.chat.service.chat;

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
}
