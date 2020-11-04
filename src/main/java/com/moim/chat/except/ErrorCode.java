package com.moim.chat.except;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode.java
 * 
 * @author cdssw
 * @since 10.22, 2020
 * @description  사용자 정의 ErrorCode
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 10.30, 2020    cdssw            최초 생성
 * </pre> 
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

	INPUT_VALUE_INVALID("V_00001", "입력값이 올바르지 않습니다."),
	USER_NOT_FOUND("U_00001", "사용자가 존재하지 않습니다."),
	ELEMENT_NOT_FOUND("E_00001", "항목이 존재하지 않습니다."),
	NOT_AUTHORIZATION("A_00001", "인증되지 않은 사용자 입니다."),
	;
	
	private final String code;
	private final String message;
}
