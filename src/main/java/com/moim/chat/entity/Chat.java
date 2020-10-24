package com.moim.chat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Chat.java
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Chat extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long meetId;
	
	private String leaderName;
	
	private String username;
	
	private String sender;
	
	private String message;
	
	@Builder
	public Chat(Long meetId, String leaderName, String username, String sender, String message) {
		this.meetId = meetId;
		this.leaderName = leaderName;
		this.username = username;
		this.sender = sender;
		this.message = message;
	}
}
