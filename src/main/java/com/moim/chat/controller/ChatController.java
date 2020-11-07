package com.moim.chat.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/history")
	@ResponseStatus(value = HttpStatus.OK)
	public Page<ChatDto.Res> getHistory(@RequestBody @Valid ChatDto.ChatHistoryReq dto, Pageable pageable, HttpServletRequest req) {
		String username = req.getHeader("username"); // gateway에서 보내준 username header를 추출
		return chatService.postHistory(dto, username, pageable);
	}
	
	// 해당 meet에 대한 전체 미확인 카운트
	@GetMapping("/unread/{meetId}")
	@ResponseStatus(value = HttpStatus.OK)
	public Long getUnread(@PathVariable final long meetId, HttpServletRequest req) {
		String username = req.getHeader("username"); // gateway에서 보내준 username header를 추출
		return chatService.getUnread(meetId, username);		
	}
	
	// 해당 meet에 대한 유저별 미확인 카운트
	// 리더는 모든 사용자 카운트, 지원자는 리더것만 볼수 있음
	@GetMapping("/unread/users/{meetId}")
	@ResponseStatus(value = HttpStatus.OK)
	public List<ChatDto.UsersUnreadRes> getUsersUnread(@PathVariable final long meetId, HttpServletRequest req) {
		String username = req.getHeader("username"); // gateway에서 보내준 username header를 추출
		return chatService.getUsersUnread(meetId, username);
	}
	
	// 메시지 읽음 처리
	@PutMapping("/read/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public void putRead(@PathVariable final long id, HttpServletRequest req) {
		String username = req.getHeader("username"); // gateway에서 보내준 username header를 추출
		chatService.putRead(id, username);
	}
}
