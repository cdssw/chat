package com.moim.chat.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.moim.kafka.EventUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User.java
 * 
 * @author cdssw
 * @since Apr 12, 2020
 * @description 
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * Apr 12, 2020   cdssw            최초 생성
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
@Entity
public class User extends BaseTimeEntity {

	@Id
	private Long id;

	private String username;
	private String userNm;
	private String userNickNm;
	private String phone;
	private String avatarPath;

	// 사용자 정보 수정
	public void editUser(EventUser user) {
		this.phone = user.getPhone();
		this.avatarPath = user.getAvatarPath();
	}
}
