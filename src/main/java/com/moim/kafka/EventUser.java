package com.moim.kafka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * EventUser.java
 * 
 * @author cdssw
 * @since 2020. 6. 4.
 * @description  Kafka 이벤트 객체
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 2020. 6. 4.    cdssw            최초 생성
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EventUser {

	private Long id;
	private String username;
	private String userNm;
	private String userNickNm;
	private String avatarPath;
	private String phone;
}
