package com.moim.kafka;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ChatMessage.java
 * 
 * @author cdssw
 * @since 10.22, 2020
 * @description  kafka 이벤트 객체, 수신쪽과 같은 패키지명으로 등록되어야 함
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 10.30, 2020    cdssw            최초 생성
 * </pre> 
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ChatMessage {

	private Long meetId;
	private String leaderName;
	private String username;
	private String message;
	private LocalDateTime timeStamp;
	
	@Builder
	public ChatMessage(Long meetId, String leaderName, String username, String message) {
		this.meetId = meetId;
		this.leaderName = leaderName;
		this.username = username;
		this.message = message;
	}
}
