package com.moim.chat.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moim.chat.service.chat.ChatDto;
import com.moim.chat.service.chat.ChatService;

import lombok.AllArgsConstructor;

/**
 * ChatController.java
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
@RestController
public class ChatController {
	
	private ChatService chatService;
	
	@MessageMapping("/message")
	@ResponseStatus(value = HttpStatus.OK)
	public void sendMessage(ChatDto.ChatReq dto) {
		chatService.addMessage(dto);
	}
	
	@GetMapping("/history")
	@ResponseStatus(value = HttpStatus.OK)
	public Page<ChatDto.Res> getChatListByPage(Pageable pageable) {
		return chatService.getChatListByPage(pageable);
	}
}
