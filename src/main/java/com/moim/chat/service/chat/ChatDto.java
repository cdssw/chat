package com.moim.chat.service.chat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.moim.chat.entity.Chat;
import com.moim.kafka.ChatMessage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ChatDto.java
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
public class ChatDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChatReq {
		
		@NotNull
		private Long meetId;
		
		@NotBlank
		private String leaderName;
		
		@NotBlank
		private String username;
		
		@NotBlank
		private String message;
		
		@Builder
		public ChatReq(Long meetId, String leaderName, String username, String message) {
			this.meetId = meetId;
			this.leaderName = leaderName;
			this.username = username;
			this.message = message;
		}
		
		public Chat toEntity() {
			return Chat.builder()
					.meetId(meetId)
					.leaderName(leaderName)
					.username(username)
					.message(message)
					.build();
		}
		
		public ChatMessage toMessage() {
			return ChatMessage.builder()
					.meetId(meetId)
					.leaderName(leaderName)
					.username(username)
					.message(message)
					.build();
		}
	}
}
