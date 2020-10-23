package com.moim.chat.controller;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.moim.chat.service.chat.ChatService;

/**
 * BaseControllerTest.java
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
public abstract class BaseControllerTest {

	// service 기능을 테스트 하는것이 아님
	// controller 테스트시 필요한 service mock 정의
	@MockBean protected ChatService chatService;
}
