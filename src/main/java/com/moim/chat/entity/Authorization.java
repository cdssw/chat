package com.moim.chat.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Authorization.java
 * 
 * @author cdssw
 * @since 2020. 10. 23.
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 2020. 10. 23.    cdssw            최초 생성
 * </pre>
 */
@Getter
@Setter
public class Authorization {

	private long exp;
	
	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("client_id")
	private String clientId;
	
	private List<String> authorities;
	
	private String jti;
	
	private List<String> scope;
}
