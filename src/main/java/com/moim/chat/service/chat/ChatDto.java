package com.moim.chat.service.chat;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.moim.chat.entity.Chat;
import com.moim.kafka.EventMessage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
		private String receiver;
		
		@NotBlank
		private String sender;
		
		@NotBlank
		private String message;
		
		@Builder
		public ChatReq(Long meetId, String leaderName, String receiver, String sender, String message) {
			this.meetId = meetId;
			this.leaderName = leaderName;
			this.receiver = receiver;
			this.sender = sender;
			this.message = message;
		}
		
		public Chat toEntity() {
			return Chat.builder()
					.meetId(meetId)
					.leaderName(leaderName)
					.receiver(receiver)
					.sender(sender)
					.message(message)
					.readYn(false)
					.build();
		}
		
		public EventMessage toMessage() {
			return EventMessage.builder()
					.meetId(meetId)
					.leaderName(leaderName)
					.receiver(receiver)
					.sender(sender)
					.message(message)
					.build();
		}
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChatHistoryReq {
		
		private Long id;
		
		@NotNull
		private Long meetId;
		
		@NotBlank
		private String sender;
		
		@Builder
		public ChatHistoryReq(Long id, Long meetId, String sender) {
			this.id = id;
			this.meetId = meetId;
			this.sender = sender;
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@SuperBuilder
	public static abstract class BaseRes {
		private Long id;
		private Long meetId;
		private String leaderName;
		private String receiver;
		private String sender;
		private String message;
		private LocalDateTime timeStamp;
	}
	
	// 결과 DTO
	@Getter
	@Setter
	@NoArgsConstructor
	@SuperBuilder // 부모의 생성자에 대하여 builder를 사용할수 있게 해준다.
	public static class Res extends BaseRes {
	}	
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class UsersUnreadRes {
		private String sender;
		private String userNickNm;
		private String avatarPath;
		private Long count;
	}
}
